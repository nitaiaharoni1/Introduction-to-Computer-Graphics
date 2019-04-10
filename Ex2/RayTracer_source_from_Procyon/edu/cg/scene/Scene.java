// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene;

import java.util.Iterator;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Ops;
import java.util.concurrent.ExecutionException;
import java.awt.Color;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;
import edu.cg.algebra.Point;
import java.util.LinkedList;
import edu.cg.Logger;
import java.util.concurrent.ExecutorService;
import edu.cg.scene.objects.Surface;
import edu.cg.scene.lightSources.Light;
import java.util.List;
import edu.cg.algebra.Vec;
import edu.cg.scene.camera.PinholeCamera;

public class Scene
{
    private String name;
    private int maxRecursionLevel;
    private int antiAliasingFactor;
    private boolean renderRefarctions;
    private boolean renderReflections;
    private PinholeCamera camera;
    private Vec ambient;
    private Vec backgroundColor;
    private List<Light> lightSources;
    private List<Surface> surfaces;
    private transient ExecutorService executor;
    private transient Logger logger;
    
    public Scene() {
        this.name = "scene";
        this.maxRecursionLevel = 1;
        this.antiAliasingFactor = 1;
        this.renderRefarctions = false;
        this.renderReflections = false;
        this.ambient = new Vec(1.0, 1.0, 1.0);
        this.backgroundColor = new Vec(0.0, 0.5, 1.0);
        this.lightSources = new LinkedList<Light>();
        this.surfaces = new LinkedList<Surface>();
        this.executor = null;
        this.logger = null;
    }
    
    public Scene initCamera(final Point eyePoistion, final Vec towardsVec, final Vec upVec, final double distanceToPlain) {
        this.camera = new PinholeCamera(eyePoistion, towardsVec, upVec, distanceToPlain);
        return this;
    }
    
    public Scene initAmbient(final Vec ambient) {
        this.ambient = ambient;
        return this;
    }
    
