package frc.robot;

import frc.robot.BulukLib.MotionControllers.Gains.Gains;
import frc.robot.BulukLib.MotionControllers.Gains.TrapezoidalGains;

public class Constants {

    public class ConstantsHanger {

        public static final int RightHangerPort = 17;
        public static final int LeftHangerPort = 18;
        //public static final int switchPort = 0;
        public static final boolean r_inverted = true;
        public static final boolean l_inverted = false;
    }

  public class ElevatorConstants {

        public static final double maxVelocity = 0.69; //MS
        public static final double maxAcc = 0.8625; //MS_Squared
        public static final TrapezoidalGains motionGains = new TrapezoidalGains(7.5, 0, 0.0, 0, 0, maxAcc, maxVelocity);
        public static final int CAN_ID_LEADER = 15;
        public static final int CAN_ID_SLAVE = 16;
        public static final boolean leaderInverted = false;
        public static final boolean slaveInverted = true;
        public static final double IDLE_POSITION = 0.63;
        public static final double SETPOINT_RETRACT = IDLE_POSITION + 0.03;
        public static final double SETPOINT_FEEDER = 84.7;
        public static final double SETPOINT_L2 = 73.9;
        public static final double SETPOINT_L3 = 119;
        public static final double SETPOINT_L4 = 186;
        public static final double CONVERSION_FACTOR = (120.9 / 20.85) / 3; 
        public static final double ELEVATOR_OFFSET_CENTIMETERS = 63;
    
  }

  public class WristConstants {
  
    public class Algae {

        public static final double tolerance = 5;
        public static final int CAN_ID_WRIST = 13;
        public static final int CAN_ID_RIGHTWHEEL = 12;
        public static final int CAN_ID_LEFTWHEEL = 14;
        public static final boolean wristMotorInverted = false;
        public static final boolean RightInverted = false;
        public static final boolean LeftInverted = true;
        public static final int wristCurrentLimit = 30;
        public static final int WheelsCurrentLimit = 25;
        public static final Gains GAINS = new Gains(0.005,0,0);

    }

    public class Coral {

        public static final int DIO_PORT_SENSOR = 1;
        public static final int CAN_ID_WRIST = 10;
        public static final int CAN_ID_EATER = 11;
        public static final boolean wristMotorInverted = false;
        public static final boolean wheelInverted = false;
        public static final int wristCurrentLimit = 40;
        public static final int wheelCurrentLimit = 15;
        public static final Gains Gains = new Gains(0.072,0.0063,0.0001);
        public static final double encoderPositionFactor = 360; //degrees
        public static final double wristErrorTolerance = 0.1;  
    }
  }

  public class DriveConstants {

    public static final Gains driveGains = new Gains(0.015, 0, 0, 0.05, 0.06); //0.13
    public static final Gains turnGains = new Gains(5.0, 0, 0);
  
    public static final class frontLeft{

        public static final int DrivePort = 1; 
        public static final int TurnPort = 2; 
        public static final int EncPort = 1;
        public static final double offset = 93.2519;                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ; //48     //93  //138      //48 o 138 o 228
 
        public static final boolean DrivemotorReversed = true;
        public static final boolean TurnmotorReversed = true;

    }

    public static final class frontRight{

        public static final int DrivePort = 4; 
        public static final int TurnPort = 3; 
        public static final int EncPort = 0; 
        public static final double offset = 311.8359; 
 
        public static final boolean DrivemotorReversed = true;
        public static final boolean TurnmotorReversed = true;

    }

    public static final class backLeft{

        public static final int DrivePort = 5; 
        public static final int TurnPort = 6; 
        public static final int EncPort = 2; 
        public static final double offset = 327.1289;
 
        public static final boolean DrivemotorReversed = true;
        public static final boolean TurnmotorReversed = true; 

    }

    public static final class backRight{

        public static final int DrivePort = 7; 
        public static final int TurnPort = 8; 
        public static final int EncPort = 3; 
        public static final double offset = 338.7; 
 
        public static final boolean DrivemotorReversed = true;
        public static final boolean TurnmotorReversed = true;

    }

}

}
