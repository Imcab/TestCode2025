package frc.robot.Subsystems.Components;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BulukLib.MotionControllers.ClosedLoopControl.ClosedLoopControl.OutputType;
import frc.robot.BulukLib.MotionControllers.TrapezoidalControl.Trapezoidal;
import frc.robot.BulukLib.MotionControllers.TrapezoidalControl.TrapezoidalTolerance;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private final SparkMax leader, follower;
  private final SparkMaxConfig leaderConfig, followerConfig;
  private final RelativeEncoder leaderEncoder, followerEncoder;
  DigitalInput limit = new DigitalInput(0);

  private final Trapezoidal motion;

  public Elevator() {
  
    motion = new Trapezoidal(ElevatorConstants.motionGains, OutputType.kPositive);

    motion.initTuning("motionTune");
  
    leaderConfig = new SparkMaxConfig();
    followerConfig = new SparkMaxConfig();

    leader = new SparkMax(ElevatorConstants.CAN_ID_LEADER, MotorType.kBrushless);
    follower = new SparkMax(ElevatorConstants.CAN_ID_SLAVE, MotorType.kBrushless);

    leaderEncoder = leader.getEncoder();
    followerEncoder = follower.getEncoder();

    motion.reset(0.63);

    motion.setTolerance(new TrapezoidalTolerance(0.02, 0.08));

    resetEncoders();
    burnFlash();
  
  }

  public void resetEncoders() {
    leaderEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }

  public double getVelocity(){
    return (leaderEncoder.getVelocity() * (ElevatorConstants.CONVERSION_FACTOR / 3)) / 100;
  }

  private void burnFlash() {
    leaderConfig.idleMode(IdleMode.kCoast).inverted(ElevatorConstants.leaderInverted);
    leaderConfig.encoder
      .uvwAverageDepth(2)
      .uvwMeasurementPeriod(10);

    leader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    followerConfig.idleMode(IdleMode.kCoast).follow(ElevatorConstants.CAN_ID_LEADER, ElevatorConstants.slaveInverted);
    follower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public double getRelative(){
    return leaderEncoder.getPosition();
  }

  public double getOutput(){
    return leader.get();
  }

  public double RelativeCentimeters(){
    return (getRelative() * ElevatorConstants.CONVERSION_FACTOR) + ElevatorConstants.ELEVATOR_OFFSET_CENTIMETERS;
  }

  public double RelativeMeters(){
    return RelativeCentimeters()/100;
  }

  public void start(double value){
      motion.run(RelativeMeters(), value);
  }

  public boolean pressed(){
    return !limit.get();
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("VELOCITY", getVelocity());
    SmartDashboard.putBoolean("SWITCH_PRESSED", pressed());
    SmartDashboard.putNumber("RELATIVE METERS", RelativeMeters());

    SmartDashboard.putBoolean("AUTOGOAL", atGoal());

    motion.graph("MotionGraph");
    motion.Tune();
  
    if (pressed()) {
      resetEncoders();
    }

    
  }

  public void runSpeed(double speed) {
    leader.set(speed);
  }

  public boolean atGoal() {
    return motion.atGoal();
  }

  public void stop() {
    leader.stopMotor();
  }

  public void move(double position){
    leader.set(motion.get().calculate(RelativeMeters(), position));
  }

  public void runMotion(double position, double velocity){
    leader.set(motion.run(RelativeMeters(), new State(position, velocity)));
  }

  public void runMotion(double position){
    leader.set(motion.run(RelativeMeters(), position));
  }

}
