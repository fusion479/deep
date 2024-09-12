package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawPivot extends SubsystemBase {

    private final MultipleTelemetry telemetry;
    private final Servo leftServo, rightServo;
    public ClawPivot(final HardwareMap hwMap, final MultipleTelemetry telemetry){
        this.telemetry = telemetry;
        this.leftServo = hwMap.get(Servo.class, "leftPivot");
        this.rightServo = hwMap.get(Servo.class, "rightPivot");
    }

    public void setPivotPosition(double position){
        this.leftServo.setPosition(position);
        this.rightServo.setPosition(1 - position);
    }
}
