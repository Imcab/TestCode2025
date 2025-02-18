package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;

public class parallelCoral extends Command{
    CoralWrist wrist;


    public parallelCoral(CoralWrist wrist){
        this.wrist = wrist;
        addRequirements(wrist);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute(){
        wrist.requestPosition(1.5);
        wrist.wheelSpeed(0.3);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
    }
}
