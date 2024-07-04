package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

public abstract class OpModeCore extends CommandOpMode {
    private final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();
    private final ElapsedTime startUp = new ElapsedTime();

    // INITIALIZE HUBS AND MOTORS
    private final List<LynxModule> hubs = super.hardwareMap.getAll(LynxModule.class);
    private final List<DcMotorEx> motors = super.hardwareMap.getAll(DcMotorEx.class);

    public void logCycles() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.period.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.period.seconds());
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void bulkRead() {
        for (LynxModule hub : this.hubs) {
            hub.clearBulkCache();
        }

        for (DcMotorEx motor : this.motors) {
            motor.getCurrentPosition();
            motor.getVelocity();
        }
    }

    public void setBulks() {
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
