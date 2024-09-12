package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HorizontalSlides extends SubsystemBase {

    private final MultipleTelemetry telemetry;
    private final Servo leftServo, rightServo;
    public HorizontalSlides(HardwareMap hwMap, MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.leftServo = hwMap.get(Servo.class, "leftHorizontal");
        this.rightServo = hwMap.get(Servo.class, "rightHorizontal");
    }

    public void setPosition(double position){
        this.leftServo.setPosition(position);
        this.rightServo.setPosition(1 - position);
    }
}
