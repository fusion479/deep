package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

@Config
public class Drivetrain extends SubsystemBase {
    public static double MAX_ACCEL = 0.3;
    public static double MAX_ANGULAR_ACCEL = 0.1;

    public static double MAX_DEACCEL = 0.5;
    public static double MAX_ANGULAR_DEACCEL = 0.5;

    public static double MAX_VEL = 0.75;
    public static double MAX_ANGULAR_VEL = 0.6;

    private final Follower follower;

    private final MultipleTelemetry telemetry;

    private double xPower = 0.0;
    private double angPower = 0.0;
    private double yPower = 0.0;

    public Drivetrain(final HardwareMap hwMap, final MultipleTelemetry telemetry, Pose startPose) {
        this.telemetry = telemetry;

        this.follower = new Follower(hwMap);
        this.follower.setStartingPose(startPose);
    }

    private static double calculateAccel(double accel, double deaccel, double prevPower, double check) {
        double rel;

        if (Math.abs(prevPower) > Math.abs(check))
            rel = Math.min(deaccel, Math.abs(check - prevPower));
        else rel = Math.min(accel, Math.abs(check - prevPower));

        return check - prevPower >= 0 ? rel : -rel;
    }

    public void startThread(final GamepadEx gamepad, CommandOpMode opMode) {
        new Thread(() -> {
            this.follower.startTeleopDrive();
            this.follower.brakeMode();

            while (opMode.opModeIsActive())
                try {
                    synchronized (this.follower) {
                        this.yPower += Drivetrain.calculateAccel(MAX_ACCEL, MAX_DEACCEL, this.yPower, gamepad.getLeftY());
                        this.xPower += Drivetrain.calculateAccel(MAX_ACCEL, MAX_DEACCEL, this.xPower, gamepad.getLeftX());
                        this.angPower += Drivetrain.calculateAccel(MAX_ANGULAR_ACCEL, MAX_ANGULAR_DEACCEL, this.angPower, gamepad.getRightX());

                        this.follower.setTeleOpMovementVectors(-yPower, -xPower, -angPower, true);
                        this.follower.update();
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    public Follower getFollower() {
        return this.follower;
    }
}