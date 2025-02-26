package frc.robot.Commands.elevatorCommands;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.BulukLib.Swerve.BulukXboxController;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;

public class feed extends Command {
    private final Elevator elevator;
    private final CoralWrist coral;
    public double CoralFeed;
    private BulukXboxController operator;
    private CommandXboxController driver;

    public feed(Elevator elevator, CoralWrist coral, double CoralFeed, BulukXboxController operator, CommandXboxController driver){
        this.elevator = elevator;
        this.coral = coral;
        this.CoralFeed = CoralFeed;
        this.operator = operator;
        this.driver = driver;

        addRequirements(elevator, coral);
    }

    @Override
    public void initialize(){
 
    }

    @Override
    public void execute(){
        elevator.runMotion(0.832);

        coral.requestPosition(CoralFeed);
        coral.wheelSpeed(-0.55);
        
        if (coral.hasPiece()) {
            driver.getHID().setRumble(RumbleType.kBothRumble, 0.5);
            operator.rumble(0.5);
        }
    
    }
            
    @Override
    public void end(boolean interrupted){
        operator.rumble(0);
        driver.getHID().setRumble(RumbleType.kBothRumble, 0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }


}
