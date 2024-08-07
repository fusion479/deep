package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
public class Drivetrain extends SubsystemBase {
    private final MecanumDrive drive;
    private final MultipleTelemetry telemetry;

    public Drivetrain(final HardwareMap hwMap, final MultipleTelemetry telemetry, Pose2d startPose) {
        this.telemetry = telemetry;

        this.drive = new MecanumDrive(hwMap, startPose);
    }

    public void startThread(final GamepadEx gamepad, CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    synchronized (this.drive) {
                        this.drive.setDrivePowers(new PoseVelocity2d(
                                new Vector2d(
                                        -gamepad.getLeftY(),
                                        gamepad.getLeftX()
                                ),
                                -gamepad.getRightX()));
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    public MecanumDrive getDrive() {
        return drive;
    }
}