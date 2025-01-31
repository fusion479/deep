package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.PIDController;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class Extendo extends SubsystemBase {
    public static double kP = 0.004;
    public static double kI = 0;
    public static double kD = 0;

    public static double OFFSET = 217;
    public static int ALLOWED_ERROR = 10;
    public static int SCORE = -10;
    public static int READY = 0;
    public static int ACCEPTING = 165;
    public static int SPECIMEN = 125;

    private double power;

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

                    this.power = this.controller.calculate(currPos + 360 * rotations);
                    if (!this.controller.isFinished()) {
                        synchronized (this.extendoBottom) {
                            this.extendoBottom.setPower(-this.power);
                        }

                        synchronized (this.extendoTop) {
                            this.extendoTop.setPower(-this.power);
                        }
                    }

                    TelemetryCore.getInstance().addData("pls work", currPos);
                    prevPos = currPos;
                } catch (Exception e) {
                    e.printStackTrace();
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

    public void setPosition(double position) {
        this.controller.setTarget(position);
    }

    public double getError() {
        return this.controller.getLastError();
    }

    public boolean isFinished() {
        return this.controller.isFinished();
    }
}
