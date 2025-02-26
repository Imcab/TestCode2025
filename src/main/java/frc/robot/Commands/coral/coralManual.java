package frc.robot.Commands.coral;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.CoralWrist;

public class coralManual extends Command {
    CoralWrist coral;
    DoubleSupplier joystickSupplier;

    public coralManual(CoralWrist coral, DoubleSupplier joyStickSupplier){
        this.coral = coral;
        this.joystickSupplier = joyStickSupplier;

        addRequirements(coral);
    }
    
    @Override
    public void initialize () {}

    @Override
    public void execute (){
        coral.setSpeed(joystickSupplier.getAsDouble());
    }

    @Override
    public void end (boolean interrupted) {
       coral.stop();
    }
    @Override
    public boolean isFinished(){
        return false;
    }


}
