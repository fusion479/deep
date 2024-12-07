package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
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

public class CommandRobot {
    private final MultipleTelemetry telemetry;
    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;
    private final OpModeCore opMode;
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, score, liftIncrement, liftDecrement, open, close;
    private Drivetrain drivetrain;
    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    private boolean isExtended = false;

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
    }

    // AUTON
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
        this.drivetrain.startThread(this.gamepad1, this.opMode);
        this.lift.startThread(this.opMode);
        // TODO: Add in more threads if needed
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new ClawPivotScore(this.telemetry, this.claw),
                new WaitCommand(100),
                new ExtendoReady(this.telemetry, this.extendo),
                new LiftAccepting(this.telemetry, this.lift)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftAccepting(this.telemetry, this.lift),
                new ExtendoAccepting(this.telemetry, this.extendo),
                new WaitCommand(100),
                new ClawPivotAccepting(this.telemetry, this.claw)
        );

        this.highBasket = new ParallelCommandGroup(
                new LiftHighBasket(this.telemetry, this.lift),
                new ExtendoScore(this.telemetry, this.extendo),
                new ClawPivotScore(this.telemetry, this.claw)
        );

        this.lowBasket = new ParallelCommandGroup(
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

        this.score = new SequentialCommandGroup(
                new WaitCommand(800),
                new ClawPivotScore(this.telemetry, this.claw),
                new ExtendoReady(this.telemetry, this.extendo),
                new LiftAccepting(this.telemetry, this.lift)
        );

        this.liftIncrement = new LiftIncrement(this.telemetry, this.lift);

        this.liftDecrement = new LiftDecrement(this.telemetry, this.lift);

        this.open = new ClawOpen(this.telemetry, this.claw);

        this.close = new ClawClose(this.telemetry, this.claw);
    }

    // TODO: Configure controls for gamepad (talk with drive team)
    public void configureControls() {
        this.gamepad2.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new ConditionalCommand(
                        this.lowBasket,
                        this.lowBasket,
                        () -> {
                            toggle();
                            return active();
                        }));
        this.gamepad2.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new ConditionalCommand(
                        this.highBasket,
                        this.highBasket,
                        () -> {
                            toggle();
                            return active();
                        }));
        this.gamepad2.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ConditionalCommand(
                        this.ready,
                        this.accepting,
                        () -> {
                            toggle();
                            return active();
                        }
                ));
        this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(this.liftIncrement);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(this.liftDecrement);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new ConditionalCommand(
                        this.lowRung,
                        this.lowRung,
                        () -> {
                           toggle();
                           return active();
                        }));
        this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new ConditionalCommand(
                        this.highRung,
                        this.highRung,
                        () -> {
                            toggle();
                            return active();
                        }));
        this.gamepad2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(this.open);
        this.gamepad2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(this.close);
    }

    private void toggle(){
        isExtended = !isExtended;
    }

    private boolean active(){
        return isExtended;
    }
}
