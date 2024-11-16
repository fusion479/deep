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
    public static double MAX_ACCEL = 0.15;
    public static double MAX_ANGULAR_ACCEL = 0.15;
    public static double MAX_VEL = 0.75;
    public static double MAX_ANGULAR_VEL = 0.75;

    private final MecanumDrive drive;
    private final MultipleTelemetry telemetry;

    private double xPower = 0.0;
    private double angPower = 0.0;
    private double yPower = 0.0;

    public Drivetrain(final HardwareMap hwMap, final MultipleTelemetry telemetry, Pose2d startPose) {
        this.telemetry = telemetry;

        this.drive = new MecanumDrive(hwMap, startPose);
    }

    private static double relativeMin(double min, double check) {
        double rel = Math.min(min, Math.abs(check));

        return check >= 0 ? rel : -rel;
    }

    public void startThread(final GamepadEx gamepad, CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    // TODO: Check if this works
                    synchronized (this.drive) {
                        this.yPower += Drivetrain.relativeMin(MAX_ACCEL, gamepad.getLeftY() - this.yPower);
                        this.xPower += Drivetrain.relativeMin(MAX_ACCEL, gamepad.getLeftX() - this.xPower);
                        this.angPower += Drivetrain.relativeMin(MAX_ANGULAR_ACCEL, gamepad.getRightX() - this.angPower);

                        this.drive.setDrivePowers(new PoseVelocity2d(
                                new Vector2d(
                                        this.yPower * MAX_VEL,
                                        this.xPower * MAX_VEL
                                ),
                                this.angPower * MAX_ANGULAR_VEL));
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    @Override
    public void periodic() {
        this.telemetry.addData("xPower: ", this.xPower);
        this.telemetry.addData("yPower: ", this.yPower);
    }

    public MecanumDrive getDrive() {
        return drive;
    }
}