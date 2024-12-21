package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw extends SubsystemBase {
    public static double SCORE = 1;
    public static double ACCEPTING = 0.55;

    //TODO: Get positions for claw rotate
    public static double UP = 1;
    public static double DOWN = 0.49;
    public static double READY = 0;

    private final MultipleTelemetry telemetry;

    private final Servo clawRotate;
    private final CRServo leftClaw, rightClaw;


    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.leftClaw = hwMap.get(CRServo.class, "leftClaw");
        this.rightClaw = hwMap.get(CRServo.class, "rightClaw");
        this.clawRotate = hwMap.get(Servo.class, "clawRotate");

        this.setPosition(Claw.READY);
    }

    public void setPosition(double position) {
        this.clawRotate.setPosition(position);
    }

    public void setClawPower(double power) {
        this.leftClaw.setPower(power);
        this.rightClaw.setPower(-power);
    }
}
