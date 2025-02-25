package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.utils.PIDController;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;

import java.io.PrintWriter;
import java.io.StringWriter;

@Config
public class Lift extends SubsystemBase {
    public static double MIN_POWER = -0.2;

    //placeholder lift values
    public static double LOW_BASKET = 400;
    public static double LOW_RUNG = 400;
    public static double HIGH_RUNG = 595;
    public static double DRIVE_IN = 500;
    public static double CLIMB = -Integer.MAX_VALUE;

    public static double ACCEPTING = 10;
    public static double INCREMENT = 50;

    public static double SLAM = 260;

    public static double kP = 0.006;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final DcMotorEx rightSec;
    private final DcMotorEx leftSec;
    private final DcMotorEx rightPri;
    private final DcMotorEx leftPri;
    private final VoltageSensor voltageSensor;
    private final PIDController controller;

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

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;

                    synchronized (this.rightPri) {
                        power = this.controller.calculate(this.getPosition() * (12.25 / voltageSensor.getVoltage()));
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

    public double getPosition() {
        return -this.leftPri.getCurrentPosition();
    }

    public synchronized boolean isFinished() {
        return this.controller.isFinished();
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public void setConstants() {
        this.controller.setCoefficients(Lift.kP, Lift.kI, Lift.kD, Lift.kG);
    }
}
