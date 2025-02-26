package frc.robot.Commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.Hanger.HangerClimber;

public class climb extends Command{

    private HangerClimber climber;
    private double speed;
    public climb(HangerClimber climber, double speed){
        this.climber = climber;
        this.speed = speed;
        addRequirements(climber);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        climber.setSpeed(speed);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        climber.stop();
    }

}
