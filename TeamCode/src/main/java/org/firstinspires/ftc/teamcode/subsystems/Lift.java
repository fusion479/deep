package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;

@Config
public class Lift extends SubsystemBase {
    public static double MIN_POWER = -0.25;
    public static double ALLOWED_ERROR = 15;

    public static double LOW_BASKET = 1500;
    public static double HIGH_BASKET = 2800;

    public static double LOW_RUNG = 1000;
    public static double HIGH_RUNG = 2100;

    public static double SPECIMEN = 0;

    public static double ACCEPTING = 50;
    public static double INCREMENT = 250;
    public static double SLAM = 750;

    public static double kP = 0.0015;
    public static double kI = 0.0006;
    public static double kD = 0.000015;
    public static double kG = 0.063;

    private final DcMotorEx rightSec;
    private final DcMotorEx leftSec;
    private final DcMotorEx rightPri;
    private final DcMotorEx leftPri;
    private final PIDController controller;

    private final MultipleTelemetry telemetry;

    public Lift(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.rightSec = hwMap.get(DcMotorEx.class, "rightLiftSec");
        this.leftSec = hwMap.get(DcMotorEx.class, "leftLiftSec");
        this.rightPri = hwMap.get(DcMotorEx.class, "rightLiftPri");
        this.leftPri = hwMap.get(DcMotorEx.class, "leftLiftPri");

        this.leftSec.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightSec.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightPri.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftPri.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftSec.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightSec.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightPri.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftPri.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftSec.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightSec.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightPri.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftPri.setDirection(DcMotorSimple.Direction.FORWARD);

        this.controller = new PIDController(kP, kI, kD);
        this.controller.setAllowedError(Lift.ALLOWED_ERROR);

        this.setTarget(0);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;

                    synchronized (this.rightPri) {
                        power = this.controller.calculate(this.rightPri.getCurrentPosition());
                    }

                    if (!this.controller.isFinished()) {
                        synchronized (this.rightPri) {
                            this.rightPri.setPower(Math.max(power, Lift.MIN_POWER));
                        }

                        synchronized (this.rightSec) {
                            this.rightSec.setPower(Math.max(power, Lift.MIN_POWER));
                        }

                        synchronized (this.leftPri) {
                            this.leftPri.setPower(Math.max(power, Lift.MIN_POWER));
                        }

                        synchronized (this.leftSec) {
                            this.leftSec.setPower(Math.max(power, Lift.MIN_POWER));
                        }
                    } else {
                        this.leftSec.setPower(Lift.kG);
                        this.rightSec.setPower(Lift.kG);
                        this.rightPri.setPower(Lift.kG);
                        this.rightSec.setPower(Lift.kG);
                    }

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    public void setPower(double power) {
        this.leftSec.setPower(power);
        this.rightSec.setPower(power);
        this.rightPri.setPower(power);
        this.leftPri.setPower(power);
    }

    public double getTarget() {
        return this.controller.getTarget();
    }

    public void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public double getPosition() {
        return this.rightPri.getCurrentPosition();
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public void setConstants() {
        this.controller.setCoefficients(kP, kI, kD);
    }
}
