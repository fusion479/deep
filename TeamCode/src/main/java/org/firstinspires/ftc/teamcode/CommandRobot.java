package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.drivetrain.ManualDrive;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.commands.RobotCore;

public class CommandRobot extends RobotCore {
    private final Drivetrain drivetrain;
    private GamepadEx gamepad1;
    private GamepadEx gamepad2;

    public CommandRobot(Type type, HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        super(type, telemetry);

        this.drivetrain = new Drivetrain(hwMap, telemetry, startPose);
        this.gamepad1 = new GamepadEx(gamepad1);
        this.gamepad2 = new GamepadEx(gamepad2);
    }

    public CommandRobot(Type type, HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry) {
        super(type, telemetry);

        this.drivetrain = new Drivetrain(hwMap, telemetry, startPose);
    }

    public MecanumDrive getDrive() {
        return this.drivetrain.getDrive();
    }

    @Override
    public void configureCommands() {
        this.drivetrain.setDefaultCommand(new ManualDrive(this.drivetrain, this.gamepad1, super.getTelemetry()));
    }

    @Override
    public void updateTriggers() {
    }
}
