package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.LogWriter;
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
    public static double MIN_POWER = -0.1;

    public static double LOW_BASKET = 700;
    public static double HIGH_BASKET = 1350;

    public static double LOW_RUNG = 400;
    public static double HIGH_RUNG = 630;
    public static double DRIVE_IN = 475;

    public static double ACCEPTING = 10;
    public static double INCREMENT = 50;

    public static double SLAM = 250;

    public static double lowkP = 0.005;
    public static double lowkI = 0;
    public static double lowkD = 0;
    public static double lowkG = 0;
    public static double highkP = 0.005;
    public static double highkI = 0;
    public static double highkD = 0;
    public static double highkG = 0;

    public static double LOW_VOLTAGE = 12;

    private final DcMotorEx rightSec;
    private final DcMotorEx leftSec;
    private final DcMotorEx rightPri;
    private final DcMotorEx leftPri;
    private VoltageSensor voltageSensor;
    private final PIDController highController;
    private final PIDController lowController;

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

        this.highController = new PIDController(Lift.highkP, Lift.highkI, Lift.highkD, Lift.highkG);
        this.highController.setAllowedError(15);
        this.lowController = new PIDController(Lift.lowkP, Lift.lowkI, Lift.lowkD, Lift.lowkG);
        this.lowController.setAllowedError(15);

        this.voltageSensor = hwMap.get(VoltageSensor.class, "Control Hub");

        this.setTarget(0);
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive())
                try {
                    double power;
                    double voltage;
                    voltage = voltageSensor.getVoltage();

                    synchronized (this.rightPri) {
                        switch (voltage){
                            case voltage < this.LOW_VOLTAGE:
                                power = this.lowController.calculate(this.getPosition());
                            case voltage >= this.LOW_VOLTAGE:
                                power = this.highController.calculate(this.getPosition());
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
        }).start;
    }

    public double getTarget() {
        return this.lowController.getTarget();
    }

    public void setTarget(double target) {
        this.highController.setTarget(target);
        this.lowController.setTarget(target);
    }

    public double getPosition() {
        return -this.leftPri.getCurrentPosition();
    }

    public void setPower(double power) {
        this.rightSec.setPower(power);
        this.rightPri.setPower(power);
        this.leftPri.setPower(power);
        this.leftSec.setPower(power);
    }

   // public boolean isFinished() {
   //     return this.controller.isFinished();
   // }

    public double getError() {
            return this.lowController.getLastError();
        }

    public void setConstants() {
        this.lowController.setCoefficients(Lift.lowkP, Lift.lowkI, Lift.lowkD, Lift.lowkG);
        this.highController.setCoefficients(Lift.highkP, Lift.highkI, Lift.highkD, Lift.highkG);
    }
}
