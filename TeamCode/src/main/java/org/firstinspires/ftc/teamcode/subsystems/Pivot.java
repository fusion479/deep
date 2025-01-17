package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Pivot extends SubsystemBase {
    public static double SCORE = 0.9;
    public static double ACCEPTING = 0.15;
    public static double READY = 0.25;

    private final MultipleTelemetry telemetry;
    private final Servo pivot;

    public Pivot(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.pivot = hwMap.get(Servo.class, "rotator");

        this.setPosition(Pivot.READY);
    }

    public void setPosition(double position) {
        this.pivot.setPosition(position);
    }
}
