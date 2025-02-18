package frc.robot.com;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.util.Domain;

public class Shoot extends Command {
    private final Elevator elevator;
    private final CoralWrist coral;
    private final AlgaeWrist algae;

    private final Domain retracted;

    public Shoot(Elevator elevator, CoralWrist coral, AlgaeWrist algae){
        this.elevator = elevator;
        this.coral = coral;
        this.algae = algae; 
        this.retracted = new Domain(-0.1, 0.1);

        addRequirements(elevator, coral, algae);
    }

    @Override
    public void initialize(){
      
    }

    @Override
    public void execute(){

        coral.wheelSpeed(0.8);

    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
        algae.stop();
        coral.stop();
    }


    @Override
    public boolean isFinished(){
        
        return retracted.inRange(coral.getRawPosition());
    }
}
