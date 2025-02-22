package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class retract extends Command{
    Elevator elevator;
    CoralWrist coral;
    public retract(Elevator elevator, CoralWrist coral){
        this.elevator = elevator;
        this.coral = coral;
  
        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        
        elevator.runMotion(0.7);
        coral.requestPosition(0.1);

    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
        coral.stop();
    }


    @Override
    public boolean isFinished(){
        
        return false;
    }
}
