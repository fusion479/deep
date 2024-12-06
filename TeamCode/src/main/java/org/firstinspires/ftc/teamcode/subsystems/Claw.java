package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw extends SubsystemBase {
    public static double SCORE = 0.5;
    public static double ACCEPTING = 1;

    public static double OPEN = 0.3;
    public static double CLOSE = 0.5;

    private final MultipleTelemetry telemetry;

    private final Servo leftPivot, rightPivot, claw;

    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.leftPivot = hwMap.get(Servo.class, "leftPivot");
        this.rightPivot = hwMap.get(Servo.class, "rightPivot");
        this.claw = hwMap.get(Servo.class, "claw");

        this.claw.setDirection(Servo.Direction.REVERSE);
    }

    public void setPivotPosition(double position) {
        this.leftPivot.setPosition(position);
        this.rightPivot.setPosition(1 - position);
    }

    public void setClawPosition(double position) {
        this.claw.setPosition(position);
    }
}
