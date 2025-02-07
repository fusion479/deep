package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;

@Config
public class Lift extends SubsystemBase {
    public static double MIN_POWER = -0.1;

    public static double LOW_BASKET = 700;
    public static double HIGH_BASKET = 1350;

    public static double LOW_RUNG = 400;
    public static double HIGH_RUNG = 479;
    public static double AUTON_HIGH_RUNG = 675;

    public static double ACCEPTING = 10;
    public static double INCREMENT = 50;

    public static double SLAM = 220;

    public static double kP = 0.005;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;

    private final DcMotorEx rightSec;
    private final DcMotorEx leftSec;
    private final DcMotorEx rightPri;
    private final DcMotorEx leftPri;
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

        this.setTarget(0);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;

                    synchronized (this.rightPri) {
                        power = this.controller.calculate(this.getPosition());
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
                    e.printStackTrace();
                }
        }).start();
    }

    public double getTarget() {
        return this.controller.getTarget();
    }

    public void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public double getPosition() {
        return -this.leftPri.getCurrentPosition();
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public void setConstants() {
        this.controller.setCoefficients(Lift.kP, Lift.kI, Lift.kD, Lift.kG);
    }
}
