package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotAccepting;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotScore;
import org.firstinspires.ftc.teamcode.commands.claw.ClawRotateDown;
import org.firstinspires.ftc.teamcode.commands.claw.ClawRotateReady;
import org.firstinspires.ftc.teamcode.commands.claw.ClawRotateUp;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoReady;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoScore;
import org.firstinspires.ftc.teamcode.commands.lift.LiftAccepting;
import org.firstinspires.ftc.teamcode.commands.lift.LiftDecrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowRung;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class CommandRobot {
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, liftIncrement, liftDecrement, slamdown, specimen;
    public GamepadTrigger intakeAccept, intakeReject;

    private TeleOpMode mode;

    private final MultipleTelemetry telemetry;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;

    private Drivetrain drivetrain;

    private GamepadEx gamepad1;
    private GamepadEx gamepad2;
    private final OpModeCore opMode;

    public static int CLAW_ACCEPT_DELAY = 450;
    public static int CLAW_RETRACT_DELAY = 450;

    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, OpModeCore opMode, TeleOpMode mode) {
        this.telemetry = telemetry;

        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose(0, 0, 0));
        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);

        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);

        this.opMode = opMode;
        this.mode = mode;

        this.configureCommands();
        this.configureControls();
    }

    public CommandRobot(HardwareMap hwMap, Pose startPose, MultipleTelemetry telemetry, OpModeCore opMode) {
        this.telemetry = telemetry;

        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);

        this.opMode = opMode;

        this.configureCommands();
    }

    public void startThreads() {
        if (this.mode == TeleOpMode.OWEN || this.mode == TeleOpMode.RYAN)
            this.drivetrain.startThread(this.gamepad1, this.opMode);
        else
            this.drivetrain.startThread(this.gamepad2, this.opMode);

        this.lift.startThread(this.opMode);
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ClawRotateReady(this.telemetry, this.claw),
                new ClawPivotAccepting(this.telemetry, this.claw),
                new WaitCommand(CommandRobot.CLAW_RETRACT_DELAY),
                new ExtendoReady(this.telemetry, this.extendo)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ExtendoAccepting(this.telemetry, this.extendo),
                new WaitCommand(CommandRobot.CLAW_ACCEPT_DELAY),
                new ClawRotateDown(this.telemetry, this.claw),
                new ClawPivotAccepting(this.telemetry, this.claw)
        );

        this.highBasket = new SequentialCommandGroup(
                new LiftHighBasket(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawRotateUp(this.telemetry, this.claw)
        );

        this.lowBasket = new SequentialCommandGroup(
                new LiftLowBasket(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawRotateUp(this.telemetry, this.claw)
        );

        this.highRung = new SequentialCommandGroup(
                new LiftHighRung(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawRotateUp(this.telemetry, this.claw)
        );

        this.lowRung = new SequentialCommandGroup(
                new LiftLowRung(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawRotateUp(this.telemetry, this.claw)

        );

        this.specimen = new SequentialCommandGroup(
                new LiftLowRung(this.telemetry, this.lift),
                new ExtendoReady(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawRotateReady(this.telemetry, this.claw)
        );

        this.intakeAccept = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, d -> this.claw.setClawPower(-d), this.gamepad2);
        this.intakeReject = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, this.claw::setClawPower, this.gamepad2);

        this.liftIncrement = new LiftIncrement(this.telemetry, this.lift);

        this.liftDecrement = new LiftDecrement(this.telemetry, this.lift);

    }

    public void configureControls() {
        switch (this.mode) {
            /* ------------------------------------- */
            /* --------------- OWEN ---------------- */
            /* ------------------------------------- */

            case OWEN:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(this.ready);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.accepting);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.lowRung);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highRung);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(this.highBasket);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(this.lowBasket);
                break;

            /* ------------------------------------- */
            /* --------------- RYAN ---------------- */
            /* ------------------------------------- */

            case RYAN:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.accepting);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highBasket);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(this.ready);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.lowBasket);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.lowRung);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.highRung);
                break;

            /* ------------------------------------- */
            /* ------------ RYAN & KELLY ----------- */
            /* ------------------------------------- */

            case RYAN_KELLY:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.accepting);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highBasket);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(this.ready);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(this.lowBasket);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.lowRung);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.highRung);
                break;
        }
    }

    public enum TeleOpMode {
        OWEN,
        RYAN,
        RYAN_KELLY
    }

    public Follower getFollower() {
        return this.drivetrain.getFollower();
    }
}
