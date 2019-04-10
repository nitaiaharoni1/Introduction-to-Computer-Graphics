// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.scene.objects.Sphere;
import edu.cg.scene.objects.AxisAlignedRectangle;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Shape;
import edu.cg.scene.lightSources.Spotlight;
import edu.cg.scene.lightSources.PointLight;
import edu.cg.scene.lightSources.DirectionalLight;
import edu.cg.scene.objects.Surface;
import edu.cg.scene.objects.Material;
import edu.cg.scene.objects.Plain;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.scene.Scene;

public class Scenes
{
    public static Scene scene1() {
        final Shape plainShape = new Plain(new Vec(0.0, 0.0, 1.0), new Point(0.0, 0.0, 0.0));
        final Material plainMaterial = Material.getMetalMaterial();
        final Surface plainSurface = new Surface(plainShape, plainMaterial);
        final Light dirLight = new DirectionalLight().initDirection(new Vec(0.0, 0.0, -1.0)).initIntensity(new Vec(0.5));
        final Light pointLight = new PointLight().initIntensity(new Vec(0.9)).initPosition(new Point(2.5, 0.0, 1.0)).initDecayFactors(0.1, 0.05, 1.0);
        final Light spotLight = new Spotlight().initIntensity(new Vec(0.9)).initPosition(new Point(-2.5, 0.0, 1.0)).initDecayFactors(0.1, 0.05, 1.0).initDirection(new Vec(-0.1, -0.1, -0.1));
        return new Scene().initAmbient(new Vec(0.0)).initCamera(new Point(0.0, 6.0, 1.0), new Vec(0.0, -0.8, -0.2), new Vec(0.0, -0.5, 0.5), 2.0).addLightSource(dirLight).addLightSource(pointLight).addLightSource(spotLight).addSurface(plainSurface).initName("scene1").initAntiAliasingFactor(1).initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(3);
    }
    
