package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.hardware.EnhancedColorSensor;

@Config
public class Claw extends SubsystemBase {
    public static double SCORE = 1;
    public static double ACCEPTING = 0.55;

    //TODO: Get positions for claw rotate
    public static double UP = 1;
    public static double DOWN = 0.49;
    public static double READY = 0;
    public static int COLOR_THRESHOLD = 200;
    public static int DISTANCE_THRESHOLD = 75;

    private final MultipleTelemetry telemetry;

    private final Servo clawRotate;
    private final EnhancedColorSensor sensor;
    private final CRServo leftClaw, rightClaw;


    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.sensor = new EnhancedColorSensor(hwMap.get(RevColorSensorV3.class, "sensor"));
        this.leftClaw = hwMap.get(CRServo.class, "leftClaw");
        this.rightClaw = hwMap.get(CRServo.class, "rightClaw");
        this.clawRotate = hwMap.get(Servo.class, "clawRotate");

        this.setPosition(Claw.READY);
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

    public void startThread(OpModeCore opMode) {
        this.sensor.startThread(opMode);
    }

    public void logSensorData() {
        this.telemetry.addData("RED", this.sensor.getRed());
        this.telemetry.addData("BLUE", this.sensor.getBlue());
        this.telemetry.addData("DISTANCE", this.sensor.getDistance());
    }

    public void setPosition(double position) {
        this.clawRotate.setPosition(position);
    }

    public void setPower(double power) {
        this.leftClaw.setPower(power);
        this.rightClaw.setPower(-power);
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
