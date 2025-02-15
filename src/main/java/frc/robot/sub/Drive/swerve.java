package frc.robot.sub.Drive;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.util.SwerveConfig;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;


public class swerve extends SubsystemBase{
  
    private final AHRS navX =  new AHRS(NavXComType.kMXP_SPI);

    //For advantage scope
    StructPublisher<Pose2d> odometrypublisher = NetworkTableInstance.getDefault()
    .getStructTopic("Botpose2D", Pose2d.struct).publish();

    StructPublisher<ChassisSpeeds> ChassisSpeedpublisher = NetworkTableInstance.getDefault()
    .getStructTopic("ChassisSpeeds", ChassisSpeeds.struct).publish();

    StructArrayPublisher<SwerveModuleState> ModuleStatepublisher = NetworkTableInstance.getDefault()
    .getStructArrayTopic("SwerveStates", SwerveModuleState.struct).publish();

    
    private static final double MAX_LINEAR_SPEED = Units.feetToMeters(19.0);
    private static final double TRACK_WIDTH_X = Units.inchesToMeters(28); 
    private static final double TRACK_WIDTH_Y = Units.inchesToMeters(31.7); 
    private static final double DRIVE_BASE_RADIUS =
        Math.hypot(TRACK_WIDTH_X / 2.0, TRACK_WIDTH_Y / 2.0);
    private static final double MAX_ANGULAR_SPEED = MAX_LINEAR_SPEED / DRIVE_BASE_RADIUS;

    //private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(getModuleTranslations());

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(SwerveConfig.measures.getTranslations());

    private Rotation2d rawGyroRotation = new Rotation2d();
    private SwerveModulePosition[] lastModulePositions = // For delta tracking
      new SwerveModulePosition[] {
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition(),
        new SwerveModulePosition()
      };
    private SwerveDrivePoseEstimator poseEstimator =
      new SwerveDrivePoseEstimator(kinematics, rawGyroRotation, lastModulePositions, new Pose2d());

    ModuleSpark []modules  = new ModuleSpark[4];

    public swerve(){

     
        modules[0] = new ModuleSpark(0);
        modules[1] = new ModuleSpark(1);
        modules[2] = new ModuleSpark(2);
        modules[3] = new ModuleSpark(3);

        new Thread(() -> {
            try{
                Thread.sleep(1000);
                navX.reset();
            } catch (Exception e){
    
            }
          }).start();

        
    }

    public void periodic(){

      odometrypublisher.set(getPose());
      ChassisSpeedpublisher.set(getChassisSpeeds());
      ModuleStatepublisher.set(getModuleStates());

    
      /*if (!vision.isLimeEmpty()) {
        addObservationStd(vision.getLimeObservation().observation(),
         vision.getLimeObservation().withTimeStamps(),
          vision.getLimeObservation().getTrust());
      }*/

      for (var module : modules) {
        module.periodic();
      }
      if (DriverStation.isDisabled()) {
        for (var module : modules) {
          module.stop();
      }}

      SwerveModulePosition[] modulePositions = getModulePositions();
      SwerveModulePosition[] moduleDeltas = new SwerveModulePosition[4];

      for (int moduleIndex = 0; moduleIndex < 4; moduleIndex++) {
        moduleDeltas[moduleIndex] =
            new SwerveModulePosition(
                modulePositions[moduleIndex].distanceMeters
                    - lastModulePositions[moduleIndex].distanceMeters,
                modulePositions[moduleIndex].angle);
        lastModulePositions[moduleIndex] = modulePositions[moduleIndex];
      }

      // Update gyro angle
      if (navX.isConnected() == true) {
        // Use the real gyro angle
        rawGyroRotation = getnavXRotation();
      } else {
        // Use the angle delta from the kinematics and module deltas
        Twist2d twist = kinematics.toTwist2d(moduleDeltas);
        rawGyroRotation = rawGyroRotation.plus(new Rotation2d(twist.dtheta));
      }
      SmartDashboard.putNumber("NaxX", getAngle());

      // Apply odometry update
      poseEstimator.update(rawGyroRotation, modulePositions);
    }

    public double getAngle(){
      return Math.IEEEremainder(navX.getAngle(), 360);
    }

