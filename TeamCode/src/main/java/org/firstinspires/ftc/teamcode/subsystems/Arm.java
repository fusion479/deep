package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Arm extends SubsystemBase {
    public static double ACCEPTING = 0.9;
    public static double SCORE = 0.55;
    public static double READY = 0.57;
    public static double INTAKE = 0.97;
    public static double SPECIMEN = 0.78;
    public static double DRIVE_IN = 0.85;
    public static double SWEEP = 0;

    private final Servo arm;

    public Arm(final HardwareMap hwMap) {
        this.arm = hwMap.get(Servo.class, "leftArm");

        this.setPosition(Arm.READY);
    }

    public void setPosition(double position) {
        this.arm.setPosition(position);
    }

    public double getPosition() {
        return this.arm.getPosition();
    }
}
