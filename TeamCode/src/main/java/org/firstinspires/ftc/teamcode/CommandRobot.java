package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
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
import org.firstinspires.ftc.teamcode.commands.arm.ArmDriveIn;
import org.firstinspires.ftc.teamcode.commands.arm.ArmIntake;
import org.firstinspires.ftc.teamcode.commands.arm.ArmReady;
import org.firstinspires.ftc.teamcode.commands.arm.ArmSpecimen;
import org.firstinspires.ftc.teamcode.commands.arm.ArmSweep;
import org.firstinspires.ftc.teamcode.commands.claw.ClawClose;
import org.firstinspires.ftc.teamcode.commands.claw.ClawOpen;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoBasket;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoDriveIn;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoReady;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSpecimen;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSweep;
import org.firstinspires.ftc.teamcode.commands.lift.LiftAccepting;
import org.firstinspires.ftc.teamcode.commands.lift.LiftDecrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftDriveIn;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftSlam;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotAccepting;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotBasket;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotDriveIn;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotIntake;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotReady;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotSpecimen;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotSweep;
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
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class CommandRobot {
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

    public static int ACCEPTING_WAIT = 700;
    public static int SLAM_WAIT = 600;
    public static int SPECIMEN_WAIT = 300;

    public static double ARM_THRESH = 0.61;

    public static double SLOW_ANG_VEL = 0.1;
    public static double SLOW_ANG_ACCEL = 0.02;

    private GamepadTrigger lt, rt;

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

        this.lt = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, p -> this.extendo.setPower(p / 2), this.gamepad1);
        this.rt = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, p -> this.extendo.setPower(-p / 2), this.gamepad1);

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
    }

    public void startThreads(OpModeCore opMode) {
        this.drivetrain.startThread(this.gamepad1, opMode);
        this.extendo.startThread(opMode);
        this.lift.startThread(opMode);
    }

    public void startAutoThreads(OpModeCore opMode) {
        this.extendo.startThread(opMode);
        this.lift.startThread(opMode);
    }

    public void configureControls() {
        switch (this.mode) {
            case KELLY:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(new ConditionalCommand(this.ready(), this.accepting(), () -> {
                            if (this.lift.getPosition() > 100 || this.arm.getPosition() < ARM_THRESH)
                                this.intakeToggle = true;

                            else this.intakeToggle = !this.intakeToggle;
                            return this.intakeToggle;
                        }));

                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(this.wristLeft());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(this.wristRight());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.driveIn());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.specimen());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(new ConditionalCommand(this.close(), this.intake(), () -> this.intakeToggle));
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open());
                break;

            case DEV:
                this.gamepad1.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(new ConditionalCommand(this.ready(), this.accepting(), () -> {
                            if (this.lift.getPosition() > 100 || this.arm.getPosition() < ARM_THRESH)
                                this.intakeToggle = true;

                            else this.intakeToggle = !this.intakeToggle;
                            return this.intakeToggle;
                        }));

                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(this.liftDecrement());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(this.liftIncrement());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(this.wristRight());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(this.sweep());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(this.highRung());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(this.specimen());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(this.slam());
                this.gamepad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenPressed(new ConditionalCommand(this.close(), this.intake(), () -> this.intakeToggle));
                this.gamepad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(this.open());
                break;
        }
    }

    public enum TeleOpMode {
        KELLY,
        DEV
    }

    public Follower getFollower() {
        return this.drivetrain.getFollower();
    }

    public Command ready() {
        return new SequentialCommandGroup(
                new ClawClose(this.claw),
                new WristReady(this.wrist),
                new PivotReady(this.pivot),
                new ExtendoReady(this.extendo),
                new ArmReady(this.arm),
                new LiftAccepting(this.lift)
        );
    }

    public Command specimen() {
        return new SequentialCommandGroup(
                new LiftAccepting(this.lift),
                new WristBasket(this.wrist),
                new PivotBasket(this.pivot),
                new ArmBasket(this.arm),
                new ExtendoBasket(this.extendo),
                new WaitCommand(SPECIMEN_WAIT),
                new ClawOpen(this.claw)
        );
    }

    public Command accepting() {
        return new SequentialCommandGroup(
                new LiftAccepting(this.lift),
                new ExtendoAccepting(this.extendo),
                new WaitCommand(ACCEPTING_WAIT),
                new ClawOpen(this.claw),
                new ArmAccepting(this.arm),
                new PivotAccepting(this.pivot),
                new WristAccepting(this.wrist)
        );
    }

    public Command highRung() {
        return new SequentialCommandGroup(
                new ClawClose(this.claw),
                new LiftHighRung(this.lift),
                new ExtendoSpecimen(this.extendo),
                new WaitCommand(350),
                new PivotSpecimen(this.pivot),
                new WristSpecimen(this.wrist),
                new ArmSpecimen(this.arm)
        );
    }

    public Command intake() {
        return new SequentialCommandGroup(
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
    }

    public Command slam() {
        return new SequentialCommandGroup(
                new LiftSlam(this.lift),
                new WaitCommand(SLAM_WAIT),
                new ClawOpen(this.claw),
                new WaitCommand(100),
                new LiftIncrement(this.lift),
                new LiftIncrement(this.lift),
                new LiftIncrement(this.lift)
        );
    }

    public Command driveIn() {
        return new SequentialCommandGroup(
                new ClawClose(this.claw),
                new LiftDriveIn(this.lift),
                new ExtendoDriveIn(this.extendo),
                new PivotDriveIn(this.pivot),
                new WristSpecimen(this.wrist),
                new ArmDriveIn(this.arm)
        );
    }

    public Command sweep() {
        return new SequentialCommandGroup(
                new LiftAccepting(this.lift),
                new ClawClose(this.claw),
                new PivotSweep(this.pivot),
                new ArmSweep(this.arm),
                new ExtendoSweep(this.extendo)
        );
    }

    public Command close() {
        return new ClawClose(this.claw);
    }

    public Command open() {
        return new ClawOpen(this.claw);
    }

    public Command liftIncrement() {
        return new LiftIncrement(this.lift);
    }

    public Command liftDecrement() {
        return new LiftDecrement(this.lift);
    }

    public Command wristRight() {
        return new WristRight(this.wrist);
    }

    public Command wristLeft() {
        return new WristLeft(this.wrist);
    }

    public void update() {
        this.rt.update();
        this.lt.update();

        if (this.extendo.getPosition() > 100) {
            Drivetrain.MAX_ANGULAR_VEL = SLOW_ANG_VEL;
            Drivetrain.MAX_ANGULAR_ACCEL = SLOW_ANG_ACCEL;
        } else {
            Drivetrain.MAX_ANGULAR_ACCEL = 0.2;
            Drivetrain.MAX_ANGULAR_VEL = 0.6;
        }
    }

    public void logDev() {
        TelemetryCore.getInstance().addData("Target", this.lift.getTarget());
        TelemetryCore.getInstance().addData("Position", this.lift.getPosition());
        TelemetryCore.getInstance().addData("Error", this.lift.getError());

        this.lift.setConstants();
    }
}
