// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.com.AlgaeCommand;
import frc.robot.com.DriveCommands;
import frc.robot.com.Rise;
import frc.robot.com.elevatorToHeight;
import frc.robot.com.feed;
import frc.robot.com.manualElevator;
import frc.robot.com.retract;
import frc.robot.com.safe;
import frc.robot.com.speed;
import frc.robot.com.wristSpeed;
import frc.robot.com.wristToAngle;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.sub.Hanger;
import frc.robot.sub.Drive.swerve;
import frc.robot.util.BulukXboxController;
import frc.robot.util.Syncro.SyncroCommands;
import frc.robot.util.Syncro.SyncroLinker;

public class RobotContainer {

  swerve chassis = new swerve();

  Elevator elevator = new Elevator();
  AlgaeWrist algae = new AlgaeWrist();
  CoralWrist wrist = new CoralWrist();
  Hanger hanger = new Hanger();

  CommandXboxController driver = new CommandXboxController(0);
  //CommandXboxController c2 = new CommandXboxController(1);

  BulukXboxController operator = new BulukXboxController(1);

  public RobotContainer() {
    SyncroLinker.listen();

    configureBindings();
    
  }

  private void configureBindings() {

    chassis.setDefaultCommand(
      DriveCommands.joystickDrive(
        chassis,
        ()-> driver.getLeftY(),
        ()-> driver.getLeftX(),
        ()-> driver.getRightX()));

    driver.b().whileTrue(DriveCommands.getInRange(chassis, ()-> driver.getLeftY()));
    driver.leftBumper().whileTrue(DriveCommands.brake(chassis));
    driver.start().whileTrue(DriveCommands.resetHeading(chassis));
    driver.y().whileTrue(DriveCommands.toReef(chassis, ()-> driver.getLeftY(), 3.8));

    SyncroCommands.periodicUpdate();

    SyncroCommands.add("INTAKE", new wristSpeed(wrist, -0.5));

    SyncroCommands.add("STOP", new wristSpeed(wrist, 0));

    SyncroCommands.add("EJECT", new wristSpeed(wrist, 0.3));

    SyncroCommands.add("StopAll", new InstantCommand(()->{
      algae.stop();
      wrist.stop();
      elevator.stop();
    }, algae,wrist,elevator));

    //elevator.setDefaultCommand(new manualElevator(elevator, ()-> -operator.getLeftY() * 0.20));
    
    //operator.a().whileTrue(new StartEndCommand(()-> elevator.runSpeed(-0.1), ()-> elevator.stop(), elevator));
    //operator.b().whileTrue(new StartEndCommand(()-> elevator.runSpeed(0.2), ()-> elevator.stop(), elevator));
    //operator.a().whileTrue(new elevatorToHeight(elevator, 0.7));
    //operator.y().whileTrue(new elevatorToHeight(elevator, 0.739));
    //operator.x().whileTrue(new elevatorToHeight(elevator, 1.19));
    //operator.b().whileTrue(new elevatorToHeight(elevator, 1.86));

    operator.L3().whileTrue(new safe(elevator));
    //Retract
    operator.dPadDown().whileTrue(new retract(elevator, wrist));

    operator.povUp().whileTrue(new wristSpeed(wrist, -0.5));
    //L1
    operator.a().whileTrue(new Rise(elevator, wrist, 0.7, 3.2));
    //L2
    operator.b().whileTrue(new Rise(elevator, wrist, 0.88, 3.2));
    //L3
    operator.x().whileTrue(new Rise(elevator, wrist, 1.35, 3.5));
    //L4
    operator.y().whileTrue(new Rise(elevator, wrist, 1.96, 3.0));

    //Intake Coral
    operator.rightBumper().whileTrue(new feed(elevator, wrist, 1.38));
    //Outake Coral
    operator.RT().whileTrue(new wristSpeed(wrist, 0.5));

    //L2
    //operator.y().whileTrue(new elevatorToHeight(elevator, (ElevatorConstants.SETPOINT_L2)));
    /* 
    //Intake Alga
    operator.leftBumper().whileTrue(new AlgaeCommand(algae, 69, 0.5));
    //Outake Alga
    operator.LT().whileTrue(new AlgaeCommand(algae, 69, -0.5));

    //L1
    operator.aButton().whileTrue(new Rise(elevator, wrist, algae, 66, 3.2));

    //L2
    operator.bButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L2, 3.2));

    L3
    operator.xButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L3 + 2, 3.35));

    //L4
    operator.yButton().whileTrue(new Rise(elevator, wrist, algae, ElevatorConstants.SETPOINT_L4 +4, 3.0));

    //Elevator down
    operator.dPadDown().whileTrue(new speed(elevator, -0.3, wrist, algae));
  
    //Manual Retract
    operator.dPadUp().whileTrue(new wristToAngle(wrist, 0));

    //Colgador down
    operator.dPadLeft().whileTrue(new StartEndCommand(()-> hanger.setSpeed(-0.1), ()-> hanger.stop(), hanger));
    //Colgador up
    operator.dPadRight().whileTrue(new StartEndCommand(()-> hanger.setSpeed(0.5), ()-> hanger.stop(), hanger));
    */
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

