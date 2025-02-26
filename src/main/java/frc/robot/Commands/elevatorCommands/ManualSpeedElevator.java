package frc.robot.Commands.elevatorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.Elevator;

public class ManualSpeedElevator extends Command{
    Elevator elevator;
    double speed;
    public ManualSpeedElevator(Elevator elevator, double speed){
        this.elevator = elevator;
        this.speed = speed;
    
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute(){
        elevator.runSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return elevator.pressed();
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop(); 
    }
}
