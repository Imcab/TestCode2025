package frc.robot.com;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.AlgaeWrist;

public class AlgaeCommand extends Command{
    double parameteralign;
    double parameterout;
    AlgaeWrist algae;
    boolean isFinished;
    public AlgaeCommand(AlgaeWrist algae, double parameteralign, double parameterout){
        this.algae = algae;
        this.parameteralign = parameteralign;
        this.parameterout = parameterout;
   
        addRequirements(algae);
    }
    @Override
    public void initialize () {
        //se alinea
        isFinished = false;
       
    }

    @Override
    public void execute (){
        algae.setPosition(parameteralign);
        algae.runWheels(parameterout);

        /*if (algae.getPosition() <=  parameteralign + 0.1 && algae.getPosition() <=  parameteralign - 0.1) {
            algae.runWheels(parameterout);
            isFinished = true;
        }*/

    }

    @Override
    public void end (boolean interrupted) {
        algae.stop();
    }
    @Override
    public boolean isFinished(){
        //return timer.calculate(isFinished);
        return false;
    }
}
