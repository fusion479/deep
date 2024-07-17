package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;
import org.firstinspires.ftc.teamcode.utils.commands.SubsystemCore;

public class Lift extends SubsystemCore {
    // PID Constants & Controller
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;
    public static double kG = 0;
    private final PIDController controller = new PIDController(kP, kI, kD, kG);

    private final DcMotorEx rightMotor;
    private final DcMotorEx leftMotor;

    public Lift(HardwareMap hwMap, MultipleTelemetry telemetry) {
        super(telemetry);

        this.rightMotor = hwMap.get(DcMotorEx.class, "liftRight");
        this.leftMotor = hwMap.get(DcMotorEx.class, "LiftLeft");

        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void asyncPower(CommandOpMode opMode) {
        new Thread(() -> {
            try {
                while (opMode.opModeIsActive())
                    synchronized (this) {
                        this.leftMotor.setPower(this.controller.calculate(this.leftMotor.getCurrentPosition()));
                        this.rightMotor.setPower(this.controller.calculate(this.rightMotor.getCurrentPosition()));
                    }
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
