package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.utils.PIDController;

@Config
public class Lift extends SubsystemBase {
    public static final double MIN_POWER = -0.3;
    public static final double LOW_VOLTAGE = 12.0;

    public static double ACCEPTING = 200;
    public static double LOW_BASKET = 500;
    public static double HIGH_BASKET = 615; // higher
    public static double LOW_RUNG = 300;
    public static double HIGH_RUNG = 615;
    public static double INCREMENT = 50;

    public static double kP = 0.01;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final DcMotorEx rightMotor;
    private final DcMotorEx leftMotor;
    private final VoltageSensor voltage;
    private final PIDController controller;

    private final MultipleTelemetry telemetry;

    public Lift(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.voltage = hwMap.get(VoltageSensor.class, "Control Hub");

        this.rightMotor = hwMap.get(DcMotorEx.class, "liftRight");
        this.leftMotor = hwMap.get(DcMotorEx.class, "liftLeft");

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        this.controller = new PIDController(kP, kI, kD, kG);
        this.controller.setAllowedError(10);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;

                    synchronized (this.rightMotor) {
                        power = this.controller.calculate(this.rightMotor.getCurrentPosition());

                        this.rightMotor.setPower(Math.max(power, Lift.MIN_POWER));
                    }

                    synchronized (this.leftMotor) {
                        this.leftMotor.setPower(Math.max(power, Lift.MIN_POWER));
                    }

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    public void setPower(double power) {
        this.leftMotor.setPower(power);
        this.rightMotor.setPower(power);
    }

    public double getTarget() {
        return this.controller.getTarget();
    }

    public void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public double getPosition() {
        return this.rightMotor.getCurrentPosition();
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public void setConstants() {
        this.controller.setCoefficients(kP, kI, kD, kG);
    }

    public void reset() {
        setTarget(-999999999);
        while (this.voltage.getVoltage() > Lift.LOW_VOLTAGE) {
        }
        setTarget(0);
    }
}
