package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {

    private final Servo servo;
    private final MultipleTelemetry telemetry;
    public Claw(final HardwareMap hwMap, final MultipleTelemetry telemetry){
        this.telemetry = telemetry;
        this.servo = hwMap.get(Servo.class, "claw");
    }

    public void setPivotPosition(double position){
        this.servo.setPosition(position);
    }
}
