package edu.cg.scene;

import edu.cg.Logger;
import edu.cg.algebra.Point;
import edu.cg.algebra.*;
import edu.cg.scene.camera.PinholeCamera;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Surface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Scene {
    private String name = "scene";
    private int maxRecursionLevel = 1;
    private int antiAliasingFactor = 1; //gets the values of 1, 2 and 3
    private boolean renderRefarctions = false;
    private boolean renderReflections = false;
    private PinholeCamera camera;
    private Vec ambient = new Vec(1, 1, 1); //white
    private Vec backgroundColor = new Vec(0, 0.5, 1); //blue sky
    private List<Light> lightSources = new LinkedList<>();
    private List<Surface> surfaces = new LinkedList<>();

    //MARK: initializers
    public Scene initCamera(Point eyePoistion, Vec towardsVec, Vec upVec, double distanceToPlain) {
        this.camera = new PinholeCamera(eyePoistion, towardsVec, upVec, distanceToPlain);
        return this;
    }

    public Scene initAmbient(Vec ambient) {
        this.ambient = ambient;
        return this;
    }

    public Scene initBackgroundColor(Vec backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public Scene addLightSource(Light lightSource) {
        lightSources.add(lightSource);
        return this;
    }

    public Scene addSurface(Surface surface) {
        surfaces.add(surface);
        return this;
    }

    public Scene initMaxRecursionLevel(int maxRecursionLevel) {
        this.maxRecursionLevel = maxRecursionLevel;
        return this;
    }

    public Scene initAntiAliasingFactor(int antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        return this;
    }

    public Scene initName(String name) {
        this.name = name;
        return this;
    }

    public Scene initRenderRefarctions(boolean renderRefarctions) {
        this.renderRefarctions = renderRefarctions;
        return this;
    }

    public Scene initRenderReflections(boolean renderReflections) {
        this.renderReflections = renderReflections;
        return this;
    }

    //MARK: getters
    public String getName() {
        return name;
    }

    public int getFactor() {
        return antiAliasingFactor;
    }

    public int getMaxRecursionLevel() {
        return maxRecursionLevel;
    }

    public boolean getRenderRefarctions() {
        return renderRefarctions;
    }

    public boolean getRenderReflections() {
        return renderReflections;
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return "Camera: " + camera + endl +
                "Ambient: " + ambient + endl +
                "Background Color: " + backgroundColor + endl +
                "Max recursion level: " + maxRecursionLevel + endl +
                "Anti aliasing factor: " + antiAliasingFactor + endl +
                "Light sources:" + endl + lightSources + endl +
                "Surfaces:" + endl + surfaces;
    }

    private transient ExecutorService executor = null;
    private transient Logger logger = null;

    private void initSomeFields(int imgWidth, int imgHeight, Logger logger) {
        this.logger = logger;
    }

    public BufferedImage render(int imgWidth, int imgHeight, double viewPlainWidth, Logger logger) throws InterruptedException, ExecutionException {
        initSomeFields(imgWidth, imgHeight, logger);
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        camera.initResolution(imgHeight, imgWidth, viewPlainWidth);
        int nThreads = Runtime.getRuntime().availableProcessors();
        nThreads = nThreads < 2 ? 2 : nThreads;
        this.logger.log("Intitialize executor. Using " + nThreads + " threads to render " + name);
        executor = Executors.newFixedThreadPool(nThreads);
        @SuppressWarnings("unchecked")
        Future<Color>[][] futures = (Future<Color>[][]) (new Future[imgHeight][imgWidth]);
        this.logger.log("Starting to shoot " +
                (imgHeight * imgWidth * antiAliasingFactor * antiAliasingFactor) +
                " rays over " + name);
        for (int y = 0; y < imgHeight; ++y)
            for (int x = 0; x < imgWidth; ++x)
                futures[y][x] = calcColor(x, y);
        this.logger.log("Done shooting rays.");
        this.logger.log("Wating for results...");
        for (int y = 0; y < imgHeight; ++y)
            for (int x = 0; x < imgWidth; ++x) {
                Color color = futures[y][x].get();
                img.setRGB(x, y, color.getRGB());
            }
        executor.shutdown();
        this.logger.log("Ray tracing of " + name + " has been completed.");
        executor = null;
        this.logger = null;
        return img;
    }

    private Future<Color> calcColor(int x, int y) {
        return executor.submit(() -> {
            Point centerPoint = camera.transform(x, y);
            Point nextPoint = camera.transform(x + 1, y + 1);
            Vec pixelColor = new Vec();
            for (double i = 0; i < antiAliasingFactor; i++) {
                for (double j = 0; j < antiAliasingFactor; j++) {
                    Point nextPointPull = new Point(j / antiAliasingFactor, i / antiAliasingFactor, 0);
                    Point centerPointPull = new Point(antiAliasingFactor - j / antiAliasingFactor, antiAliasingFactor - i / antiAliasingFactor, 1);
                    Point subPixel = Ops.add( nextPoint.mult(nextPointPull),centerPoint.mult(centerPointPull));
                    Ray ray = new Ray(camera.getCameraPosition(), subPixel);
                    pixelColor = pixelColor.add(calcColor(ray, 0));
                }
            }
            return pixelColor.mult(1 / Math.pow(antiAliasingFactor, 2)).toColor();
        });
    }

    private Vec calcColor(Ray ray, int recusionLevel) {
        if (maxRecursionLevel <= recusionLevel) return new Vec();
        Hit mHit = intersection(ray);
        if (mHit == null) return backgroundColor;
        Surface surface = mHit.getSurface();
        Point pHit = ray.getHittingPoint(mHit);
        Vec pixelColor = calcMaterialColor(ray, mHit, surface, pHit);
        pixelColor = calcRefractionColor(ray, recusionLevel, mHit, surface, pHit, pixelColor);
        pixelColor = calcReflectionColor(ray, recusionLevel, mHit, surface, pHit, pixelColor);
        return pixelColor;
    }

    private Vec calcReflectionColor(Ray ray, int recusionLevel, Hit mHit, Surface surface, Point pHit, Vec pixelColor) {
        if (renderReflections) {
            Vec refIntensity = new Vec(surface.reflectionIntensity());
            Vec refDirection = Ops.reflect(ray.direction(), mHit.getNormalToSurface());
            recusionLevel = recusionLevel + 1;
            Vec refColor = calcColor(new Ray(pHit, refDirection), recusionLevel).mult(refIntensity);
            pixelColor = pixelColor.add(refColor);
        }
        return pixelColor;
    }

    private Vec calcRefractionColor(Ray ray, int recusionLevel, Hit mHit, Surface surface, Point pHit, Vec pixelColor) {
        if (renderRefarctions) {
            if (surface.isTransparent()) {
                Vec refIntensity = new Vec(surface.refractionIntensity());
                Vec refDirection = Ops.refract(ray.direction(), mHit.getNormalToSurface(), surface.n1(mHit), surface.n2(mHit));
                recusionLevel = recusionLevel + 1;
                Vec refColor = calcColor(new Ray(pHit, refDirection), recusionLevel).mult(refIntensity);
                pixelColor = pixelColor.add(refColor);
            }
        }
        return pixelColor;
    }

    private Vec calcMaterialColor(Ray ray, Hit mHit, Surface surface, Point pHit) {
        Vec pixelColor;
        Light light;
        Vec ambientColor = surface.Ka().mult(ambient);
        pixelColor = ambientColor;
        Iterator lightsIterator = lightSources.iterator();
        while (lightsIterator.hasNext()) {
            light = (Light) lightsIterator.next();
            Ray rayLight = light.rayToLight(pHit);
            if (!isOccluded(rayLight, light)) {
                Vec Il = light.intensity(pHit, rayLight);
                Vec diffuseColor = diffuse(rayLight, mHit, surface).mult(Il);
                Vec specularColor = specular(rayLight, ray, mHit, surface).mult(Il);
                pixelColor = pixelColor.add(diffuseColor).add(specularColor);
            }
        }
        return pixelColor;
    }

    private Vec specular(Ray rayLight, Ray rayCamera, Hit mHit, Surface surface) {
        int shininess = surface.shininess();
        Vec normal = mHit.getNormalToSurface();
        Vec rayLightDirection = rayLight.direction().neg();
        Vec reflectDirection = Ops.reflect(rayLightDirection, normal);
        Vec rayCameraDirection = rayCamera.direction().neg();
        double angle = reflectDirection.dot(rayCameraDirection);
        if (angle >= 0) {
            return surface.Ks().mult(Math.pow(reflectDirection.dot(rayCameraDirection), shininess));
        }
        return new Vec();
    }

    private Vec diffuse(Ray rayLight, Hit mHit, Surface surface) {
        Vec normal = mHit.getNormalToSurface();
        Vec rayLightDirection = rayLight.direction();
        return surface.Kd().mult(normal.dot(rayLightDirection));
    }

    private Hit intersection(Ray ray) {
        Surface surface;
        Hit mHit = null;
        Iterator surfacesIterator = surfaces.iterator();
        while (surfacesIterator.hasNext()) {
            surface = (Surface) surfacesIterator.next();
            Hit hit = surface.intersect(ray);
            if (hit == null) {
            } else if (mHit == null) mHit = hit;
            else if (hit.compareTo(mHit) < 0) mHit = hit;
        }
        return mHit;
    }

    private boolean isOccluded(Ray ray, Light light) {
        boolean occluded = false;
        Surface surface;
        Iterator surfacesIterator = surfaces.iterator();
        while (surfacesIterator.hasNext()) {
            surface = (Surface) surfacesIterator.next();
            if (light.isOccludedBy(surface, ray)) occluded = true;
        }
        return occluded;
    }
}
