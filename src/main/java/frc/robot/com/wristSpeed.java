package frc.robot.com;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.WristConstants.Coral;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class wristSpeed extends Command{
    CoralWrist y;
    double  speed;
    public wristSpeed(CoralWrist y, double speed){
        this.y = y;

        this.speed = speed;
        addRequirements(y);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute(){
        y.wheelSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        y.stop();
    }
}
