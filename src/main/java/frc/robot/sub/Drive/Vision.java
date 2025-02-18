package frc.robot.sub.Drive;

import frc.robot.util.Limelight;
import frc.robot.util.PoseObservation;

public class Vision {
    public Limelight limelight;
    public boolean Limerequest = false;
    private boolean loop_ONCE = false;

    public Vision(){

        limelight = new Limelight();

    }

    //Periodic method to call 
    public void periodic(){

        //Loop-Once per loop
        loop_ONCE = false;

        if (!loop_ONCE) {
            
            limelight.update();
            //BL_cam.update();
            //BR_cam.update();

            loop_ONCE = true;

        }

        if (limelight.hasTarget() && !limeIsRequested()) {
            limelight.blink();
        }else if (limelight.hasTarget() && limeIsRequested()) {
            limelight.LedOn();
        }else{
            limelight.LedOff();
        }


    }
 
    public PoseObservation observationLime(){
        return limelight.getObservation();
    }
    public void limeRequest(boolean toggle){
        Limerequest = toggle;
    }
    public boolean limeIsRequested(){
        return Limerequest;
    }
    public boolean lime_hasResults(){
        return limelight.hasResults();
    }
    public double aim(){
        return limelight.aimAngular();
    }
    public double range(){
        return limelight.rangeForward();
    }

}
