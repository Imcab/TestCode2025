package frc.robot.sub;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WristConstants.Coral;
import frc.robot.util.ClosedLoopControl;
import frc.robot.util.ClosedLoopControl.ClosedLoopRequest;
import frc.robot.util.ClosedLoopControl.OutputType;

public class CoralWrist extends SubsystemBase{
    
    private SparkMax wrist; 
    private RelativeEncoder wristEncoder;
    private SparkMax wheel;

    private SparkMaxConfig ConfigWrist , ConfigEater;

    private double target;

    //private BeamSensor beamBreaker = new BeamSensor(Coral.DIO_PORT_SENSOR); 

    private ClosedLoopControl pid;

    private ClosedLoopRequest request; 

    //Declaracion de 
    public CoralWrist(){

        pid  = new ClosedLoopControl(Coral.Gains, OutputType.kNegative);

        request = pid.new ClosedLoopRequest();

        wrist = new SparkMax(Coral.CAN_ID_WRIST, MotorType.kBrushless);

        wristEncoder = wrist.getEncoder();

        wheel = new SparkMax(Coral.CAN_ID_EATER, MotorType.kBrushless);

        ConfigWrist = new SparkMaxConfig();
        ConfigEater = new SparkMaxConfig();

        request.enableOutputClamp(true);

        request.withClamp(1);

        pid.setTolerance(0.5);

        Burnflash();

        setZero();
    }

    private void Burnflash(){

        wrist.setCANTimeout(250);
        wheel.setCANTimeout(250);

        ConfigWrist.
            inverted(Coral.wristMotorInverted).
            idleMode(IdleMode.kBrake).
            smartCurrentLimit(Coral.wristCurrentLimit);
        //Config eater
        ConfigEater.
            inverted(Coral.wheelInverted).
            idleMode(IdleMode.kCoast).
            smartCurrentLimit(30);

        wrist.configure(ConfigWrist, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        wheel.configure(ConfigEater, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        setZero();
        
        wrist.setCANTimeout(0);
        wheel.setCANTimeout(0);
    }

    @Override
    public void periodic(){
        pid.graph("CoralWrist");
        SmartDashboard.putNumber("[CORALWRIST]: RawPosition:", getRawPosition());
        SmartDashboard.putBoolean("[CORALWRIST]: AtGoal:"  , atGoal());
        SmartDashboard.putNumber("[CORALWRIST]: Target:", target);
    }

    public Command atun(){
    Command a = Commands.run(()-> requestPosition(2), this);
    a.beforeStarting(Commands.waitSeconds(2));
    return a;
  }
    
    
    public boolean hasPiece(){
        //return beamBreaker.get();
        return false;
    }

    public double getRawPosition(){
        return -wristEncoder.getPosition();
    }

    public void setZero(){
        wristEncoder.setPosition(0);
    }

    public void requestPosition(double rotations){
        this.target = rotations;
        wrist.set(pid.runRequest(request.withReference(getRawPosition()).toSetpoint(rotations)));
    }

    public void wheelSpeed(double speed){
        wheel.set(speed);
    }
    public void retract(){
        requestPosition(0);
    }

    public boolean isWheelSpinning(){
        return wheel.get() != 0;
    }

    public void stop(){
        wrist.stopMotor();
        wheel.stopMotor();
    }

    public boolean atGoal(){
   
        return Math.abs(target - getRawPosition()) <= 0.1;
    }

}
