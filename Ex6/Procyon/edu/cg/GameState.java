// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.algebra.Vec;

import java.util.Timer;
import java.util.TimerTask;

public class GameState
{
    private SteeringState steeringState;
    private AccelarationState accelearationState;
    private double carVelocity;
    private Vec nextTranslation;
    private Timer timer;
    private final long TIMER_INTERVAL_MS = 8L;
    private final double MAX_ROTATION = 20.0;
    private final double MAX_TRANSLATION_X = 5.0;
    private final double MAX_VELOCITY = 115.0;
    private final double CAR_ACCELRATION = 30.0;
    private static /* synthetic */ int[] $SWITCH_TABLE$edu$cg$GameState$AccelarationState;
    private static /* synthetic */ int[] $SWITCH_TABLE$edu$cg$GameState$SteeringState;
    
    public GameState() {
        this.steeringState = SteeringState.STRAIGHT;
        this.accelearationState = AccelarationState.CRUISE;
        this.carVelocity = 0.0;
        this.nextTranslation = new Vec(0.0, 0.0, 0.0);
        (this.timer = new Timer()).schedule(new UpdateTranslation(), 0L, 8L);
    }
    
    private synchronized double getCarVelocity() {
        return this.carVelocity;
    }
    
    private synchronized void updateCarVelocity(final double newVelocity) {
        this.carVelocity = Math.max(0.0, newVelocity);
        this.carVelocity = Math.min(115.0, this.carVelocity);
    }
    
    private synchronized double getCarAccelaration() {
        switch ($SWITCH_TABLE$edu$cg$GameState$AccelarationState()[this.accelearationState.ordinal()]) {
            case 1: {
                return 30.0;
            }
            case 2: {
                return -60.0;
            }
            case 3: {
                return -150.0;
            }
            default: {
                return 0.0;
            }
        }
    }
    
    public synchronized double getCarRotation() {
        switch ($SWITCH_TABLE$edu$cg$GameState$SteeringState()[this.steeringState.ordinal()]) {
            case 1: {
                return -20.0;
            }
            case 3: {
                return 20.0;
            }
            case 2: {
                return 0.0;
            }
            default: {
                return 0.0;
            }
        }
    }
    
    public synchronized void updateSteering(final SteeringState newState) {
        this.steeringState = newState;
    }
    
    public synchronized void updateAccelaration(final AccelarationState newState) {
        this.accelearationState = newState;
    }
    
    private synchronized void updateNextTranslation(final Vec deltaTranslation) {
        this.nextTranslation = this.nextTranslation.add(deltaTranslation);
        this.nextTranslation.x = (float)Math.max(this.nextTranslation.x, -5.0);
        this.nextTranslation.x = (float)Math.min(this.nextTranslation.x, 5.0);
    }
    
    public synchronized Vec getNextTranslation() {
        final Vec retVal = new Vec(this.nextTranslation);
        this.nextTranslation = new Vec(0.0);
        return retVal;
    }
    
    static /* synthetic */ int[] $SWITCH_TABLE$edu$cg$GameState$AccelarationState() {
        final int[] $switch_TABLE$edu$cg$GameState$AccelarationState = GameState.$SWITCH_TABLE$edu$cg$GameState$AccelarationState;
        if ($switch_TABLE$edu$cg$GameState$AccelarationState != null) {
            return $switch_TABLE$edu$cg$GameState$AccelarationState;
        }
        final int[] array = new int[AccelarationState.values().length];
        try {
            array[AccelarationState.BREAKS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            array[AccelarationState.CRUISE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            array[AccelarationState.GAS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return array;
    }
    
    static /* synthetic */ int[] $SWITCH_TABLE$edu$cg$GameState$SteeringState() {
        final int[] $switch_TABLE$edu$cg$GameState$SteeringState = GameState.$SWITCH_TABLE$edu$cg$GameState$SteeringState;
        if ($switch_TABLE$edu$cg$GameState$SteeringState != null) {
            return $switch_TABLE$edu$cg$GameState$SteeringState;
        }
        final int[] array = new int[SteeringState.values().length];
        try {
            array[SteeringState.LEFT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            array[SteeringState.RIGHT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            array[SteeringState.STRAIGHT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return array;
    }
    
    public enum AccelarationState
    {
        GAS("GAS", 0), 
        CRUISE("CRUISE", 1), 
        BREAKS("BREAKS", 2);
        
        private AccelarationState(final String s, final int n) {
        }
    }
    
    public enum SteeringState
    {
        LEFT("LEFT", 0), 
        STRAIGHT("STRAIGHT", 1), 
        RIGHT("RIGHT", 2);
        
        private SteeringState(final String s, final int n) {
        }
    }
    
    class UpdateTranslation extends TimerTask
    {
        @Override
        public void run() {
            final double theta = GameState.this.getCarRotation() * 3.141592653589793 / 180.0;
            final double cosTheta = Math.cos(theta);
            final double sinTheta = Math.sin(theta);
            final double currentCarVelocity = GameState.this.getCarVelocity();
            double currentCarAccelaration = GameState.this.getCarAccelaration();
            currentCarAccelaration += -0.1 * Math.abs(sinTheta) * 30.0;
            final double dt = 0.008;
            final double dr = currentCarVelocity * dt + currentCarAccelaration * dt * dt;
            final double dz = Math.min(0.0, -cosTheta * dr);
            final double dx = sinTheta * dr;
            final double newVelocity = Math.min(115.0, currentCarVelocity + cosTheta * currentCarAccelaration * dt);
            GameState.this.updateCarVelocity(newVelocity);
            GameState.this.updateNextTranslation(new Vec(dx, 0.0, dz));
        }
    }
}
