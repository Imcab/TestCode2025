package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;

public class wristToAngle extends Command {
    CoralWrist w;
    double degrees;

    public wristToAngle(CoralWrist w, double degrees){
        this.w = w;
        this.degrees = degrees;
        addRequirements(w);
    }

    @Override
    public void initialize() {
        System.out.println("i: Inicializando movimiento a " + degrees + " grados.");
    }

    @Override
    public void execute(){
        System.out.println("i: Moviendo a " + degrees + " grados, posición actual: " + w.getRawPosition());
        w.requestPosition(degrees);
    }

    @Override
    public boolean isFinished() {
        boolean done = Math.abs(w.getRawPosition() - degrees) <= 0.1;
        if (done) System.out.println("i: Movimiento terminado.");
        return done;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("i: Terminando comando, interrumpido: " + interrupted);
        w.stop();
    }
}
