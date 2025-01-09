package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class TestWire extends SubsystemBase {
    public static double ZERO = 0;
    public static double ONE = 0;
    public static double TWO = 0;
    public static double THREE = 0;
    public static double FOUR = 0;
    public static double FIVE = 0;


    private final MultipleTelemetry telemetry;
    private final Servo zero, one, two, three, four, five;

    public TestWire(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.zero = hwMap.get(Servo.class, "zero");
        this.one = hwMap.get(Servo.class, "one");
        this.two = hwMap.get(Servo.class, "two");
        this.three = hwMap.get(Servo.class, "three");
        this.four = hwMap.get(Servo.class, "four");
        this.five=hwMap.get(Servo.class, "five");

    }
    public void setPosition0(double position) {
        zero.setPosition(position);
    }
    public void setPosition1(double position) {
        one.setPosition(position);
    }
    public void setPosition2(double position) {
        two.setPosition(position);
    }
    public void setPosition3(double position) {
        three.setPosition(position);
    }
    public void setPosition4(double position) {
        four.setPosition(position);
    }
    public void setPosition5(double position) {
        five.setPosition(position);
    }



}
