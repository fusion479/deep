package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

import java.io.PrintWriter;
import java.io.StringWriter;

@Config
public class Extendo extends SubsystemBase {
    public static double kP = 0.005;
    public static double kI = 0;
    public static double kD = 0;

    public static double OFFSET = 11.45;
    public static int ALLOWED_ERROR = 3;
    public static int SCORE = -30;
    public static int READY = 40;
    public static int ACCEPTING = 250;
    public static int SPECIMEN = 90;
    public static int DRIVE_IN = 120;
    public static double SWEEP = 0;
    public static int TELE_OFFSET = 75;

    private double power;
    private boolean toggleController;

    public final AnalogInput encoder;
    private final CRServo extendoBottom;
    private final CRServo extendoTop;
    private final PIDController controller;

    private double rotations = 0;
    private double prevPos = 0;

    public Extendo(final HardwareMap hwMap) {
        this.controller = new PIDController(Extendo.kP, Extendo.kI, Extendo.kD);

        this.extendoBottom = hwMap.get(CRServo.class, "extendoBottom");
        this.extendoTop = hwMap.get(CRServo.class, "extendoTop");
        this.encoder = hwMap.get(AnalogInput.class, "encoder");

        this.controller.setAllowedError(Extendo.ALLOWED_ERROR);
    }

    public synchronized double getPosition() {
        return -(Extendo.OFFSET - (this.encoder.getVoltage() / 3.3 * 360));
    }

    public void startThread(OpModeCore opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive()) {
                try {
                    double currPos = this.getPosition();

                    if (prevPos - currPos > 180) rotations++;
                    else if (180 < currPos - prevPos) rotations--;

                    if (this.toggleController) {


                        this.power = this.controller.calculate(currPos + 360 * rotations);
                        if (!this.controller.isFinished()) {
                            synchronized (this.extendoBottom) {
                                this.extendoBottom.setPower(-this.power);
                            }

                            synchronized (this.extendoTop) {
                                this.extendoTop.setPower(-this.power);
                            }
                        }
                    } else {
                        this.extendoTop.setPower(this.power);
                        this.extendoBottom.setPower(this.power);
                    }

                    prevPos = currPos;
                    Thread.sleep(50);
                } catch (Exception e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    TelemetryCore.getInstance().addLine(errors.toString());
                }
            }
        }).start();
    }

    public void setConstants() {
        this.controller.setCoefficients(Extendo.kP, Extendo.kI, Extendo.kD);
    }

    public double getTarget() {
        return this.controller.getTarget();
    }

    public synchronized void setPosition(double position) {
        this.toggleController = true;
        this.controller.setTarget(position);
    }

    public void setPower(double power) {
        this.toggleController = false;
        this.power = power;
    }

    public double getError() {
        return this.controller.getLastError();
    }
}
