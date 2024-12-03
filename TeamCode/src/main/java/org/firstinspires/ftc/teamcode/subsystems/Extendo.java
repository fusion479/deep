package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Extendo extends SubsystemBase {
    public static double ACCEPTING = 1;
    public static double SCORE = 0;
    public static double READY = 0;
    public static double INCREMENT = 0.1;

    private final MultipleTelemetry telemetry;
    private final Servo extendo;

    public Extendo(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.extendo = hwMap.get(Servo.class, "extendo");
    }

    public double getPosition() {
        return this.extendo.getPosition();
    }

    public void setPosition(double position) {
        this.extendo.setPosition(position);
    }
}
