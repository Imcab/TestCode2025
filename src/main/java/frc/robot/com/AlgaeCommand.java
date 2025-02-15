package frc.robot.com;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.AlgaeWrist;

public class AlgaeCommand extends Command{
    double parameteralign;
    double parameterout;
    AlgaeWrist algae;
    boolean isFinished;
    Debouncer timer;
    public AlgaeCommand(AlgaeWrist algae, double parameteralign, double parameterout, double seconds){
        this.algae = algae;
        this.parameteralign = parameteralign;
        this.parameterout = parameterout;
        this.timer = new Debouncer(seconds);

    }
    @Override
    public void initialize () {
        //se alinea
        isFinished = false;
       
    }

    @Override
    public void execute (){
        algae.setPosition(parameteralign);


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
