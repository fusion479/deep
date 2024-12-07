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
    public static double MIN_POWER = -0.25;
    public static double LOW_VOLTAGE = 12.0;
    public static double ALLOWED_ERROR = 15;

    public static double ACCEPTING = 100;
    public static double LOW_BASKET = 1500;
    public static double HIGH_BASKET = 2600; // higher

    public static double LOW_RUNG = 1000;
    public static double HIGH_RUNG = 2000;
    public static double INCREMENT = 100;

    public static double kP = 0.002;
    public static double kI = 0.0006;
    public static double kD = 0.000015;
    public static double kG = 0.06;

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

        this.controller = new PIDController(kP, kI, kD);
        this.controller.setAllowedError(Lift.ALLOWED_ERROR);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;

                    synchronized (this.rightMotor) {
                        power = this.controller.calculate(this.rightMotor.getCurrentPosition());
                    }

                    if (!this.controller.isFinished()) {
                        synchronized (this.rightMotor) {
                            this.rightMotor.setPower(Math.max(power, Lift.MIN_POWER));
                        }

                        synchronized (this.leftMotor) {
                            this.leftMotor.setPower(Math.max(power, Lift.MIN_POWER));
                        }
                    } else {
                        this.leftMotor.setPower(Lift.kG);
                        this.rightMotor.setPower(Lift.kG);
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

    public double getVoltage() {
        return this.voltage.getVoltage();
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
