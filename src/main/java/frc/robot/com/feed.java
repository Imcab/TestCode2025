package frc.robot.com;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.util.Domain;
import frc.robot.Constants.ElevatorConstants;;

public class feed extends Command {
    private final Elevator elevator;
    private final CoralWrist coral;
    public double CoralFeed;
    public Domain coralRange;
    public Domain feedRange;
    public boolean isFinished;
    public boolean initialize;
    public Debouncer time;

    public feed(Elevator elevator, CoralWrist coral, double CoralFeed){
        this.elevator = elevator;
        this.coral = coral;
        this.CoralFeed = CoralFeed;

        feedRange = new Domain(ElevatorConstants.SETPOINT_FEEDER - 3, ElevatorConstants.SETPOINT_FEEDER + 3);
        coralRange = new Domain(CoralFeed - 0.3, CoralFeed + 0.3);

        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){
        initialize = false;
        isFinished = false;
    }

    @Override
    public void execute(){
        elevator.runRequest(ElevatorConstants.SETPOINT_FEEDER);

        if (feedRange.inRange(elevator.getCentimeters())){
            coral.requestPosition(CoralFeed);
        }

        if (coralRange.inRange(coral.getRawPosition())){
            coral.wheelSpeed(-0.55);
        }

    
    }
            


    @Override
    public void end(boolean interrupted){
        elevator.stop();
        coral.stop();

        initialize = false;
        isFinished = false;
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }


}
