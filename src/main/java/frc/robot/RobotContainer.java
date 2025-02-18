// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.com.AlgaeCommand;
import frc.robot.com.Rise;
import frc.robot.com.feed;
import frc.robot.com.speed;
import frc.robot.com.wristSpeed;
import frc.robot.com.wristToAngle;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.sub.Drive.swerve;
import frc.robot.util.BulukXboxController;
import frc.robot.util.Syncro.SyncroCommands;
import frc.robot.util.Syncro.SyncroLinker;

public class RobotContainer {

  Elevator elevator = new Elevator();
  AlgaeWrist algae = new AlgaeWrist();
  CoralWrist wrist = new CoralWrist();

  CommandXboxController c = new CommandXboxController(0);
  //CommandXboxController c2 = new CommandXboxController(1);

  BulukXboxController operator = new BulukXboxController(1);

  swerve chassis = new swerve();

  public RobotContainer() {
    SyncroLinker.listen();

    configureBindings();
    
  }

  private void configureBindings() {

    SyncroCommands.periodicUpdate();

    SyncroCommands.add("Eject", new wristSpeed(wrist, 0.3));

    SyncroCommands.add("StopWristWheels", new wristSpeed(wrist, 0));

    SyncroCommands.add("IntakeAlgae", new AlgaeCommand(algae, 69, 0.5));

    SyncroCommands.add("StopAlgae", new InstantCommand(()->
      algae.stop(),algae));

    SyncroCommands.add("StopAll", new InstantCommand(()->{
      algae.stop();
      wrist.stop();
      elevator.stop();
    }, algae,wrist,elevator));
    
    /*chassis.setDefaultCommand(
      DriveCommands.joystickDrive(
        chassis,
         ()-> c2.getLeftY(),
           ()-> c2.getLeftX(),
             ()-> -c2.getRightX()));
    */
    
    /* 
    Drive modes. Normal Drive
    miaw.setDefaultCommand(DriveCommands.joystickDrive(miaw, ()-> c2.getLeftY(),  ()-> c2.getLeftX(),  ()-> -c2.getRightX()));
    //91.4 elev
    //402 muñeca
    c2.leftBumper().whileTrue(new StartEndCommand(()-> {w.wheelSpeed(-0.5);}, ()->{w.wheelSpeed(0);}, w));
    c2.a().whileTrue(new i(w, 0));
    //c2.a().whileTrue(juan.l4());
    c2.x().whileTrue(new i(w, 2.0));
    c2.start().whileTrue(DriveCommands.resetHeading(miaw));
    c2.b().whileTrue(new i(w, 3.2));
    c2.y().whileTrue(new r(w, 2, 0.3));
    c2.rightBumper().whileTrue(new StartEndCommand(()-> {w.wheelSpeed(0.8);}, ()->{w.wheelSpeed(0);}, w));*/

    //Intake Alga
    operator.leftBumper().whileTrue(new AlgaeCommand(algae, 69, 0.5));
    //Outake Alga
    operator.LT().whileTrue(new AlgaeCommand(algae, 69, -0.5));

    //Intake Coral
    operator.rightBumper().whileTrue(new feed(elevator, wrist, 1.38));
    //Outake Coral
    operator.RT().whileTrue(new wristSpeed(wrist, 0.5));

    //L1
    operator.aButton().whileTrue(new Rise(elevator, wrist, algae, 66, 3.2));

    //L2
    operator.bButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L2, 3.2));

    //L3
    operator.xButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L3 + 2, 3.35));

    //L4
    operator.yButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L4 +4, 3.0));

    //Elevator down
    operator.dPadDown().whileTrue(new speed(elevator, -0.3, wrist, algae));
  
    //Manual Retract
    operator.dPadUp().whileTrue(new wristToAngle(wrist, 0));

    /*c.leftBumper().whileTrue(new manualElevator(e, ()-> c.getLeftY() * -0.2));
    c.rightBumper().whileTrue(new elevatorToHeight(e, ElevatorConstants.SETPOINT_FEEDER));
    c.x().whileTrue(new elevatorToHeight(e, ElevatorConstants.SETPOINT_L2));
    c.b().whileTrue(new elevatorToHeight(e, ElevatorConstants.SETPOINT_L3));
    c.y().whileTrue(new Rise(e, w, a, ElevatorConstants.SETPOINT_L4));
    c.povUp().whileTrue(new parallelCoral(w));
    c.povDown().whileTrue(new speed(e, -0.08, w, a));
    c.povLeft().whileTrue(new wristToAngle(w, 0));
    c.start().whileTrue(new InstantCommand(()->{e.resetEncoders();}, e));*/
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

