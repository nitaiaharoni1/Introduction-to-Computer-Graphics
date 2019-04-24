package edu.cg.scene;

import edu.cg.Logger;
import edu.cg.algebra.Point;
import edu.cg.algebra.*;
import edu.cg.scene.camera.PinholeCamera;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Surface;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    //Todo: Change - This is the original func:
//    private Future<Color> calcColor(int x, int y) {
//        return executor.submit(() -> {
//            // TODO: You need to re-implement this method if you want to handle
//            //       super-sampling. You're also free to change the given implementation as you like.
//            Point centerPoint = camera.transform(x, y);
//            Ray ray = new Ray(camera.getCameraPosition(), centerPoint);
//            Vec color = calcColor(ray, 0);
//            return color.toColor();
//        });
//    }
    private Future<Color> calcColor(int x, int y) {
        return executor.submit(() -> {
            Point centerXYPixel = camera.transform(x, y);
            Point rightDownXYPixelPoint = camera.transform(x + 1, y + 1);
            Vec color = new Vec();
            for (int i = 0; i < antiAliasingFactor; i++) {
                for (int j = 0; j < antiAliasingFactor; j++) {
                    Point centerXYPixelPointWeight = new Point(antiAliasingFactor - j, antiAliasingFactor - i, antiAliasingFactor).mult(1 / antiAliasingFactor);
                    Point rightDownXYPixelPointWeight = new Point(j, i, 0).mult(1 / antiAliasingFactor);
                    Point screenPixel = Ops.add(centerXYPixel.mult(centerXYPixelPointWeight), rightDownXYPixelPoint.mult(rightDownXYPixelPointWeight));
                    Ray ray = new Ray(camera.getCameraPosition(), screenPixel);
                    color = color.add(calcColor(ray, 0));
                }
            }
            return color.mult(1 / Math.pow(antiAliasingFactor, 2)).toColor();
        });
    }

    //Todo: Change according to lecture videos
    private Vec calcColor(Ray ray, int recusionLevel) {
        if (recusionLevel >= maxRecursionLevel) {
            return new Vec();
        } else {
            Hit minHit = intersection(ray);
            if (minHit == null) {
                return backgroundColor;
            }
            Point hittingPoint = ray.getHittingPoint(minHit);
            Surface surface = minHit.getSurface();
            Vec color = surface.Ka().mult(ambient);
            for (Light light : lightSources) {
                Ray rayToLight = light.rayToLight(hittingPoint);
                if (!isOccluded(light, rayToLight)) {
                    Vec tmpColor = diffuse(minHit, rayToLight);
                    tmpColor = tmpColor.add(specular(minHit, rayToLight, ray));
                    Vec Il = light.intensity(hittingPoint, rayToLight);
                    color = color.add(tmpColor.mult(Il));
                }
            }
            if (renderReflections) {
                Vec reflectionDirection = Ops.reflect(ray.direction(), minHit.getNormalToSurface());
                Vec reflectionWeight = new Vec(surface.reflectionIntensity());
                Vec reflectionColor = calcColor(new Ray(hittingPoint, reflectionDirection), recusionLevel + 1).mult(reflectionWeight);
                color = color.add(reflectionColor);
            }
            if (renderRefarctions) {
                Vec refractionColor = new Vec();
                if (surface.isTransparent()) {
                    double n1 = surface.n1(minHit);
                    double n2 = surface.n2(minHit);
                    Vec refractionDirection = Ops.refract(ray.direction(), minHit.getNormalToSurface(), n1, n2);
                    Vec refractionWeight = new Vec(surface.refractionIntensity());
                    refractionColor = calcColor(new Ray(hittingPoint, refractionDirection), recusionLevel + 1).mult(refractionWeight);
                    color = color.add(refractionColor);
                }
            }
            return color;
        }
    }

    //Todo: Change according to lecture videos
    private Vec diffuse(Hit minHit, Ray rayToLight) {
        Vec L = rayToLight.direction();
        Vec N = minHit.getNormalToSurface();
        Vec Kd = minHit.getSurface().Kd();
        return Kd.mult(Math.max(N.dot(L), 0.0));
    }

    //Todo: Change according to lecture videos
    private Vec specular(Hit minHit, Ray rayToLight, Ray rayFromViewer) {
        Vec L = rayToLight.direction();
        Vec N = minHit.getNormalToSurface();
        Vec R = Ops.reflect(L.neg(), N);
        Vec Ks = minHit.getSurface().Ks();
        Vec v = rayFromViewer.direction();
        int shininess = minHit.getSurface().shininess();
        double dot = R.dot(v.neg());
        return (dot < 0.0) ? new Vec() : Ks.mult(Math.pow(dot, shininess));
    }

    //Todo: Change
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

    //Todo: Change
    private Hit intersection(Ray ray) {
        Hit minHit = null;
        for (Surface surface : surfaces) {
            Hit newHit = surface.intersect(ray);
            if (minHit == null || (newHit != null && newHit.compareTo(minHit) < 0)) {
                minHit = newHit;
            }
        }
        return minHit;
    }
}