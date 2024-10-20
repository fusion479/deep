package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

public class CommandRobot {
    private final MultipleTelemetry telemetry;

    private final Drivetrain drivetrain;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;
    private final OpModeCore opMode;
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, score, liftIncrement, liftDecrement, extendoIncrement, extendoDecrement;
    public GamepadTrigger intakeAccept, intakeReject;
    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

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

        this.intakeAccept = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, d -> this.claw.setClawPower(-d), this.gamepad2);
        this.intakeReject = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, this.claw::setClawPower, this.gamepad2);
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
        this.claw.startThreads(this.opMode);
        // TODO: Add in more threads if needed
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new ClawSetPosition(this.telemetry, this.claw, Claw.READY),
                new WaitCommand(100),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.ACCEPTING),
                new WaitCommand(100),
                new ClawSetPosition(this.telemetry, this.claw, Claw.DOWN)
        );

        this.highBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGH_BASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP)
        );

        this.lowBasket = new ParallelCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOW_BASKET),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP)
        );

        this.highRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.HIGH_RUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP)
        );

        this.lowRung = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, Lift.LOW_RUNG),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.SCORE),
                new ClawSetPosition(this.telemetry, this.claw, Claw.UP)
        );

        this.score = new SequentialCommandGroup(
                new WaitCommand(800),
                new ClawSetPosition(this.telemetry, this.claw, Claw.READY),
                new ExtendoSetPosition(this.telemetry, this.extendo, Extendo.READY),
                new LiftSetPosition(this.telemetry, this.lift, Lift.ACCEPTING)
        );

        this.liftIncrement = new SequentialCommandGroup(
                new LiftSetPosition(this.telemetry, this.lift, this.lift.getTarget() + Lift.INCREMENT)
        );

        this.liftDecrement = new SequentialCommandGroup(
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
        this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(this.accepting);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(this.lowBasket);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(this.highBasket);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(this.ready);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(this.liftIncrement);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(this.liftDecrement);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(this.score);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(this.lowRung);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(this.highRung);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(this.extendoDecrement);
        this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(this.extendoIncrement);
    }
}
