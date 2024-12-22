package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm extends SubsystemBase {
    private MultipleTelemetry telemetry;

    private final Servo armLeft;
    private final Servo armRight;

    public static double READY = 0.0;
    public static double ACCEPTING = 0.0;
    public static double SCORE = 0.0;

    public Arm(HardwareMap hwMap, MultipleTelemetry telemetry) {
        this.armLeft = hwMap.get(Servo.class, "armLeft");
        this.armRight = hwMap.get(Servo.class, "armRight");
    }

    public void setPosition(double position) {
        this.armLeft.setPosition(position);
        this.armRight.setPosition(1 - position);
    }
}
