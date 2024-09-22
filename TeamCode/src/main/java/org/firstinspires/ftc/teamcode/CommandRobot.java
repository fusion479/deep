package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.claw.ClawSetPosition;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSetPosition;
import org.firstinspires.ftc.teamcode.commands.lift.LiftSetPosition;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

public class CommandRobot {
    private final MultipleTelemetry telemetry;

    private final Drivetrain drivetrain;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;

    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    private final OpModeCore opMode;

    public Command ready, accepting, scoreHighBasket, scoreHighRung, scoreLowBasket, scoreLowRung, score, liftIncrement, liftDecrement, extendoIncrement, extendoDecrement;

    // TELEOP
    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, OpModeCore opMode) {
        this.telemetry = telemetry;

        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose2d(0, 0, 0));
        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);

        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);

        this.opMode = opMode;

        this.configureCommands();
        this.configureControls();

        // TODO: Implement triggers for claw motors
    }

    // AUTON
    public CommandRobot(HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry, OpModeCore opMode) {
        this.telemetry = telemetry;

        this.drivetrain = new Drivetrain(hwMap, telemetry, startPose);
        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);

        this.opMode = opMode;

        this.configureCommands();
    }

    public MecanumDrive getDrive() {
        return this.drivetrain.getDrive();
    }

    public void startThreads() {
        this.drivetrain.startThread(this.gamepad1, this.opMode);
        this.lift.startThread(this.opMode);
        // TODO: Add in more threads if needed
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.READY),
                new WaitCommand(100),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.ACCEPTING),
                new WaitCommand(100),
                new ClawSetPosition(this.telemetry, this.claw, Claw.DOWN),
                new ClawSetPosition(this.telemetry, this.claw, Claw.OPEN)
        );

        this.scoreHighBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGH_BASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreLowBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOW_BASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreHighRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGH_RUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.scoreLowRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOW_RUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE)
        );

        this.score = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.OPEN),
                new WaitCommand(800),
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.READY),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING)
        );

        this.liftIncrement = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new LiftSetPosition(this.telemetry, this.lift, this.lift.getTarget() + Lift.INCREMENT)
        );

        this.liftDecrement = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.CLOSE),
                new LiftSetPosition(this.telemetry, this.lift, this.lift.getTarget() - Lift.INCREMENT)

        );

        this.extendoIncrement = new SequentialCommandGroup(
                new ExtendoSetPosition(this.telemetry, this.extendo, this.extendo.getPosition() + Extendo.INCREMENT)
        );

        this.extendoDecrement = new SequentialCommandGroup(
                new ExtendoSetPosition(this.telemetry, this.extendo, this.extendo.getPosition() - Extendo.INCREMENT)
        );
    }

    // TODO: Configure controls for gamepad (talk with driveteam)
    public void configureControls() {
    }
}
