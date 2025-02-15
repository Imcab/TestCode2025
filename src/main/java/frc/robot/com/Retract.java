package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.util.Domain;

public class Retract extends Command{
    Elevator elevator;
    CoralWrist wrist;
    public Domain elevadorInRange;
    public Domain wristInRange;
    boolean isFinished = false;
    public Retract(Elevator elevator, CoralWrist wrist){
        this.elevator = elevator;
        this.wrist = wrist;
        elevadorInRange = new Domain(63, 68);
        wristInRange = new Domain(-1, 1);

        addRequirements(elevator, wrist);
    }

    @Override
    public void initialize(){

        isFinished = false;
     
    
    }
    @Override
    public void execute(){
        
        wrist.requestPosition(0);

        elevator.runRequest(66.5);
            
        if (elevadorInRange.inRange(elevator.getCentimeters())){
            isFinished = true;
            
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
        elevator.stop();
    }

     @Override
    public boolean isFinished(){

        return isFinished;
    }
}
