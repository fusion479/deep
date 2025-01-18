package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;

@Config
public class Extendo extends SubsystemBase {
    public static double kP = 0.004;
    public static double kI = 0;
    public static double kD = 0;

    public static double OFFSET = 0;
    public static int ALLOWED_ERROR = 15;
    public static int SCORE = 10;
    public static int READY = 100;
    public static int ACCEPTING = 600;

    private final MultipleTelemetry telemetry;
    private double power;

    public final AnalogInput encoder;
    private final CRServo extendo;
    private final PIDController controller;

    private double rotations = 0;
    private double prevPos = 0;

    public Extendo(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.controller = new PIDController(Extendo.kP, Extendo.kI, Extendo.kD);

        this.extendo = hwMap.get(CRServo.class, "extendo");
        this.encoder = hwMap.get(AnalogInput.class, "encoder");

        this.controller.setAllowedError(Extendo.ALLOWED_ERROR);
    }

    public synchronized double getPosition() {
        return Extendo.OFFSET - (this.encoder.getVoltage() / 3.3 * 360);
    }

    @Override
    public void periodic() {
        double currPos = this.getPosition();

        if (prevPos - currPos > 180) rotations++;
        else if (180 < currPos - prevPos) rotations--;

        this.power = this.controller.calculate(currPos + 360 * rotations);
        if (!this.controller.isFinished()) this.extendo.setPower(this.power);

        prevPos = currPos;
    }

    public void setConstants() {
        this.controller.setCoefficients(Extendo.kP, Extendo.kI, Extendo.kD);
    }

    public double getTarget() {
        return this.controller.getTarget();
    }

    public void setPosition(double position) {
        this.controller.setTarget(position);
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public void setPower(double power) {
        this.extendo.setPower(power);
        this.controller.setTarget(this.getPosition());
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }
}
