package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.arm.ArmAccepting;
import org.firstinspires.ftc.teamcode.commands.arm.ArmIntake;
import org.firstinspires.ftc.teamcode.commands.arm.ArmReady;
import org.firstinspires.ftc.teamcode.commands.arm.ArmScore;
import org.firstinspires.ftc.teamcode.commands.claw.ClawClose;
import org.firstinspires.ftc.teamcode.commands.claw.ClawOpen;
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
import org.firstinspires.ftc.teamcode.commands.lift.LiftSlam;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotAccepting;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotReady;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotScore;
import org.firstinspires.ftc.teamcode.commands.wrist.WristAccepting;
import org.firstinspires.ftc.teamcode.commands.wrist.WristLeft;
import org.firstinspires.ftc.teamcode.commands.wrist.WristReady;
import org.firstinspires.ftc.teamcode.commands.wrist.WristRight;
import org.firstinspires.ftc.teamcode.commands.wrist.WristScore;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class CommandRobot {
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, liftIncrement, liftDecrement, specimen, open, close, intakeClose, wristRight, wristLeft, slam;

    private TeleOpMode mode;

    private final MultipleTelemetry telemetry;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;
    private final Pivot pivot;
    private final Wrist wrist;
    private final Arm arm;

    private Drivetrain drivetrain;

    private GamepadEx gamepad1;
    private GamepadEx gamepad2;
    private final OpModeCore opMode;

    private boolean intakeToggle;

    public static int CLAW_ACCEPT_DELAY = 450;
    public static int CLAW_RETRACT_DELAY = 450;
    public static int READY_DELAY = 1250;
    public static int SLAM_OPEN_DELAY = 500;

    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, OpModeCore opMode, TeleOpMode mode) {
        this.telemetry = telemetry;

        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose(0, 0, 0));
        this.lift = new Lift(hwMap, telemetry);
        this.extendo = new Extendo(hwMap, telemetry);
        this.claw = new Claw(hwMap, telemetry);
        this.pivot = new Pivot(hwMap, telemetry);
        this.wrist = new Wrist(hwMap, telemetry);
        this.arm = new Arm(hwMap, telemetry);

        this.intakeToggle = true;

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
        this.pivot = new Pivot(hwMap, telemetry);
        this.wrist = new Wrist(hwMap, telemetry);
        this.arm = new Arm(hwMap, telemetry);

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
                new ClawClose(this.telemetry, this.claw),
                new WristReady(this.telemetry, this.wrist),
                new PivotReady(this.telemetry, this.pivot),
                new ArmReady(this.telemetry, this.arm),
                //todo: find correct wait amount
                new WaitCommand(READY_DELAY),
                new ExtendoReady(this.telemetry, this.extendo),
                new LiftAccepting(this.telemetry, this.lift)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ExtendoAccepting(this.telemetry, this.extendo),
                //todo: find correct wait amount
                new WaitCommand(READY_DELAY),
                new ClawOpen(this.telemetry, this.claw),
                new ArmAccepting(this.telemetry, this.arm),
                new PivotAccepting(this.telemetry, this.pivot),
                new WristAccepting(this.telemetry, this.wrist)
        );

        this.highBasket = new SequentialCommandGroup(
                new ClawClose(this.telemetry, this.claw),
                new LiftHighBasket(this.telemetry, this.lift),
                new WristScore(this.telemetry, this.wrist),
                new PivotScore(this.telemetry, this.pivot),
                new ArmScore(this.telemetry, this.arm),
                new ExtendoScore(this.telemetry, this.extendo)
        );

        this.lowBasket = new SequentialCommandGroup(
                new ClawClose(this.telemetry, this.claw),
                new LiftLowBasket(this.telemetry, this.lift),
                new WristScore(this.telemetry, this.wrist),
                new PivotScore(this.telemetry, this.pivot),
                new ArmScore(this.telemetry, this.arm),
                new ExtendoScore(this.telemetry, this.extendo)
        );

        this.highRung = new SequentialCommandGroup(
                new ClawClose(this.telemetry, this.claw),
                new LiftHighRung(this.telemetry, this.lift),
                new WristScore(this.telemetry, this.wrist),
                new PivotScore(this.telemetry, this.pivot),
                new ArmScore(this.telemetry, this.arm),
                new ExtendoScore(this.telemetry, this.extendo)
        );

        this.lowRung = new SequentialCommandGroup(
                new ClawClose(this.telemetry, this.claw),
                new LiftLowRung(this.telemetry, this.lift),
                new WristScore(this.telemetry, this.wrist),
                new PivotScore(this.telemetry, this.pivot),
                new ArmScore(this.telemetry, this.arm),
                new ExtendoScore(this.telemetry, this.extendo)
        );

        this.specimen = new SequentialCommandGroup(
                new ClawOpen(this.telemetry, this.claw),
                new LiftAccepting(this.telemetry, this.lift),
                new WristScore(this.telemetry, this.wrist),
                new PivotScore(this.telemetry, this.pivot),
                new ArmScore(this.telemetry, this.arm),
                new ExtendoScore(this.telemetry, this.extendo)
        );

        this.slam = new ParallelCommandGroup(
                new LiftSlam(this.telemetry, this.lift),
                new SequentialCommandGroup(
                        new WaitCommand(SLAM_OPEN_DELAY),
                        new ClawOpen(this.telemetry, this.claw)
                )
        );

        this.open = new ClawOpen(this.telemetry, this.claw);

        this.close = new ClawClose(this.telemetry, this.claw);

        this.intakeClose = new SequentialCommandGroup(
                new ArmIntake(this.telemetry, this.arm),
                new WaitCommand(250),
                new ClawClose(this.telemetry, this.claw)
        );

        this.liftIncrement = new LiftIncrement(this.telemetry, this.lift);

        this.liftDecrement = new LiftDecrement(this.telemetry, this.lift);

        this.wristRight = new WristRight(this.telemetry, this.wrist);

        this.wristLeft = new WristLeft(this.telemetry, this.wrist);
    }

    public void configureControls() {
        switch (this.mode) {
            /* ------------------------------------- */
            /* --------------- OWEN ---------------- */
            /* ------------------------------------- */

            case OWEN:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(new ConditionalCommand(this.ready, this.accepting, () -> {
                            if (this.lift.getPosition() > 100) this.intakeToggle = true;
                            else this.intakeToggle = !this.intakeToggle;
                            return this.intakeToggle;
                        }));
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(this.wristRight);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(this.wristLeft);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highRung);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.specimen);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.slam);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(new ConditionalCommand(this.close, this.intakeClose, () -> this.intakeToggle));
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open);
                break;

            /* ------------------------------------- */
            /* --------------- RYAN ---------------- */
            /* ------------------------------------- */

            case RYAN:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highBasket);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(this.accepting, this.ready);
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
