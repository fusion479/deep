package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;

public class Lift extends SubsystemBase {
    public static double ACCEPTING = 0;
    public static double LOW_BASKET = 0;
    public static double HIGH_BASKET = 0;
    public static double LOW_RUNG = 0;
    public static double HIGH_RUNG = 0;
    public static double INCREMENT = 150;

    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final MultipleTelemetry telemetry;
    private final DcMotorEx rightMotor;
    private final DcMotorEx leftMotor;

    private PIDController controller;

    public Lift(final HardwareMap hwMap, final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;

        this.rightMotor = hwMap.get(DcMotorEx.class, "liftRight");
        this.leftMotor = hwMap.get(DcMotorEx.class, "liftLeft");

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.controller = new PIDController(kP, kI, kD, kG);
        this.controller.setAllowedError(10);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    synchronized (this.leftMotor) {
                        this.leftMotor.setPower(this.controller.calculate(this.leftMotor.getCurrentPosition()));
                    }
                    synchronized (this.rightMotor) {
                        this.rightMotor.setPower(this.controller.calculate(this.rightMotor.getCurrentPosition()));
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

    public boolean isFinished() {
        return this.controller.isFinished();
    }

    public void setConstants() {
        this.controller = new PIDController(kP, kI, kD, kG);
    }
}