    public double getYawVelocityRadPerSec(){
      return Units.degreesToRadians(-navX.getRawGyroZ());
    }

    
    public Rotation2d getnavXRotation(){
      return Rotation2d.fromDegrees(getAngle());
    }

    public ChassisSpeeds getChassisSpeeds(){
      return kinematics.toChassisSpeeds(getModuleStates());
    }

    public static Translation2d[] getModuleTranslations() {
    return new Translation2d[] {
      new Translation2d(TRACK_WIDTH_X / 2.0, TRACK_WIDTH_Y / 2.0),
      new Translation2d(TRACK_WIDTH_X / 2.0, -TRACK_WIDTH_Y / 2.0),
      new Translation2d(-TRACK_WIDTH_X / 2.0, TRACK_WIDTH_Y / 2.0),
      new Translation2d(-TRACK_WIDTH_X / 2.0, -TRACK_WIDTH_Y / 2.0)
      };
    }

  public void runVelocity(ChassisSpeeds speeds) {
    // Calculate module setpoints
    ChassisSpeeds discreteSpeeds = ChassisSpeeds.discretize(speeds, 0.02);
    SwerveModuleState[] setpointStates = kinematics.toSwerveModuleStates(discreteSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(setpointStates, MAX_LINEAR_SPEED);

    // Send setpoints to modules
    SwerveModuleState[] optimizedSetpointStates = new SwerveModuleState[4];
    for (int i = 0; i < 4; i++) {
      // The module returns the optimized state, useful for logging
      optimizedSetpointStates[i] = modules[i].runSetpoint(setpointStates[i]);
    }


  }

  public void stop() {
    runVelocity(new ChassisSpeeds());
  }

  public void runRobotRelative(double vx, double vy, double rot){

    ChassisSpeeds relative = ChassisSpeeds.discretize(ChassisSpeeds.fromRobotRelativeSpeeds(
      new ChassisSpeeds(vx, vy, rot),
       rawGyroRotation),
        0.02);

    SwerveModuleState[] state = kinematics.toSwerveModuleStates(relative);

    SwerveDriveKinematics.desaturateWheelSpeeds(state, SwerveConfig.speeds.MAX_LINEAR_SPEED);

    // Send setpoints to modules
    SwerveModuleState[] optimizedSetpointStates = new SwerveModuleState[4];
    for (int i = 0; i < 4; i++) {
      // The module returns the optimized state, useful for logging
      optimizedSetpointStates[i] = modules[i].runSetpoint(state[i]);
    }
    
  }

  public void stopWithX() {
    Rotation2d[] headings = new Rotation2d[4];
    for (int i = 0; i < 4; i++) {
      headings[i] = getModuleTranslations()[i].getAngle();
    }
    kinematics.resetHeadings(headings);
    stop();
  }

  private SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[4];
    for (int i = 0; i < 4; i++) {
      states[i] = modules[i].getState();
    }
    return states;
  }

  private SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] states = new SwerveModulePosition[4];
    for (int i = 0; i < 4; i++) {
      states[i] = modules[i].getPosition();
    }
    return states;
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

  public Double getX() {
    return getPose().getX();
  }

  public Double getY() {
    return getPose().getY();
  }

  public Rotation2d getRotation() {
    return getPose().getRotation();
  }

  public void setPose(Pose2d pose) {
    poseEstimator.resetPosition(rawGyroRotation, getModulePositions(), pose);
  }

  /** Returns the maximum linear speed in meters per sec. */
  public double getMaxLinearSpeedMetersPerSec() {
    return MAX_LINEAR_SPEED;
  }

  /** Returns the maximum angular speed in radians per sec. */
  public double getMaxAngularSpeedRadPerSec() {
    return MAX_ANGULAR_SPEED;
  }

  public void resetHeading(){
    navX.reset();
  }

  public void addObservationStd(Pose2d pose, double timeStamps, Matrix< N3, N1> std){
      poseEstimator.addVisionMeasurement(pose, timeStamps, std);
  }
  public void addObservation(Pose2d pose, double timeStamps){
    poseEstimator.addVisionMeasurement(pose, timeStamps);
}

 

}
