package frc.robot.com;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.util.Domain;

public class GenericScore extends Command{
    private final CoralWrist coral;
    private final Elevator elevator;
    public double height;
    public Domain elevadorInRange;
    public Domain wristInRange;
    public Domain wristInRange2;
    public Domain down;
    public double speed;
    public boolean initialize = false;
    public boolean isFinished = false;
    public AlgaeWrist a;
    public Debouncer timer = new Debouncer(0.45);

    public GenericScore(CoralWrist coral, Elevator elevator, double height, double speed, AlgaeWrist a){
        this.coral = coral;
        this.elevator = elevator;
        this.speed = speed;
        this.height = height;
        this.a = a;

        elevadorInRange = new Domain(height - 4, height + 4);
        wristInRange = new Domain(3.1, 3.3);
        wristInRange2 = new Domain(-0.1, 0.1);
        down = new Domain(68, 71);

        addRequirements(coral, elevator, a);
    }

    @Override
    public void initialize(){

        initialize = false;
        isFinished = false;
        SmartDashboard.putString("GS", "init");
    
    }
    @Override
    public void execute(){
        
        elevator.runRequest(height);
        a.setPosition(66);
        
        
        if (elevadorInRange.inRange(elevator.getCentimeters())){
            coral.requestPosition(3.2);
            SmartDashboard.putString("GS", "elevador arriba");
        }

        if (wristInRange.inRange(coral.getRawPosition())){
            coral.wheelSpeed(speed);
            SmartDashboard.putString("GS", "muñeca angulada");
            initialize = true;
        }
        if (timer.calculate(initialize)) {
            SmartDashboard.putString("GS", "preparando");
            isFinished = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        coral.stop();
        elevator.stop();
        a.stop();

        SmartDashboard.putString("GS", "finished");
    }

     @Override
    public boolean isFinished(){
        
        return isFinished;
    }
    
}

