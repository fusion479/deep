package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Pivot extends SubsystemBase {
    public static double ACCEPTING = 0;
    public static double READY = 0;
    public static double SCORE = 0.37;
    public static double SPECIMEN = 0.03;
    public static double INTAKE = 0.1;

    private final Servo pivot;

    public Pivot(final HardwareMap hwMap) {
        this.pivot = hwMap.get(Servo.class, "pivot");

        this.setPosition(Pivot.READY);
    }

    public void setPosition(double position) {
        this.pivot.setPosition(position);
    }
}
