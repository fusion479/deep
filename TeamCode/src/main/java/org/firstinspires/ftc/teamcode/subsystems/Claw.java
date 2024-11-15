package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.hardware.EnhancedColorSensor;

public class Claw extends SubsystemBase {
    public static double UP = 1;
    public static double READY = 0.5;
    public static double DOWN = 0.0;

    // TODO: Calculate appropriate values for these thresholds
    public static int COLOR_THRESHOLD = 100;
    public static int DISTANCE_THRESHOLD = 100;

    private final MultipleTelemetry telemetry;

    private final Servo leftPivot, rightPivot;
    private final CRServo leftCRServo, rightCRServo;
    private final EnhancedColorSensor sensor;

    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.leftPivot = hwMap.get(Servo.class, "leftPivot");
        this.leftCRServo = hwMap.get(CRServo.class, "leftCRServo");

        this.rightPivot = hwMap.get(Servo.class, "rightPivot");
        this.rightCRServo = hwMap.get(CRServo.class, "rightCRServo");

        this.sensor = new EnhancedColorSensor(hwMap.get(ColorRangeSensor.class, "sensor"));
    }

    public void startThreads(OpModeCore opMode) {
        this.sensor.startThread(opMode);
    }

    public BlockCases hasValidBlock(Claw.Color color) {
        if (this.sensor.getDistance() < DISTANCE_THRESHOLD) {
            if (this.sensor.getBlue() < COLOR_THRESHOLD && this.sensor.getRed() < COLOR_THRESHOLD)
                return BlockCases.ACCEPT;

            else if (color == Color.BLUE)
                return this.sensor.getBlue() > COLOR_THRESHOLD ? BlockCases.ACCEPT : BlockCases.REJECT;

            else
                return this.sensor.getRed() > COLOR_THRESHOLD ? BlockCases.ACCEPT : BlockCases.REJECT;
        }

        return BlockCases.WAIT;
    }

    public void setPosition(double position) {
        this.leftPivot.setPosition(position);
        this.rightPivot.setPosition(1 - position);
    }

    public void setClawPower(double power) {
        this.rightCRServo.setPower(power);
        this.leftCRServo.setPower(-power);
    }

    public enum Color {
        RED,
        BLUE
    }

    public enum BlockCases {
        ACCEPT,
        REJECT,
        WAIT
    }
}