    public static Scene scene2() {
        final Shape boxShape1 = new AxisAlignedRectangle(new Point(0.0, 0.0, 0.0), new Point(1.0, 1.0, 1.0));
        final Material boxMat1 = new Material().initKa(new Vec(0.8, 0.05, 0.05)).initKd(new Vec(0.0)).initKs(new Vec(0.9)).initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0);
        final Surface boxSurface1 = new Surface(boxShape1, boxMat1);
        final Light dirLight = new DirectionalLight().initDirection(new Vec(-1.0, -1.0, -1.0)).initIntensity(new Vec(0.9));
        final Light pointLight1 = new PointLight().initIntensity(new Vec(0.7)).initPosition(new Point(2.0, 2.0, 1.5));
        final Light pointLight2 = new PointLight().initIntensity(new Vec(0.7)).initPosition(new Point(2.0, 1.0, 0.5));
        return new Scene().initAmbient(new Vec(1.0)).initCamera(new Point(4.0, 4.0, 1.5), new Vec(-1.0, -1.0, -0.3), new Vec(0.0, 0.0, 1.0), 3.0).addLightSource(dirLight).addLightSource(pointLight1).addLightSource(pointLight2).addSurface(boxSurface1).initName("scene2").initAntiAliasingFactor(1).initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(3);
    }
    
    public static Scene scene3() {
        final Shape boxShape1 = new AxisAlignedRectangle(new Point(-0.5, 0.0, 0.0), new Point(0.5, 1.0, 1.0));
        final Material boxMat1 = new Material().initKa(new Vec(0.01)).initKd(new Vec(0.03)).initKs(new Vec(0.03)).initIsTransparent(true).initRefractionIntensity(1.0);
        final Surface boxSurface1 = new Surface(boxShape1, boxMat1);
        final Shape boxShape2 = new AxisAlignedRectangle(new Point(0.0, 0.0, 0.0), new Point(1.0, -1.0, 1.0));
        final Material boxMat2 = new Material().initKa(new Vec(0.5, 0.0, 0.0)).initKd(new Vec(0.1)).initKs(new Vec(0.1)).initIsTransparent(true).initRefractionIntensity(0.1);
        final Surface boxSurface2 = new Surface(boxShape2, boxMat2);
        final Shape boxShape3 = new AxisAlignedRectangle(new Point(0.0, 0.0, 0.0), new Point(-1.0, -1.0, 1.0));
        final Material boxMat3 = new Material().initKa(new Vec(0.0, 0.0, 0.5)).initKd(new Vec(0.1)).initKs(new Vec(0.1)).initIsTransparent(true).initRefractionIntensity(0.1);
        final Surface boxSurface3 = new Surface(boxShape3, boxMat3);
        final Light dirLight = new DirectionalLight().initDirection(new Vec(0.0, -1.0, -1.0)).initIntensity(new Vec(0.7, 0.5, 0.0));
        return new Scene().initAmbient(new Vec(1.0)).initCamera(new Point(0.0, 6.0, 0.8), new Vec(0.0, -1.0, 0.0), new Vec(0.0, 0.0, -0.2), 3.0).addLightSource(dirLight).addSurface(boxSurface1).addSurface(boxSurface2).addSurface(boxSurface3).initName("scene3").initAntiAliasingFactor(1).initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
    }
    
    public static Scene scene4() {
        final int pyramidHeight = 4;
        final int boxHeight = 1;
        final int boxWidth = 1;
        final int boxDepth = 1;
        final Scene pyramidScence = new Scene();
        pyramidScence.initName("Scene4");
        pyramidScence.initAmbient(new Vec(0.33));
        pyramidScence.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
        final Point cameraPosition = new Point(2 * boxWidth * Math.pow(pyramidHeight + 1, 2.0), 2 * boxDepth * Math.pow(pyramidHeight + 1, 2.0), boxHeight * pyramidHeight);
        final Vec towardsVec = new Vec(-1.0, -1.0, 0.0);
        final Vec upVec = new Vec(0.0, 0.0, 1.0);
        final double distanceFromPlain = 0.25 * cameraPosition.dist(new Point(0.0, 0.0, 0.0));
        pyramidScence.initCamera(cameraPosition, towardsVec, upVec, distanceFromPlain);
        final Light dirLight = new DirectionalLight().initDirection(new Vec(-0.5, -0.5, -1.0)).initIntensity(new Vec(0.5));
        final PointLight pointLight1 = new PointLight().initPosition(new Point(12.0, 4.0, 6.0)).initIntensity(new Vec(1.0));
        final PointLight pointLight2 = new PointLight().initPosition(new Point(4.0, 12.0, 6.0)).initIntensity(new Vec(1.0));
        final PointLight pointLight3 = new PointLight().initPosition(new Point(-12.0, -20.0, 6.0)).initIntensity(new Vec(1.0));
        pyramidScence.addLightSource(dirLight);
        pyramidScence.addLightSource(pointLight1);
        pyramidScence.addLightSource(pointLight2);
        pyramidScence.addLightSource(pointLight3);
        for (int currentHeight = 0; currentHeight < pyramidHeight; ++currentHeight) {
            final int numOfBoxes = (int)Math.pow(2.0, pyramidHeight - 1) - 2 * currentHeight;
            final int offsetX = currentHeight * boxWidth;
            final int offsetY = currentHeight * boxDepth;
            for (int i = 0; i < numOfBoxes; ++i) {
                for (int j = 0; j < numOfBoxes; ++j) {
                    final Shape boxShape = new AxisAlignedRectangle(new Point(offsetX + i * boxWidth, offsetY + j * boxDepth, boxHeight * currentHeight), new Point(offsetX + (i + 1) * boxWidth, offsetY + (j + 1) * boxDepth, (currentHeight + 1) * boxHeight));
                    final Material boxMat = Material.getRandomMaterial();
                    final Surface boxSurface = new Surface(boxShape, boxMat);
                    pyramidScence.addSurface(boxSurface);
                }
            }
        }
        final Plain scenePlain = new Plain(new Vec(0.0, 0.0, 1.0), new Point(0.0, 0.0, 0.0));
        final Material plainMat = Material.getGlassMaterial(false).initReflectionIntensity(0.4);
        final Surface plainSurface = new Surface(scenePlain, plainMat);
        pyramidScence.addSurface(plainSurface);
        return pyramidScence;
    }
    
    public static Scene scene5() {
        final Shape sphereShape1 = new Sphere(new Point(0.5, 0.5, 0.5), 0.5);
        final Material sphereMat1 = Material.getRandomMaterial();
        final Surface sphereSurface1 = new Surface(sphereShape1, sphereMat1);
        final Shape sphereShape2 = new Sphere(new Point(-0.5, 0.5, 0.5), 0.5);
        final Material sphereMat2 = Material.getRandomMaterial();
        final Surface sphereSurface2 = new Surface(sphereShape2, sphereMat2);
        final Shape sphereShape3 = new Sphere(new Point(0.5, -0.5, 0.5), 0.5);
        final Material sphereMat3 = Material.getRandomMaterial();
        final Surface sphereSurface3 = new Surface(sphereShape3, sphereMat3);
        final Shape sphereShape4 = new Sphere(new Point(-0.5, -0.5, 0.5), 0.5);
        final Material sphereMat4 = Material.getRandomMaterial();
        final Surface sphereSurface4 = new Surface(sphereShape4, sphereMat4);
        final Light dirLight = new DirectionalLight().initDirection(new Vec(0.0, 0.0, -1.0)).initIntensity(new Vec(0.7));
        return new Scene().initAmbient(new Vec(1.0)).initCamera(new Point(0.0, 0.0, 2.0), new Vec(0.0, 0.0, -1.0), new Vec(1.0, 1.0, 0.0), 1.0).addLightSource(dirLight).addSurface(sphereSurface1).addSurface(sphereSurface2).addSurface(sphereSurface3).addSurface(sphereSurface4).initName("scene5").initAntiAliasingFactor(1).initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
    }
}
