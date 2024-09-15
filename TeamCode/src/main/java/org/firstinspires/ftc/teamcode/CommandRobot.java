package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.claw.ClawSetPosition;
import org.firstinspires.ftc.teamcode.commands.claw.pivot.PivotSetPosition;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoIncrement;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSetPosition;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftSetPosition;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;

public class CommandRobot {
    public final Command ready, accepting, scoreHighBasket, scoreHighRung, scoreLowBasket, scoreLowRung, score, liftlower, liftraise, extendolower, extendoraise;
    private final Drivetrain drivetrain;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;
    private final MultipleTelemetry telemetry;
    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    // TELEOP
    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, CommandOpMode opMode) {
        this.telemetry = telemetry;
        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose2d(0, 0, 0));
        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);
        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);

        this.configureControls();
        this.drivetrain.startThread(this.gamepad1, opMode);
        this.lift.startThread(opMode);

        this.ready = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.READY),
                new WaitCommand(100),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.BOTTOM)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.BOTTOM),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.ACCEPTING),
                new WaitCommand(100),
                new PivotSetPosition(this.telemetry, this.claw, Claw.DOWN),
                new ClawSetPosition(this.telemetry, this.claw, Claw.OPEN)
        );

        this.scoreHighBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGHBASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreLowBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOWBASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreHighRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGHRUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreLowRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOWRUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.score = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.OPEN),
                new WaitCommand(800),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new PivotSetPosition(this.telemetry, this.claw, Claw.READY),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.BOTTOM)
        );

        this.liftraise = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new LiftIncrement(this.lift, Lift.INCREMENT)
        );

        this.liftlower = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new LiftIncrement(this.lift, -Lift.INCREMENT)
        );

        this.extendoraise = new SequentialCommandGroup(
                new ExtendoIncrement(this.extendo, Extendo.INCREMENT)
        );

        this.extendolower = new SequentialCommandGroup(
                new ExtendoIncrement(this.extendo, -Extendo.INCREMENT)
        );

        //todo: implement triggers for claw motors
    }

    // AUTON
//    public CommandRobot(HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry, CommandOpMode opMode) {
//        this.telemetry = telemetry;
//        this.drivetrain = new Drivetrain(hwMap, telemetry, startPose);
//        this.lift = new Lift(hwMap, telemetry);
//        this.extendo = new Extendo(hwMap, telemetry);
//        this.claw = new Claw(hwMap, telemetry);
//        this.lift.startThread(opMode);
//    }

    public MecanumDrive getDrive() {
        return this.drivetrain.getDrive();
    }

    public void configureControls() {
        // controls
    }
}
