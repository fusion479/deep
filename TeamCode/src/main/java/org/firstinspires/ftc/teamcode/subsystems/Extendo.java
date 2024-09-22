package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Extendo extends SubsystemBase {
    public static double ACCEPTING = 0;
    public static double SCORE = 1;
    public static double READY = 0;
    public static double INCREMENT = 0.1;

    private final MultipleTelemetry telemetry;
    private final Servo leftServo, rightServo;

    public Extendo(HardwareMap hwMap, MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.leftServo = hwMap.get(Servo.class, "extendoLeft");
        this.rightServo = hwMap.get(Servo.class, "extendoRight");
    }

    public double getPosition() {
        return this.leftServo.getPosition();
    }

    public void setPosition(double position) {
        this.leftServo.setPosition(position);
        this.rightServo.setPosition(1 - position);
    }
}
