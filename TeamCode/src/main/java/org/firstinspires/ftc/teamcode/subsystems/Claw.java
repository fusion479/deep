package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {
    public static double OPEN = 0.5;
    public static double CLOSE = 0.0;
    public static double UP = 0.5;
    public static double DOWN = 0.0;
    private final Servo servo, leftPivot, rightPivot;
    private final DcMotorEx motor;
    private final MultipleTelemetry telemetry;

    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.servo = hwMap.get(Servo.class, "clawServo");
        this.motor = hwMap.get(DcMotorEx.class, "clawMotor");
        this.leftPivot = hwMap.get(Servo.class, "clawLeftPivot");
        this.rightPivot = hwMap.get(Servo.class, "clawRightPivot");
    }

    public void setPosition(double position) {
        this.servo.setPosition(position);
    }

    public void setPivotPosition(double position) {
        // TODO: Make if one needs to be negated
        this.leftPivot.setPosition(position);
        this.rightPivot.setPosition(position);
    }

    public void setClawPower(double power) {
        this.motor.setPower(power);
    }
}
