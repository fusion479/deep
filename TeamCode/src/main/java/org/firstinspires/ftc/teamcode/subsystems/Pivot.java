package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Pivot extends SubsystemBase {
    public static double ACCEPTING = 0;
    public static double READY = 0;
    public static double SCORE = 0.75;
    public static double SPECIMEN = 0.75;
    public static double INTAKE = 0.1;
    public double CURRENT_POSITION = 0;
    public static double INCREMENT = 0.1;

    private final Servo pivot;

    public Pivot(final HardwareMap hwMap) {
        this.pivot = hwMap.get(Servo.class, "pivot");

        this.setPosition(Pivot.READY);
    }

    public double getPosition() {
        return CURRENT_POSITION;
    }

    public void setPosition(double position) {
        this.CURRENT_POSITION = position;
        this.pivot.setPosition(position);
    }
}
