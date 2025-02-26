// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.BulukLib.Dashboard.Syncro.SyncroCommands;
import frc.robot.BulukLib.Dashboard.Syncro.SyncroLinker;
import frc.robot.BulukLib.Swerve.BulukXboxController;
import frc.robot.Commands.ResetUtils;
import frc.robot.Commands.algae.AlgaeCommand;
import frc.robot.Commands.auto.RiseAuto;
import frc.robot.Commands.auto.retractAuto;
import frc.robot.Commands.climber.climb;
import frc.robot.Commands.coral.coralBeam;
import frc.robot.Commands.coral.coralManual;
import frc.robot.Commands.coral.coralToRotation;
import frc.robot.Commands.coral.wristSpeed;
import frc.robot.Commands.elevatorCommands.Rise;
import frc.robot.Commands.elevatorCommands.feed;
import frc.robot.Commands.elevatorCommands.joystickElevator;
import frc.robot.Commands.elevatorCommands.retract;
import frc.robot.Commands.swerve.DriveCommands;
import frc.robot.Subsystems.Components.AlgaeWrist;
import frc.robot.Subsystems.Components.CoralWrist;
import frc.robot.Subsystems.Components.Elevator;
import frc.robot.Subsystems.Components.Hanger.HangerClimber;
import frc.robot.Subsystems.Components.Hanger.SparkHanger;
import frc.robot.Subsystems.Drive.swerve;

public class RobotContainer {

  swerve chassis = new swerve();

  Elevator elevator = new Elevator();
  AlgaeWrist algae = new AlgaeWrist();
  CoralWrist wrist = new CoralWrist();
  HangerClimber climber = new HangerClimber(new SparkHanger());

  CommandXboxController driver = new CommandXboxController(0);
 
  BulukXboxController operator = new BulukXboxController(1);

  SendableChooser<Command> autoChooser = new SendableChooser<>();

  public RobotContainer() {
    SyncroLinker.listen();

    SmartDashboard.putData(autoChooser);

    configureBindings();
    
  }

  private void configureBindings() {

    //testController.a().whileTrue(new InstantCommand(()-> , chassis));

    chassis.setDefaultCommand(
      DriveCommands.joystickDrive(
        chassis,
        ()-> driver.getLeftY() * 0.8,
        ()-> driver.getLeftX() * 0.8,
        ()-> -driver.getRightX() * 0.7));

    driver.leftBumper().whileTrue(DriveCommands.brake(chassis));
    driver.start().whileTrue(DriveCommands.resetHeading(chassis));
    //driver.b().whileTrue(DriveCommands.toReef(chassis,3.8));
    driver.povLeft().whileTrue(DriveCommands.moveInX(chassis, -0.4));
    driver.povRight().whileTrue(DriveCommands.moveInX(chassis, 0.4));
    driver.povUp().whileTrue(DriveCommands.moveInY(chassis, -0.4));
    driver.povDown().whileTrue(DriveCommands.moveInY(chassis, 0.4));

    driver.rightBumper().whileTrue(new climb(climber, 0.2));
    driver.leftBumper().whileTrue(new climb(climber, -0.1));
    driver.leftTrigger(0.5).whileTrue(DriveCommands.joystickDrive(
      chassis,
      ()-> driver.getLeftY() * 0.4,
      ()-> driver.getLeftX() * 0.4,
      ()-> driver.getRightX() * 0.3));


    driver.a().whileTrue(new RiseAuto(elevator, wrist, 0.84, 3.78));
    //DASHBOARD
    SyncroCommands.periodicUpdate();

    SyncroCommands.add("INTAKE CORAL", new wristSpeed(wrist, -0.5));

    SyncroCommands.add("STOP CORAL", new wristSpeed(wrist, 0));

    SyncroCommands.add("EJECT CORAL", new wristSpeed(wrist, 0.3));

    SyncroCommands.add("RESET_ELEVATOR", ResetUtils.resetElevatorEncoders(elevator));

    SyncroCommands.add("RESET WRIST", ResetUtils.resetWristEncoder(wrist));

    SyncroCommands.add("STOP_ALL", new InstantCommand(()->{
      algae.stop();
      wrist.stop();
      elevator.stop();
    }, algae,wrist,elevator));

    //DASHBOARD 

    //NAMED COMMANDS

    NamedCommands.registerCommand("out", new wristSpeed(wrist, 0.7).withTimeout(0.6));
    NamedCommands.registerCommand("L2", new RiseAuto(elevator, wrist, 0.84, 3.78));
    NamedCommands.registerCommand("L4", new RiseAuto(elevator, wrist, 1.87, 3.3));
    NamedCommands.registerCommand("L3", new RiseAuto(elevator, wrist, 1.255, 3.78));
    NamedCommands.registerCommand("Retract", new retractAuto(elevator, wrist));
    NamedCommands.registerCommand("Forward", DriveCommands.moveInY(chassis, -0.2).withTimeout(1.2));
    NamedCommands.registerCommand("Back", DriveCommands.moveInY(chassis, 0.4).withTimeout(0.8));
    NamedCommands.registerCommand("lim", DriveCommands.toReef(chassis,()-> 0.2, 3.5));

    //NAMED COMMANDS

    //AUTO CHOOSER
    autoChooser.addOption("L3_Front", new PathPlannerAuto("autoTest"));
    autoChooser.addOption("L4_Front", new PathPlannerAuto("L4Forward"));
    autoChooser.addOption("LIME", new PathPlannerAuto("Lime"));
    //autoChooser.addOption("L4_Front", new PathPlannerAuto("autoTest"));
    //autoChooser.addOption("L2_Front", new PathPlannerAuto("autoTest"));
  
    //AUTO CHOOSER

    operator.L3().whileTrue(new joystickElevator(elevator, ()-> -operator.getLeftY() * 0.2));
    operator.R3().whileTrue(new coralManual(wrist, ()-> operator.getRightY() * 0.1));

    //Resets wrist Encoder
    operator.menu().whileTrue(ResetUtils.resetWristEncoder(wrist));
    //StopAll
    operator.view().whileTrue(new InstantCommand(()->{
      algae.stop();
      wrist.stop();
      elevator.stop();
    }, algae,wrist,elevator));
    //L1
    operator.a().whileTrue(new Rise(elevator, wrist, 0.65, 3.2));
    //L2
    operator.b().whileTrue(new Rise(elevator, wrist, 0.84, 3.8));
    //L3
    operator.x().whileTrue(new Rise(elevator, wrist, 1.23, 3.78));
    //L4
    operator.y().whileTrue(new Rise(elevator, wrist, 1.81, 3.2));

    //Intake Coral
    operator.rightBumper().whileTrue(new feed(elevator, wrist, 1.62, operator, driver));
    //Outake Coral
    operator.RT().whileTrue(new wristSpeed(wrist, 0.7));

    //Retract
    operator.dPadDown().whileTrue(new retract(elevator, wrist));
    //Home
    operator.dPadUp().whileTrue(new coralToRotation(wrist, 0.1));

    //Elevator to Algae
    operator.dPadLeft().whileTrue(new Rise(elevator, wrist, 1.62, 0.05));

    //Intake Alga
    operator.leftBumper().whileTrue(new AlgaeCommand(algae, 62, 0.5));
    //Outake Alga
    operator.LT().whileTrue(new AlgaeCommand(algae, 69, -0.5));
  
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}

