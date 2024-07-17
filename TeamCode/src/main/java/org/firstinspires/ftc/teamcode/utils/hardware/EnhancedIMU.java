package org.firstinspires.ftc.teamcode.utils.hardware;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class EnhancedIMU {
    private final IMU imu;
    private YawPitchRollAngles angles;

    public EnhancedIMU(IMU imu, ImuOrientationOnRobot orientation) {
        this.imu = imu;

        this.imu.initialize(new IMU.Parameters(orientation));
        this.imu.resetYaw(); // Don't know
    }

    public void asyncRead(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive()) {
                synchronized (this.imu) {
                    this.angles = this.imu.getRobotYawPitchRollAngles();
                }
            }
        }).start();
    }

    public YawPitchRollAngles getRobotYawPitchRollAngles() {
        return this.angles;
    }
}
