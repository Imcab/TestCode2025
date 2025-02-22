package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class safe extends Command{
    Elevator elevator;
    public safe(Elevator elevator){
        this.elevator = elevator;

        addRequirements(elevator);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        
        elevator.start(0.7);
    
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }


    @Override
    public boolean isFinished(){
        
        return false;
    }
}
