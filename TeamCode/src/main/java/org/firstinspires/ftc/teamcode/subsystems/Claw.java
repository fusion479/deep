package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {
    public static double SCORE = 1;
    public static double READY = 0.5;
    public static double ACCEPTING = 0.0;

    public static double OPEN = 1.0;
    public static double CLOSE = 0.0;

    private final MultipleTelemetry telemetry;

    private final Servo leftPivot, rightPivot, claw;

    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.leftPivot = hwMap.get(Servo.class, "leftPivot");
        this.rightPivot = hwMap.get(Servo.class, "rightPivot");
        this.claw = hwMap.get(Servo.class, "claw");
    }

    public void setPivotPosition(double position) {
        this.leftPivot.setPosition(position);
        this.rightPivot.setPosition(1 - position);
    }

    public void setClawPosition(double position) {
        this.claw.setPosition(position);
    }

    public enum BlockCases {
        ACCEPT,
        REJECT,
        WAIT
    }
}
