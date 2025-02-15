package frc.robot.com;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;

public class speed extends Command{
    Elevator e;
    double x;
    CoralWrist w;
    AlgaeWrist a;
    public speed(Elevator e, double x, CoralWrist w, AlgaeWrist a){
        this.e = e;
        this.x = x;
        this.w = w;
        this.a = a;
        addRequirements(e);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute(){
        w.requestPosition(0.3);
        a.setPosition(16);
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
        a.stop();
    }
}
