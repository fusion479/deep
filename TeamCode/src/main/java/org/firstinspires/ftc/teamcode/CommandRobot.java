package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.arm.ArmAccepting;
import org.firstinspires.ftc.teamcode.commands.arm.ArmBasket;
import org.firstinspires.ftc.teamcode.commands.arm.ArmIntake;
import org.firstinspires.ftc.teamcode.commands.arm.ArmReady;
import org.firstinspires.ftc.teamcode.commands.arm.ArmSpecimen;
import org.firstinspires.ftc.teamcode.commands.claw.ClawClose;
import org.firstinspires.ftc.teamcode.commands.claw.ClawOpen;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoBasket;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoReady;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSpecimen;
import org.firstinspires.ftc.teamcode.commands.lift.LiftAccepting;
import org.firstinspires.ftc.teamcode.commands.lift.LiftDecrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftSlam;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotAccepting;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotBasket;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotIntake;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotReady;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotSpecimen;
import org.firstinspires.ftc.teamcode.commands.wrist.WristAccepting;
import org.firstinspires.ftc.teamcode.commands.wrist.WristBasket;
import org.firstinspires.ftc.teamcode.commands.wrist.WristLeft;
import org.firstinspires.ftc.teamcode.commands.wrist.WristReady;
import org.firstinspires.ftc.teamcode.commands.wrist.WristRight;
import org.firstinspires.ftc.teamcode.commands.wrist.WristSpecimen;
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
    public Command ready, accepting, highBasket, highRung, lowBasket, lowRung, liftIncrement, liftDecrement, specimen, wristRight, wristLeft, intake, open, close, ensure, slam;
    public Command pivotDecrement, pivotIncrement;
    private TeleOpMode mode;

    private final Lift lift;
    private final Extendo extendo;
    private final Claw claw;
    private final Pivot pivot;
    private final Wrist wrist;
    private final Arm arm;

    private final Drivetrain drivetrain;

    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    private boolean intakeToggle;

    public CommandRobot(HardwareMap hwMap, Gamepad gamepad1, Gamepad gamepad2, TeleOpMode mode) {
        this.drivetrain = new Drivetrain(hwMap, new Pose(0, 0, 0));
        this.lift = new Lift(hwMap);
        this.extendo = new Extendo(hwMap);
        this.claw = new Claw(hwMap);
        this.pivot = new Pivot(hwMap);
        this.wrist = new Wrist(hwMap);
        this.arm = new Arm(hwMap);

        this.intakeToggle = true;

        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);

        this.mode = mode;

        this.configureCommands();
        this.configureControls();
    }

    public CommandRobot(HardwareMap hwMap, Pose startPose) {
        this.lift = new Lift(hwMap);
        this.extendo = new Extendo(hwMap);
        this.claw = new Claw(hwMap);
        this.pivot = new Pivot(hwMap);
        this.wrist = new Wrist(hwMap);
        this.arm = new Arm(hwMap);

        this.drivetrain = new Drivetrain(hwMap, startPose);

        this.configureCommands();
    }

    public void startThreads(OpModeCore opMode) {
        if (this.mode == TeleOpMode.OWEN || this.mode == TeleOpMode.RYAN)
            this.drivetrain.startThread(this.gamepad1, opMode);
        else
            this.drivetrain.startThread(this.gamepad2, opMode);

        this.extendo.startThread(opMode);
        this.lift.startThread(opMode);
    }

    public void startAutoThreads(OpModeCore opMode) {
        this.extendo.startThread(opMode);
        this.lift.startThread(opMode);
    }

    public void configureCommands() {
        this.ready = new SequentialCommandGroup(
                new ClawClose(this.claw),
                new WristReady(this.wrist),
                new PivotReady(this.pivot),
                new ArmReady(this.arm),
                new ExtendoReady(this.extendo),
                new LiftAccepting(this.lift)
        );

        this.slam = new SequentialCommandGroup(
                new LiftSlam(this.lift),
                new WaitCommand(100),
                new PivotAccepting(this.pivot),
                new WaitCommand(400),
                new ClawOpen(this.claw)
        );

        this.accepting = new SequentialCommandGroup(
                new LiftAccepting(this.lift),
                new ExtendoAccepting(this.extendo),
                new WaitCommand(400),
                new ClawOpen(this.claw),
                new ArmAccepting(this.arm),
                new PivotAccepting(this.pivot),
                new WristAccepting(this.wrist)
        );

        this.highBasket = new SequentialCommandGroup(
                new ClawClose(this.claw),
                new LiftHighBasket(this.lift),
                new WristBasket(this.wrist),
                new PivotBasket(this.pivot),
                new ArmBasket(this.arm),
                new ExtendoBasket(this.extendo)
        );

        this.lowBasket = new SequentialCommandGroup(
                new ClawClose(this.claw),
                new LiftLowBasket(this.lift),
                new WristBasket(this.wrist),
                new PivotBasket(this.pivot),
                new ArmBasket(this.arm),
                new ExtendoBasket(this.extendo)
        );

        this.highRung = new SequentialCommandGroup(
                new ClawClose(this.claw),
                new LiftHighRung(this.lift),
                new ExtendoSpecimen(this.extendo),
                new WaitCommand(350),
                new PivotSpecimen(this.pivot),
                new WristSpecimen(this.wrist),
                new ArmSpecimen(this.arm)
        );

        this.lowRung = new SequentialCommandGroup(
                new WristSpecimen(this.wrist),
                new ArmSpecimen(this.arm),
                new WaitCommand(250),
                new ClawClose(this.claw),
                new LiftLowRung(this.lift),
                new PivotSpecimen(this.pivot),
                new ExtendoSpecimen(this.extendo)
        );

        this.specimen = new SequentialCommandGroup(
                new LiftAccepting(this.lift),
                new WristBasket(this.wrist),
                new PivotBasket(this.pivot),
                new ArmBasket(this.arm),
                new ExtendoBasket(this.extendo),
                new WaitCommand(200),
                new ClawOpen(this.claw)
        );

        this.intake = new SequentialCommandGroup(
                new ClawOpen(this.claw),
                new WaitCommand(100),
                new ArmIntake(this.arm),
                new PivotIntake(this.pivot),
                new WaitCommand(200),
                new ClawClose(this.claw),
                new WaitCommand(200),
                new PivotAccepting(this.pivot),
                new ArmAccepting(this.arm)
        );

        this.ensure = new SequentialCommandGroup(
                new ArmAccepting(this.arm),
                new InstantCommand(() -> this.pivot.setPosition(0.7)),
                new WaitCommand(750),
                new ClawOpen(this.claw)
        );

        this.liftIncrement = new LiftIncrement(this.lift);

        this.liftDecrement = new LiftDecrement(this.lift);

        this.wristRight = new WristRight(this.wrist);

        this.wristLeft = new WristLeft(this.wrist);

        this.open = new ClawOpen(this.claw);

        this.close = new ClawClose(this.claw);
    }

    public void configureControls() {
        switch (this.mode) {

            /* ------------------------------------- */
            /* --------------- OWEN ---------------- */
            /* ------------------------------------- */

            case OWEN:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(new ConditionalCommand(this.ready, this.accepting, () -> {
                            if (this.lift.getPosition() > 100 || this.arm.getPosition() > 0.93)
                                this.intakeToggle = true;

                            else this.intakeToggle = !this.intakeToggle;
                            return this.intakeToggle;
                        }));

                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement);
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement);
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
                        .whenPressed(new ConditionalCommand(this.close, this.intake, () -> this.intakeToggle));
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(this.pivotIncrement);
                this.gamepad2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(this.pivotDecrement);
                break;

            /* ------------------------------------- */
            /* --------------- RYAN ---------------- */
            /* ------------------------------------- */

            case RYAN:
                break;

            /* ------------------------------------- */
            /* ------------ RYAN & KELLY ----------- */
            /* ------------------------------------- */

            case RYAN_KELLY:
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
