package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Wrist extends SubsystemBase {
    public static double SCORE = 0;
    public static double ACCEPTING = 0;
    public static double READY = 0;
    public static double SPECIMEN = 1;

    public static double INCREMENT = 0.25;

    private final Servo wrist;

    public Wrist(final HardwareMap hwMap) {
        this.wrist = hwMap.get(Servo.class, "wrist");

        this.setPosition(Wrist.ACCEPTING);
    }

    public void setPosition(double position) {
        this.wrist.setPosition(position);
    }

    public double getPosition() {
        return this.wrist.getPosition();
    }
}
