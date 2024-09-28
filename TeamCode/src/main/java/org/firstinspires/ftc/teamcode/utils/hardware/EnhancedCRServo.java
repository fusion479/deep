package org.firstinspires.ftc.teamcode.utils.hardware;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.utils.PIDController;

public class EnhancedCRServo {
    private final CRServo servo;
    private final PIDController controller;

    public EnhancedCRServo(CRServo servo) {
        this.servo = servo;

        this.controller = new PIDController(0, 0, 0, 0);
    }

    public void setPosition(double position) {
        this.servo.setPower(this.controller.calculate(position));
    }

    public void setPower(double power) {
        this.servo.setPower(power);
    }

    public PIDController getController() {
        return this.controller;
    }
}