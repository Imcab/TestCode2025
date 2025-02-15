// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.com.AlgaeCommand;
import frc.robot.com.DriveCommands;
import frc.robot.com.GenericScore;
import frc.robot.com.Retract;
import frc.robot.com.i;
import frc.robot.com.k;
import frc.robot.com.m;
import frc.robot.com.r;
import frc.robot.com.speed;
import frc.robot.sub.AlgaeWrist;
import frc.robot.sub.CoralWrist;
import frc.robot.sub.Elevator;
import frc.robot.sub.Drive.swerve;

public class RobotContainer {

  Elevator e = new Elevator();
  AlgaeWrist a = new AlgaeWrist();
  CoralWrist w = new CoralWrist();

  CommandXboxController c = new CommandXboxController(0);
  CommandXboxController c2 = new CommandXboxController(1);

  swerve miaw = new swerve();

  public RobotContainer() {
    configureBindings();
    
  }

  private void configureBindings() {
    //Drive modes. Normal Drive
    /*miaw.setDefaultCommand(DriveCommands.joystickDrive(miaw, ()-> c2.getLeftY(),  ()-> c2.getLeftX(),  ()-> -c2.getRightX()));
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

    //0.132
    //c2.a().whileTrue(new AlgaeCommand(a, 60, 0, 0));
    //c2.b().whileTrue(new AlgaeCommand(a, 16, 0, 0));
    //c2.y().whileTrue(new AlgaeCommand(a, 35, 0, 0));
    c2.a().whileTrue(new i(w, 0));
    c2.leftBumper().whileTrue(new StartEndCommand(()-> {w.wheelSpeed(-0.5);}, ()-> {w.wheelSpeed(0);}, w));
    
    c2.povUp().whileTrue(new GenericScore(w, e, 66, 0.8, a));
    c2.povLeft().whileTrue(new SequentialCommandGroup(new GenericScore(w, e, ElevatorConstants.SETPOINT_L2, 0.8, a), new speed(e, -0.12, w, a)));
    c2.povRight().whileTrue(new SequentialCommandGroup(new GenericScore(w, e, 119, 0.8, a), new speed(e, -0.4, w, a)));
    c2.povDown().whileTrue(new speed(e, -0.4, w, a));

    c.leftBumper().whileTrue(new m(e, ()-> c.getLeftY() * -0.2));
    c.b().whileTrue(new k(e, ElevatorConstants.SETPOINT_FEEDER));
    c.x().whileTrue(new k(e, 84)); //84 L2
    c.y().whileTrue(new k(e, ElevatorConstants.SETPOINT_L3));
    c.rightBumper().whileTrue(new k(e, ElevatorConstants.SETPOINT_L4));
    c.a().whileTrue(new k(e, 68));
    c.start().whileTrue(new InstantCommand(()->{e.resetEncoders();}, e));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

