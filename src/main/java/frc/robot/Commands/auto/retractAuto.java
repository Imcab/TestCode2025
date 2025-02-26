package frc.robot.Commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;

public class retractAuto extends Command{
    Elevator elevator;
    CoralWrist coral;
    public retractAuto(Elevator elevator, CoralWrist coral){
        this.elevator = elevator;
        this.coral = coral;  
        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        
        elevator.runMotion(0.63);
        coral.requestPosition(0.02);

    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
        coral.stop();
    }

    @Override
    public boolean isFinished(){     
        return elevator.RelativeMeters() <= 0.64;
    }
}
