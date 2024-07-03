package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class OpModeCore extends CommandOpMode {
    private final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();
    private final ElapsedTime startUp = new ElapsedTime();

    public void log() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.period.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.period.seconds());
        this.multipleTelemetry.update();
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void resetPeriod() {
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