    public Scene initBackgroundColor(final Vec backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public Scene addLightSource(final Light lightSource) {
        this.lightSources.add(lightSource);
        return this;
    }
    
    public Scene addSurface(final Surface surface) {
        this.surfaces.add(surface);
        return this;
    }
    
    public Scene initMaxRecursionLevel(final int maxRecursionLevel) {
        this.maxRecursionLevel = maxRecursionLevel;
        return this;
    }
    
    public Scene initAntiAliasingFactor(final int antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        return this;
    }
    
    public Scene initName(final String name) {
        this.name = name;
        return this;
    }
    
    public Scene initRenderRefarctions(final boolean renderRefarctions) {
        this.renderRefarctions = renderRefarctions;
        return this;
    }
    
    public Scene initRenderReflections(final boolean renderReflections) {
        this.renderReflections = renderReflections;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getFactor() {
        return this.antiAliasingFactor;
    }
    
    public int getMaxRecursionLevel() {
        return this.maxRecursionLevel;
    }
    
    public boolean getRenderRefarctions() {
        return this.renderRefarctions;
    }
    
    public boolean getRenderReflections() {
        return this.renderReflections;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Camera: " + this.camera + endl + "Ambient: " + this.ambient + endl + "Background Color: " + this.backgroundColor + endl + "Max recursion level: " + this.maxRecursionLevel + endl + "Anti aliasing factor: " + this.antiAliasingFactor + endl + "Light sources:" + endl + this.lightSources + endl + "Surfaces:" + endl + this.surfaces;
    }
    
    private void initSomeFields(final int imgWidth, final int imgHeight, final Logger logger) {
        this.logger = logger;
    }
    
    public BufferedImage render(final int imgWidth, final int imgHeight, final double viewPlainWidth, final Logger logger) throws InterruptedException, ExecutionException {
        this.initSomeFields(imgWidth, imgHeight, logger);
        final BufferedImage img = new BufferedImage(imgWidth, imgHeight, 1);
        this.camera.initResolution(imgHeight, imgWidth, viewPlainWidth);
        int nThreads = Runtime.getRuntime().availableProcessors();
        nThreads = ((nThreads < 2) ? 2 : nThreads);
        this.logger.log("Intitialize executor. Using " + nThreads + " threads to render " + this.name);
        this.executor = Executors.newFixedThreadPool(nThreads);
        final Future[][] futures = new Future[imgHeight][imgWidth];
        this.logger.log("Starting to shoot " + imgHeight * imgWidth * this.antiAliasingFactor * this.antiAliasingFactor + " rays over " + this.name);
        for (int y = 0; y < imgHeight; ++y) {
            for (int x = 0; x < imgWidth; ++x) {
                futures[y][x] = this.calcColor(x, y);
            }
        }
        this.logger.log("Done shooting rays.");
        this.logger.log("Wating for results...");
        for (int y = 0; y < imgHeight; ++y) {
            for (int x = 0; x < imgWidth; ++x) {
                final Color color = futures[y][x].get();
                img.setRGB(x, y, color.getRGB());
            }
        }
        this.executor.shutdown();
        this.logger.log("Ray tracing of " + this.name + " has been completed.");
        this.executor = null;
        this.logger = null;
        return img;
    }
    
    private Future<Color> calcColor(final int x, final int y) {
        final Point leftUp;
        final Point rightDown;
        Vec color;
        int i;
        int j;
        Point leftUpWeight;
        Point rightDownWeight;
        Point pointOnScreenPlain;
        Ray ray;
        return this.executor.submit(() -> {
            leftUp = this.camera.transform(x, y);
            rightDown = this.camera.transform(x + 1, y + 1);
            color = new Vec();
            for (i = 0; i < this.antiAliasingFactor; ++i) {
                for (j = 0; j < this.antiAliasingFactor; ++j) {
                    leftUpWeight = new Point(this.antiAliasingFactor - j, this.antiAliasingFactor - i, this.antiAliasingFactor).mult(1.0 / this.antiAliasingFactor);
                    rightDownWeight = new Point(j, i, 0.0).mult(1.0 / this.antiAliasingFactor);
                    pointOnScreenPlain = Ops.add(leftUp.mult(leftUpWeight), rightDown.mult(rightDownWeight));
                    ray = new Ray(this.camera.getCameraPosition(), pointOnScreenPlain);
                    color = color.add(this.calcColor(ray, 0));
                }
            }
            return color.mult(1.0 / (this.antiAliasingFactor * this.antiAliasingFactor)).toColor();
        });
    }
    
    private Vec calcColor(final Ray ray, final int recusionLevel) {
        if (recusionLevel >= this.maxRecursionLevel) {
            return new Vec();
        }
        final Hit minHit = this.intersection(ray);
        if (minHit == null) {
            return this.backgroundColor;
        }
        final Point hittingPoint = ray.getHittingPoint(minHit);
        final Surface surface = minHit.getSurface();
        Vec color = surface.Ka().mult(this.ambient);
        for (final Light light : this.lightSources) {
            final Ray rayToLight = light.rayToLight(hittingPoint);
            if (!this.isOccluded(light, rayToLight)) {
                Vec tmpColor = this.diffuse(minHit, rayToLight);
                tmpColor = tmpColor.add(this.specular(minHit, rayToLight, ray));
                final Vec Il = light.intensity(hittingPoint, rayToLight);
                color = color.add(tmpColor.mult(Il));
            }
        }
        if (this.renderReflections) {
            final Vec reflectionDirection = Ops.reflect(ray.direction(), minHit.getNormalToSurface());
            final Vec reflectionWeight = new Vec(surface.reflectionIntensity());
            final Vec reflectionColor = this.calcColor(new Ray(hittingPoint, reflectionDirection), recusionLevel + 1).mult(reflectionWeight);
            color = color.add(reflectionColor);
        }
        if (this.renderRefarctions) {
            Vec refractionColor = new Vec();
            if (surface.isTransparent()) {
                final double n1 = surface.n1(minHit);
                final double n2 = surface.n2(minHit);
                final Vec refractionDirection = Ops.refract(ray.direction(), minHit.getNormalToSurface(), n1, n2);
                final Vec refractionWeight = new Vec(surface.refractionIntensity());
                refractionColor = this.calcColor(new Ray(hittingPoint, refractionDirection), recusionLevel + 1).mult(refractionWeight);
                color = color.add(refractionColor);
            }
        }
        return color;
    }
    
    private Vec diffuse(final Hit minHit, final Ray rayToLight) {
        final Vec L = rayToLight.direction();
        final Vec N = minHit.getNormalToSurface();
        final Vec Kd = minHit.getSurface().Kd();
        return Kd.mult(Math.max(N.dot(L), 0.0));
    }
    
    private Vec specular(final Hit minHit, final Ray rayToLight, final Ray rayFromViewer) {
        final Vec L = rayToLight.direction();
        final Vec N = minHit.getNormalToSurface();
        final Vec R = Ops.reflect(L.neg(), N);
        final Vec Ks = minHit.getSurface().Ks();
        final Vec v = rayFromViewer.direction();
        final int shininess = minHit.getSurface().shininess();
        final double dot = R.dot(v.neg());
        return (dot < 0.0) ? new Vec() : Ks.mult(Math.pow(dot, shininess));
    }
    
    private boolean isOccluded(final Light light, final Ray rayToLight) {
        for (final Surface surface : this.surfaces) {
            if (light.isOccludedBy(surface, rayToLight)) {
                return true;
            }
        }
        return false;
    }
    
    private Hit intersection(final Ray ray) {
        Hit minHit = null;
        for (final Surface surface : this.surfaces) {
            final Hit newHit = surface.intersect(ray);
            if (minHit == null || (newHit != null && newHit.compareTo(minHit) < 0)) {
                minHit = newHit;
            }
        }
        return minHit;
    }
}
