package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Sweeper extends SubsystemBase {
    public static double DOWN = 0.05;
    public static double UP = 0.9;

    private final Servo sweeper;

    public Sweeper(final HardwareMap hwMap) {
        this.sweeper = hwMap.get(Servo.class, "sweeper");

        this.setPosition(Sweeper.UP);
    }

    public double getPosition() {
        return this.sweeper.getPosition();
    }

    public void setPosition(double position) {
        this.sweeper.setPosition(position);
    }
}
