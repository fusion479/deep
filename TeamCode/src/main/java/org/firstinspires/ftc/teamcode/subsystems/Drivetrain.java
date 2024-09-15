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
    public static double MAX_ACCEL = 0.5;
    public static double MAX_ANGULAR_ACCEL = 0.5;
    public static double MAX_VEL = 1;
    public static double MAX_ANGULAR_VEL = 1;

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
                    // TODO: Check if this works
                    synchronized (this.drive) {
                        double yPower = Math.max(-MAX_ACCEL, Math.min(MAX_ACCEL, -gamepad.getLeftY()));
                        double xPower = Math.max(-MAX_ACCEL, Math.min(MAX_ACCEL, gamepad.getLeftX()));
                        double angPower = Math.max(-MAX_ANGULAR_ACCEL, Math.min(MAX_ANGULAR_ACCEL, -gamepad.getRightX()));

                        this.drive.setDrivePowers(new PoseVelocity2d(
                                new Vector2d(
                                        angPower * MAX_ANGULAR_VEL,
                                        xPower * MAX_VEL
                                ),
                                yPower * MAX_VEL));
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