package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

public class CommandRobot {
    private final Drivetrain drivetrain;
    private final Lift lift;
    private final MultipleTelemetry telemetry;
    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    // TELEOP
    public CommandRobot(HardwareMap hwMap, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        this.telemetry = telemetry;
        this.drivetrain = new Drivetrain(hwMap, telemetry, new Pose2d(0, 0, 0));
        this.lift = new Lift(hwMap, telemetry);
        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);

        this.configureControls();
    }

    // AUTON
    public CommandRobot(HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.drivetrain = new Drivetrain(hwMap, telemetry, startPose);
        this.lift = new Lift(hwMap, telemetry);
    }

    public MecanumDrive getDrive() {
        return this.drivetrain.getDrive();
    }

    public void configureControls() {
        // controls
    }

    public void startTeleopThreads(CommandOpMode opMode) {
        this.drivetrain.startThread(this.gamepad1, opMode);
        this.lift.startThread(opMode);
        // update triggers
    }

    public void startAutonThreads(CommandOpMode opMode) {
        this.lift.startThread(opMode);
    }
}
