package frc.robot.Commands.elevatorCommands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;

public class Rise extends Command{
    private final Elevator elevator;
    private final CoralWrist coral;
    private double angleRotations;

    public double height;

    public Timer timer = new Timer();

    public Rise(Elevator elevator, CoralWrist coral, double height, double angleRotations){
        this.elevator = elevator;
        this.coral = coral;
        this.height = height;
        this.angleRotations = angleRotations;

        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){
        timer.reset();
        timer.start();
        coral.wheelSpeed(-0.1);
    }

    @Override
    public void execute(){
    
        if (timer.get() >= 0.5) {
            coral.wheelSpeed(0);
        }

        elevator.runMotion(height);
        coral.requestPosition(angleRotations);
        
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        coral.wheelSpeed(0);
    }   

    @Override
    public boolean isFinished(){     
        return false;
    }
    
}
