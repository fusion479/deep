package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class TestWire extends SubsystemBase {
    public static double ZERO = 0;


    private final Servo zero;

    public TestWire(final HardwareMap hwMap) {

        this.zero = hwMap.get(Servo.class, "zero");
    }
    public void setPosition0(double position) {
        zero.setPosition(position);
    }


}