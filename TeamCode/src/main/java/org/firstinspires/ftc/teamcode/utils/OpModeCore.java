package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.List;

public abstract class OpModeCore extends CommandOpMode {
    private final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();
    private final ElapsedTime startUp = new ElapsedTime();

    // INITIALIZE HUBS FOR BULK READING
    private final List<LynxModule> hubs = super.hardwareMap.getAll(LynxModule.class);
    private final List<DcMotorEx> motors = super.hardwareMap.getAll(DcMotorEx.class);
    private final List<ServoEx> servos = super.hardwareMap.getAll(ServoEx.class);
    private final List<ColorRangeSensor> sensors = super.hardwareMap.getAll(ColorRangeSensor.class);

    public void logCycles() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.period.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.period.seconds());
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void bulkReads() {
        for (LynxModule hub : this.hubs) {
            hub.clearBulkCache();
        }

        for (DcMotorEx motor : this.motors) {
            motor.getCurrentPosition();
            motor.getVelocity();
        }

        for (ServoEx servo : this.servos) {
            servo.getPosition();
            servo.getAngle();
        }

        for (ColorRangeSensor sensor : this.sensors) {
            sensor.getDistance(DistanceUnit.MM);
        }
    }

    public void enableBulkReads() {
        for (LynxModule hub : this.hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    public void resetCycle() {
        this.period.reset();
    }

    public void resetStartUp() {
        this.startUp.reset();
    }

    public void logStartUp() {
        this.multipleTelemetry.addData("Start Up Time: ", this.startUp.milliseconds());
    }

    public MultipleTelemetry getTelemetry() {
        return this.multipleTelemetry;
    }
}
