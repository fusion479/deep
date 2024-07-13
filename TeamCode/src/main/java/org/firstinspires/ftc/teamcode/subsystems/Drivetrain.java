package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.SubsystemCore;

@Config
public class Drivetrain extends SubsystemCore {
    public final MecanumDrive drive;

    public Drivetrain(final HardwareMap hwMap, final MultipleTelemetry telemetry, Pose2d startPose) {
        super(telemetry);

        this.drive = new MecanumDrive(hwMap, startPose);
    }

    public void manualDrive(final GamepadEx gamepad) {
        this.drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad.getLeftY(),
                        gamepad.getLeftX()
                ),
                -gamepad.getRightX()
        ));
    }

    public MecanumDrive getDrive() {
        return drive;
    }
}