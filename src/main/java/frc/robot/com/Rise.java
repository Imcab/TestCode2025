package frc.robot.com;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;


public class Rise extends Command{
    private final Elevator elevator;
    private final CoralWrist coral;
    private final AlgaeWrist algae;
    private double angleRotations;

    public double height;

    public Rise(Elevator elevator, CoralWrist coral, AlgaeWrist algae, double height, double angleRotations){
        this.elevator = elevator;
        this.coral = coral;
        this.algae = algae;
        this.height = height;
        this.angleRotations = angleRotations;

        addRequirements(elevator, coral, algae);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        
        elevator.runRequest(height);
        //algae.setPosition(66);
        coral.requestPosition(angleRotations);

    }

    @Override
    public void end(boolean interrupted) {

    }


    @Override
    public boolean isFinished(){
        
        return false;
    }
    
}
