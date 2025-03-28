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
    public static double MIN_POWER = -0.6;

    //placeholder lift values
    public static double LOW_BASKET = 400;
    public static double LOW_RUNG = 400;
    public static double HIGH_RUNG = 2000;
    public static double DRIVE_IN = 1550;
    public static double CLIMB_DOWN = -Integer.MAX_VALUE;
    public static double CLIMB = 3500;
    public static double COMPENSATE = 12.0;

    public static double ACCEPTING = 10;
    public static double INCREMENT = 50;

    public static double SLAM = 850;

    public static double kP = 0.0055;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final DcMotorEx right;
    private final DcMotorEx left;

    private final VoltageSensor voltageSensor;

    private final PIDController controller;

    public Lift(final HardwareMap hwMap) {
        this.right = hwMap.get(DcMotorEx.class, "rightLiftPri");
        this.left = hwMap.get(DcMotorEx.class, "leftLiftPri");

        this.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.right.setDirection(DcMotorSimple.Direction.REVERSE);
        this.left.setDirection(DcMotorSimple.Direction.FORWARD);

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

                    synchronized (this.right) {
                        power = this.controller.calculate(this.getPosition()) * (COMPENSATE / voltageSensor.getVoltage());
                        this.right.setPower(Math.max(power, Lift.MIN_POWER));
                    }

                    synchronized (this.left) {
                        this.left.setPower(Math.max(power, Lift.MIN_POWER));
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

    public synchronized void setMinPower(double min) {
        Lift.MIN_POWER = min;
    }

    public synchronized void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public synchronized double getPosition() {
        return this.left.getCurrentPosition();
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
