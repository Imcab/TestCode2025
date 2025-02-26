package frc.robot.Commands.auto;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BulukLib.Math.Domain;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;

public class RiseAuto extends Command{
    private final Elevator elevator;
    private final CoralWrist coral;
    private double angleRotations;

    public double height;

    public boolean finished = false;;

    public Domain elevatorRange;
    public Domain wristRange;
    public Debouncer timer;

    public RiseAuto(Elevator elevator, CoralWrist coral, double height, double angleRotations){
        this.elevator = elevator;
        this.coral = coral;
        this.height = height;
        this.angleRotations = angleRotations;
        this.elevatorRange = new Domain(height - 0.02, height + 0.02);
        this.wristRange = new Domain(angleRotations - 0.05, angleRotations + 0.05);
        timer = new Debouncer(0.9);
        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){
        finished = false;
    }

    @Override
    public void execute(){
        
        elevator.runMotion(height);
        
        if (elevatorRange.inRange(elevator.RelativeMeters())) {
            coral.autoRequest(angleRotations);
        }

        if (wristRange.inRange(coral.getRawPosition())) {
            coral.wheelSpeed(0.7);
            finished = true;
        }
    }


    @Override
    public void end(boolean interrupted) {
        coral.wheelSpeed(0);
    }

    @Override
    public boolean isFinished(){     
        return timer.calculate(finished);
    }
    
}
