package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw extends SubsystemBase {
    public static double OPEN = 0.5;
    public static double CLOSE = 0.7;

    private final Servo claw;

    public Claw(final HardwareMap hwMap) {
        this.claw = hwMap.get(Servo.class, "claw");

        this.setPosition(Claw.CLOSE);
    }

    public double getPosition() {
        return this.claw.getPosition();
    }

    public void setPosition(double position) {
        this.claw.setPosition(position);
    }
}
