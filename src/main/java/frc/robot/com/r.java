package frc.robot.com;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.WristConstants.Coral;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class r extends Command{
    CoralWrist y;
    double degrees, speed;
    public r(CoralWrist y, double degrees, double speed){
        this.y = y;
        this.degrees = degrees;
        this.speed = speed;
        addRequirements(y);
    }

    @Override
    public void initialize() {
        y.wheelSpeed(speed);
    }

    @Override
    public void execute(){
        
        y.atun().execute();
        
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
