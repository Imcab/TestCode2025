package frc.robot.Commands.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BulukLib.Swerve.BulukXboxController;
import frc.robot.Subsystems.Components.CoralWrist;

public class operatorIntake extends Command {
    private CoralWrist wrist;
    private BulukXboxController operator;
    private double rumble;

    public operatorIntake(CoralWrist wrist, BulukXboxController operator,double rumble){
        this.wrist = wrist;
        this.operator = operator;
        this.rumble = rumble;
        addRequirements(wrist);
    }

    @Override
    public void initialize(){
      
    }

    @Override
    public void execute(){

        wrist.wheelSpeed(-0.5);

        if (wrist.hasPiece()) {
            operator.rumble(rumble);
        }

    }

    @Override
    public void end(boolean interrupted) {
        wrist.stop();
        operator.rumble(0);
    }

    @Override
    public boolean isFinished(){  
        return false;
    }


}
