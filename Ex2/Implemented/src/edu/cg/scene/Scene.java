package edu.cg.scene;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cg.Logger;
import edu.cg.algebra.*;
import edu.cg.scene.camera.PinholeCamera;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Surface;

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
        //TODO: initialize your additional field here.
        //      You can also change the method signature if needed.
    }

    public BufferedImage render(int imgWidth, int imgHeight, double viewPlainWidth, Logger logger)
            throws InterruptedException, ExecutionException {
        // TODO: Please notice the following comment.
        // This method is invoked each time Render Scene button is invoked.
        // Use it to initialize additional fields you need.
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
            Vec color = new Vec();
            Point centerXYPixel = camera.transform(x, y);
            Point rightDownXYPixelPoint = camera.transform(x + 1, y + 1);
            double inverseOfAnti = 1 / antiAliasingFactor;

            for (int i = 0; i <= antiAliasingFactor - 1; i++) {
                for (int j = 0; j <= antiAliasingFactor - 1; j++) {
                    Point rightDownXYPixelWeight = new Point(j, i, 0).mult(inverseOfAnti);
                    Point centerXYPixelWeight = new Point(antiAliasingFactor - j, antiAliasingFactor - i, antiAliasingFactor).mult(inverseOfAnti);
                    Point screen = Ops.add(centerXYPixel.mult(centerXYPixelWeight), rightDownXYPixelPoint.mult(rightDownXYPixelWeight));
                    Ray rayToCalc = new Ray(camera.getCameraPosition(), screen);

                    color = color.add(calcColor(rayToCalc, 0));
                }
            }
            return color.mult(1 / Math.pow(antiAliasingFactor, 2)).toColor();
        });
    }

    private Vec calcColor(Ray ray, int recursionLevel) {
        if (recursionLevel < maxRecursionLevel) {
            Hit minimalHit = intersection(ray);
            if (minimalHit == null) {
                return backgroundColor;
            }

            Point hitting = ray.getHittingPoint(minimalHit);
            Surface surface = minimalHit.getSurface();
            Vec color = surface.Ka().mult(ambient);

            for (Light light : lightSources) {
                Ray toLight = light.rayToLight(hitting);
                if (!isOccluded(light, toLight)) {
                    Vec tempColor = setDiffussion(minimalHit, toLight);
                    tempColor = tempColor.add(specular(minimalHit, toLight, ray));
                    Vec intens = light.intensity(hitting, toLight);
                    color = color.add(tempColor.mult(intens));
                }
            }

            if (renderRefarctions == true) {
                Vec refractionColor;
                if (surface.isTransparent() == true) {
                    double n1 = surface.n1(minimalHit);
                    double n2 = surface.n2(minimalHit);

                    Vec refractionDirection = Ops.refract(ray.direction(), minimalHit.getNormalToSurface(), n1, n2);
                    Vec refractionWeight = new Vec(surface.refractionIntensity());
                    refractionColor = calcColor(new Ray(hitting, refractionDirection), recursionLevel + 1).mult(refractionWeight);
                    color = color.add(refractionColor);
                }
            }

            if (renderReflections == true) {
                Vec reflectionDirection = Ops.reflect(ray.direction(), minimalHit.getNormalToSurface());
                Vec reflectionWeight = new Vec(surface.reflectionIntensity());
                Vec reflectionColor = calcColor(new Ray(hitting, reflectionDirection), recursionLevel + 1).mult(reflectionWeight);
                color = color.add(reflectionColor);
            }

            return color;
        } else {
            Vec toReturnIfBigger = new Vec();
            return toReturnIfBigger;
        }
    }

    private Vec setDiffussion(Hit minHit, Ray rayToLight) {

        Vec L = rayToLight.direction();
        Vec N = minHit.getNormalToSurface();
        Vec Kd = minHit.getSurface().Kd();

        Vec toReturn = Kd.mult(Math.max(N.dot(L), 0.0));
        return toReturn;

    }

    private Vec specular(Hit minHit, Ray rayToLight, Ray rayFromViewer) {

        Vec L = rayToLight.direction();
        Vec N = minHit.getNormalToSurface();
        Vec R = Ops.reflect(L.neg(), N);
        Vec Ks = minHit.getSurface().Ks();
        Vec v = rayFromViewer.direction();

        int shininess = minHit.getSurface().shininess();
        double dot = R.dot(v.neg());

        if (dot < 0.0) {
            return new Vec();
        } else {
            return Ks.mult(Math.pow(dot, shininess));
        }
    }

    private boolean isOccluded(Light light, Ray rayToLight) {

        Boolean isOcluded = false;

        for (Surface surface : surfaces) {
            if (light.isOccludedBy(surface, rayToLight)) {
                isOcluded = true;
                break;
            }
        }

        return isOcluded;
    }

    private Hit intersection(Ray ray) {

        Hit minimalHit = null;

        for (Surface surface : surfaces) {
            Hit newHit = surface.intersect(ray);
            if (minimalHit == null || (newHit != null && newHit.compareTo(minimalHit) < 0)) {
                minimalHit = newHit;
            }
        }

        return minimalHit;
    }
}