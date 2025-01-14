package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw extends SubsystemBase {
    public static double OPEN = 1;
    public static double CLOSE = 0; //close is correct

    public static double SCORE = 1;
    public static double ACCEPTING = 0;

    private final MultipleTelemetry telemetry;

    private final Servo claw;


    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

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
