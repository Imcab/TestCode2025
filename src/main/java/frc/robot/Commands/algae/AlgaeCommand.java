package frc.robot.Commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.AlgaeWrist;

public class AlgaeCommand extends Command{
    double parameteralign;
    double parameterout;
    AlgaeWrist algae;
    public AlgaeCommand(AlgaeWrist algae, double parameteralign, double parameterout){
        this.algae = algae;
        this.parameteralign = parameteralign;
        this.parameterout = parameterout;
   
        addRequirements(algae);
    }
    @Override
    public void initialize () {
       
    }

    @Override
    public void execute (){
        algae.setPosition(parameteralign);
        algae.runWheels(parameterout);

    }

    @Override
    public void end (boolean interrupted) {
        algae.stop();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
