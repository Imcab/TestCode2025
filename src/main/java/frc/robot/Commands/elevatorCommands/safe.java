package frc.robot.Commands.elevatorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.Elevator;

public class safe extends Command{
    Elevator elevator;
    double height;
    public safe(Elevator elevator, double height){
        this.elevator = elevator;
        this.height = height;

        addRequirements(elevator);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        elevator.start(height);
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
