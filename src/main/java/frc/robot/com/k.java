package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.Elevator;

public class k extends Command{
    Elevator e;
    double y;
    public k(Elevator e, double y){
        this.e = e;
        this.y = y;
        addRequirements(e);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute(){
        e.runRequest(y);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        e.stop();
    }
}
