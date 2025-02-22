package frc.robot.sub;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.util.Domain;
import frc.robot.util.MotionControllers.Gains;
import frc.robot.util.MotionControllers.ClosedLoopControl.ClosedLoopControl;
import frc.robot.util.MotionControllers.ClosedLoopControl.ClosedLoopControl.ClosedLoopRequest;
import frc.robot.util.MotionControllers.ClosedLoopControl.ClosedLoopControl.OutputType;
import frc.robot.util.MotionControllers.TrapezoidalControl.Trapezoidal;

public class Elevator extends SubsystemBase {

  private final SparkMax leader, follower;
  private final SparkMaxConfig leaderConfig, followerConfig;
  private final RelativeEncoder leaderEncoder, followerEncoder;
  DigitalInput limit = new DigitalInput(0);
  private double position = 69;

  public enum ELEVATOR_LEVELS {
    k1, k2, k3
  }

  private ELEVATOR_LEVELS tracker = ELEVATOR_LEVELS.k1;

  private final Trapezoidal motion;

  private final SparkAbsoluteEncoder absEncoder;

  private double vueltasAcumuladas = 0;
  private double ultimaLectura = 0;

  public Elevator() {
  
    motion = new Trapezoidal(ElevatorConstants.motionGains, OutputType.kPositive);
    motion.initTuning("motionTune");

    leaderConfig = new SparkMaxConfig();
    followerConfig = new SparkMaxConfig();

    leader = new SparkMax(ElevatorConstants.CAN_ID_LEADER, MotorType.kBrushless);
    follower = new SparkMax(ElevatorConstants.CAN_ID_SLAVE, MotorType.kBrushless);

    leaderEncoder = leader.getEncoder();
    followerEncoder = follower.getEncoder();

    absEncoder = leader.getAbsoluteEncoder();

    

    resetEncoders();
    burnFlash();

    //runMotion(0.63, 0);
  
  }

  public void resetEncoders() {
    leaderEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }

  public double getVelocity(){
    return (leaderEncoder.getVelocity() * (ElevatorConstants.CONVERSION_FACTOR / 3)) / 100;
  }

  private void burnFlash() {
    leader.setCANTimeout(250);
    leaderConfig.idleMode(IdleMode.kCoast).inverted(ElevatorConstants.leaderInverted);
    leader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leader.setCANTimeout(0);

    follower.setCANTimeout(250);
    followerConfig.idleMode(IdleMode.kCoast).follow(ElevatorConstants.CAN_ID_LEADER, ElevatorConstants.slaveInverted);
    follower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    follower.setCANTimeout(0);
  }

  public boolean pressed(){
    return !limit.get();
  }

  @Override
  public void periodic() {

    update();

    //Starts the graph and the tuner
    //elevatorPID.graph("ElevatorPID");

    motion.graph("MotionGraph");
    motion.Tune();

    if (pressed()) {
      resetEncoders();
      this.position = 69;
    }

    //Define ranges for different PID gains   
    /*if (cero_To_L2.inRange(getCentimeters())) {
        updateGains(ElevatorConstants.k1_GAINS);
        tracker = ELEVATOR_LEVELS.k1;
    }

    if (L2_to_L3.inRange(getCentimeters())) {
        updateGains(ElevatorConstants.k2_GAINS);
        tracker = ELEVATOR_LEVELS.k2;
    }

    if (l3_to_L4.inRange(getCentimeters())) {
        updateGains(ElevatorConstants.k3_GAINS);
        tracker = ELEVATOR_LEVELS.k3;
    }

    //Print the current Level
    SmartDashboard.putString("ElevatorLevel", tracker.toString());

    SmartDashboard.putNumber("CENT", getCentimeters());*/

    SmartDashboard.putNumber("VELOCITY", getVelocity());
    SmartDashboard.putNumber("RAW", rawAbs());
    SmartDashboard.putNumber("METERS", getPositionMeters());
    SmartDashboard.putBoolean("SWITCG", pressed());
    
    
  }

  private double toMeters(double value){
    return value * ElevatorConstants.ABSOLUTE_TO_METERS;
  }

  public void start(double value){
      motion.run(getPositionMeters(), new State(value, 0));
  }

  public double getPositionMeters(){
    this.position =  toMeters(vueltasAcumuladas + rawAbs()) + 0.63;
    return position;
  }

  public double getCentimeters(){
    return getPositionMeters() * 100;
  }

  public double rawAbs(){
    return absEncoder.getPosition();
  }

  public void update() {
    double nuevaLectura = rawAbs(); // Valor entre 0 y 1

    // Si pasamos de 0.9 a 0.1, significa que sumamos una vuelta
    if (ultimaLectura > 0.9 && nuevaLectura < 0.1) {
        vueltasAcumuladas++;
    }
    // Si pasamos de 0.1 a 0.9, significa que restamos una vuelta
    else if (ultimaLectura < 0.1 && nuevaLectura > 0.9) {
        vueltasAcumuladas--;
    }

    ultimaLectura = nuevaLectura;
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

  public void runMotion(double position, double velocity){
    leader.set(motion.run(getPositionMeters(), new State(position, velocity)));
  }

  public void runMotion(double position){
    leader.set(motion.run(getPositionMeters(), new State(position, 0)));
  }

}
