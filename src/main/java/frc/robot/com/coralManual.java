package frc.robot.com;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;

public class coralManual extends Command {
    CoralWrist coral;
    DoubleSupplier y;

    public coralManual(CoralWrist coral, DoubleSupplier y){
        this.coral = coral;
        this.y = y;

        addRequirements(coral);
    }
    
    @Override
    public void initialize () {}

    @Override
    public void execute (){
        double c = y.getAsDouble();
        coral.setSpeed(c*-0.2);
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
