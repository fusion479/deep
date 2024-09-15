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
    public static double BOTTOM = 0;
    public static double LOWBASKET = 0;
    public static double HIGHBASKET = 0;
    public static double LOWRUNG = 0;
    public static double HIGHRUNG = 0;
    public static double INCREMENT = 150;
    // PID Constants & Controller
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;
    private final PIDController controller = new PIDController(kP, kI, kD, kG);

    private final DcMotorEx rightMotor;
    private final DcMotorEx leftMotor;

    private final MultipleTelemetry telemetry;

    public Lift(HardwareMap hwMap, MultipleTelemetry telemetry) {
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

    public void setTarget(double target) {
        this.controller.setTarget(target);
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }

    public double getTarget() {
        return this.controller.getTarget();
    }



    // TODO: Test threads, if threads work remove
    @Override
    public void periodic() {
        if (!this.controller.isFinished()) {
            this.leftMotor.setPower(this.controller.calculate(this.leftMotor.getCurrentPosition()));
            this.rightMotor.setPower(this.controller.calculate(this.rightMotor.getCurrentPosition()));
        } else {
            this.rightMotor.setPower(kG);
            this.leftMotor.setPower(kG);
        }
    }
}
