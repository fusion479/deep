package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.claw.ClawClose;
import org.firstinspires.ftc.teamcode.commands.claw.ClawOpen;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotAccepting;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotScore;
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
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class CommandRobot {
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, liftIncrement, liftDecrement, open, close, slamdown, specimen;
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
    public static int LIFT_DELAY = 250;

    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, OpModeCore opMode, TeleOpMode mode) {
        this.telemetry = telemetry;

        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose2d(0, 0, 0));
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

    public CommandRobot(HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry, OpModeCore opMode) {
        this.telemetry = telemetry;

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
        this.drivetrain.startThread(this.mode == TeleOpMode.OWEN || this.mode == TeleOpMode.RYAN ? this.gamepad1 : this.gamepad2, this.opMode);
        this.lift.startThread(this.opMode);
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ClawClose(this.telemetry, this.claw),
                new ClawPivotScore(this.telemetry, this.claw),
                new WaitCommand(CommandRobot.CLAW_RETRACT_DELAY),
                new ExtendoReady(this.telemetry, this.extendo)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ExtendoAccepting(this.telemetry, this.extendo),
                new WaitCommand(CommandRobot.CLAW_ACCEPT_DELAY),
                new ClawOpen(this.telemetry, this.claw),
                new ClawPivotAccepting(this.telemetry, this.claw)
        );

        this.highBasket = new SequentialCommandGroup(
                new LiftHighBasket(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw)
        );

        this.lowBasket = new SequentialCommandGroup(
                new LiftLowBasket(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw)
        );

        this.highRung = new SequentialCommandGroup(
                new LiftHighRung(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw)
        );

        this.lowRung = new SequentialCommandGroup(
                new LiftLowRung(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw)
        );

        this.specimen = new SequentialCommandGroup(
                new LiftLowRung(this.telemetry, this.lift),
                new ExtendoReady(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw),
                new ClawOpen(this.telemetry, this.claw)
        );

        this.liftIncrement = new LiftIncrement(this.telemetry, this.lift);

        this.liftDecrement = new LiftDecrement(this.telemetry, this.lift);

        this.open = new ClawOpen(this.telemetry, this.claw);

        this.close = new ClawClose(this.telemetry, this.claw);
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
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(this.close);
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
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(this.close);
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
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(this.close);

                break;
        }
    }

    public enum TeleOpMode {
        OWEN,
        RYAN,
        RYAN_KELLY
    }
}
