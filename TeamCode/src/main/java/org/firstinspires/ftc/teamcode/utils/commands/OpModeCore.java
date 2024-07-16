package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

@Photon
public abstract class OpModeCore extends CommandOpMode {
    private final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();
    private final ElapsedTime startUp = new ElapsedTime();

    // DECLARE VARS FOR BULK READING
    private List<LynxModule> hubs;
    private List<DcMotorEx> motors;
    private List<ColorRangeSensor> sensors;

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
        }
    }

    public void enableBulkReads() {
        this.hubs = super.hardwareMap.getAll(LynxModule.class);
        this.motors = super.hardwareMap.getAll(DcMotorEx.class);
        this.sensors = super.hardwareMap.getAll(ColorRangeSensor.class);

        for (LynxModule hub : this.hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    public void resetCycle() {
        this.period.reset();
    }

    public MultipleTelemetry getTelemetry() {
        return this.multipleTelemetry;
    }
}
