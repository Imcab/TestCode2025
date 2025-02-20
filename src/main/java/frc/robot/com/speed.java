package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class speed extends Command{
    Elevator e;
    double x;
    CoralWrist w;
    public speed(Elevator e, double x, CoralWrist w, AlgaeWrist a){
        this.e = e;
        this.x = x;
        this.w = w;
        addRequirements(e, w, a);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute(){
        w.requestPosition(0.3);
        e.runSpeed(x);
    }

    @Override
    public boolean isFinished() {
        return e.pressed();
    }

    @Override
    public void end(boolean interrupted) {
        e.stop();
        w.stop();

    }
}
