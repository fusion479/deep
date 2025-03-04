package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utils.PIDController;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;

import java.io.PrintWriter;
import java.io.StringWriter;

@Config
public class Lift extends SubsystemBase {
    public static double MAX_ACCEL = 0.2;
    public static double MAX_VEL = 0.7;
    public static double MIN_VEL = 0.2;
    public static double MAX_DEACCEL = 0.1;

    public static double MIN_POWER = -0.2;

    public static double LOW_BASKET = 400;
    public static double LOW_RUNG = 400;
    public static double HIGH_RUNG = 630;
    public static double DRIVE_IN = 530;
    public static double CLIMB = -Integer.MAX_VALUE;
    public static double COMPENSATE = 12.0;

    public static double ACCEPTING = 10;
    public static double INCREMENT = 50;

    public static double SLAM = 260;

    public static double kP = 0.0055;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final DcMotorEx rightSec;
    private final DcMotorEx leftSec;
    private final DcMotorEx rightPri;
    private final DcMotorEx leftPri;
    private final VoltageSensor voltageSensor;
    private final PIDController controller;

    private double power = 0.0;

    public Lift(final HardwareMap hwMap) {
        this.rightSec = hwMap.get(DcMotorEx.class, "rightLiftSec");
        this.leftSec = hwMap.get(DcMotorEx.class, "leftLiftSec");
        this.rightPri = hwMap.get(DcMotorEx.class, "rightLiftPri");
        this.leftPri = hwMap.get(DcMotorEx.class, "leftLiftPri");

        this.leftPri.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightSec.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightPri.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftSec.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        this.controller = new PIDController(Lift.kP, Lift.kI, Lift.kD, Lift.kG);
        this.controller.setAllowedError(15);

        this.voltageSensor = hwMap.get(VoltageSensor.class, "Control Hub");

        this.setTarget(0);
    }

    private static double calculateAccel(double accel, double deaccel, double prevPower, double check) {
        double rel;

        if (Math.abs(prevPower) > Math.abs(check))
            rel = Math.min(deaccel, Math.abs(check - prevPower));
        else rel = Math.min(accel, Math.abs(check - prevPower));

        return check - prevPower >= 0 ? Range.clip(rel, MIN_VEL, MAX_VEL) : -Range.clip(rel, MIN_VEL, MAX_VEL);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    synchronized (this.rightPri) {
                        this.power += Lift.calculateAccel(MAX_ACCEL, MAX_DEACCEL, this.power, this.controller.calculate(this.getPosition() * (12.0 / voltageSensor.getVoltage())));
                        this.rightPri.setPower(power);
                    }

                    synchronized (this.rightSec) {
                        this.rightSec.setPower(power);
                    }

                    synchronized (this.leftPri) {
                        this.leftPri.setPower(power);
                    }

                    synchronized (this.leftSec) {
                        this.leftSec.setPower(power);
                    }

                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    TelemetryCore.getInstance().addLine(errors.toString());
                }
        }).start();
    }

    public synchronized double getTarget() {
        return this.controller.getTarget();
    }

    public synchronized void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public synchronized double getPosition() {
        return -this.leftPri.getCurrentPosition();
    }

    public synchronized boolean isFinished() {
        return this.controller.isFinished();
    }

    public synchronized double getError() {
        return this.controller.getLastError();
    }

    public synchronized void setConstants() {
        this.controller.setCoefficients(Lift.kP, Lift.kI, Lift.kD, Lift.kG);
    }
}
