package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Arm extends SubsystemBase {
    public static double ACCEPTING = 0.25;
    public static double SCORE = 1;
    public static double READY = 1;
    public static double INTAKE = 0.15;
    public static double SPECIMEN = 0.35;

    private final MultipleTelemetry telemetry;
    private final Servo left, right;

    public Arm(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.left = hwMap.get(Servo.class, "leftArm");
        this.right = hwMap.get(Servo.class, "rightArm");

        this.setPosition(Arm.READY);
    }

    public void setPosition(double position) {
        this.left.setPosition(position);
        this.right.setPosition(1 - position);
    }

    public double getPosition() {
        return this.left.getPosition();
    }
}
