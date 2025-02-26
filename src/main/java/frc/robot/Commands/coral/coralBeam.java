package frc.robot.Commands.coral;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.CoralWrist;

public class coralBeam extends Command{
    CoralWrist coral;
    double speed;
    Debouncer timer;
    public coralBeam(CoralWrist coral, double speed, double seconds){
        this.coral = coral;
        this.speed = speed;
        timer = new Debouncer(seconds);
        addRequirements(coral);
    }

    @Override
    public void initialize(){
      
    }

    @Override
    public void execute(){
        coral.wheelSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        coral.wheelSpeed(0);
    }

    @Override
    public boolean isFinished(){  
        return timer.calculate(!coral.hasPiece());
    }
}
