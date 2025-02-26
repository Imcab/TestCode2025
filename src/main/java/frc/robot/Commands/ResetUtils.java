package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;

public class ResetUtils {

    public static Command resetElevatorEncoders(Elevator elevator){
        return Commands.runOnce(()-> elevator.resetEncoders(), elevator);
    }

    public static Command resetWristEncoder(CoralWrist wrist){
        return Commands.runOnce(()-> wrist.setZero(), wrist);
    }
}
