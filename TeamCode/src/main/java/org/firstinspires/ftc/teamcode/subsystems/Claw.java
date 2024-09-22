package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {
    public static double OPEN = 0.5;
    public static double CLOSE = 0.0;
    public static double UP = 1;
    public static double READY = 0.5;
    public static double DOWN = 0.0;

    private final MultipleTelemetry telemetry;

    private final Servo leftPivot, rightPivot;
    private final CRServo leftCRServo, rightCRServo;

    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.leftPivot = hwMap.get(Servo.class, "leftPivot");
        this.leftCRServo = hwMap.get(CRServo.class, "leftCRServo");

        this.rightPivot = hwMap.get(Servo.class, "rightPivot");
        this.rightCRServo = hwMap.get(CRServo.class, "rightCRServo");
    }

    public void setPosition(double position) {
        this.leftPivot.setPosition(position);
        this.rightPivot.setPosition(1 - position);
    }

    public void setClawPower(double power) {
        this.rightCRServo.setPower(power);
        this.leftCRServo.setPower(-power);
    }
}
