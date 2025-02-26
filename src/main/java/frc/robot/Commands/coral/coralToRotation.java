package frc.robot.Commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.CoralWrist;

public class coralToRotation extends Command{
    CoralWrist coral;
    double rotation;
    public coralToRotation(CoralWrist coral, double rotation){
        this.coral = coral;
        this.rotation = rotation;

        addRequirements(coral);
    }

    @Override
    public void initialize () {}

    @Override
    public void execute (){
        coral.requestPosition(rotation);
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
