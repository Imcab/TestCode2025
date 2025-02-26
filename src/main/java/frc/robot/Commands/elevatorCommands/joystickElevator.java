package frc.robot.Commands.elevatorCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.Elevator;
public class joystickElevator extends Command{
    Elevator elevator;
    DoubleSupplier joyStickSupplier;
    public joystickElevator(Elevator elevator, DoubleSupplier joystickSupplier){
        this.elevator = elevator;
        this.joyStickSupplier = joystickSupplier;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute(){
        elevator.runSpeed(joyStickSupplier.getAsDouble());

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }
}
