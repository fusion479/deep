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
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    public static int ALLOWED_ERROR = 15;
    public static int TARGET = 0;
    private final MultipleTelemetry telemetry;

    public final AnalogInput encoder;
    private final CRServo extendo;
    private final PIDController controller;

    public Extendo(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.controller = new PIDController(Extendo.kP, Extendo.kI, Extendo.kD);

        this.extendo = hwMap.get(CRServo.class, "extendo");
        this.encoder = hwMap.get(AnalogInput.class, "encoder");
    }

    public double getPosition() {
        return (this.encoder.getVoltage() / 3.3 * 360);
    }

    @Override
    public void periodic() {
        double power = this.controller.calculate(this.getPosition());
        this.telemetry.addData("Power", power);
        this.extendo.setPower(power);
    }

    public void setConstants() {
        this.controller.setCoefficients(kP, kI, kD);
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

    public boolean isFinished() {
        return this.controller.isFinished();
    }
}
