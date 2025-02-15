package frc.robot.com;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.util.Domain;
import frc.robot.Constants.ElevatorConstants;;

public class feed extends Command {
    private final Elevator elevator;
    private final CoralWrist coral;
    public Domain feedRange;
    public boolean isFinished;
    public boolean initialize;

    public feed(Elevator elevator, CoralWrist coral){
        this.elevator = elevator;
        this.coral = coral;

        feedRange = new Domain(ElevatorConstants.SETPOINT_FEEDER - 3, ElevatorConstants.SETPOINT_FEEDER + 3);

        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){
        initialize = false;
        isFinished = false;
    }

    @Override
    public void execute(){}

    @Override
    public void end(boolean interrupted){

        initialize = false;
        isFinished = false;
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }


}
