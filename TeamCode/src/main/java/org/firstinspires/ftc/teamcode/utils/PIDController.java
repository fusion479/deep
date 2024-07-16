package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {

    private double kP, kI, kD;
    private final ElapsedTime timer = new ElapsedTime();
    private double target = 0;
    private double integralSum = 0, lastError = 0;
    private double allowedError = 0;

    public PIDController(double kP) {
        this.kP = kP;
        this.kI = 0;
        this.kD = 0;
    }

    public PIDController(double kP, double kI) {
        this.kP = kP;
        this.kI = kI;
        this.kD = 0;
    }

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double getTarget() {
        return this.target;
    }

    public void setTarget(double target) {
        this.target = target;
        this.lastError = 0;
    }

    public void setAllowedError(double error) {
        this.allowedError = error;
    }

    public void setCoefficients(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double reference) {
        double error = this.target - reference;
        double derivative = (error - this.lastError) / this.timer.seconds();
        this.integralSum = this.integralSum + (error * this.timer.seconds());

        this.lastError = error;
        this.timer.reset();

        return (this.kP * error) + (this.kI * this.integralSum) + (this.kD * derivative);
    }

    public double getLastError() {
        return this.lastError;
    }

    public boolean isFinished() {
        return Math.abs(this.lastError) >= this.allowedError;
    }
}
