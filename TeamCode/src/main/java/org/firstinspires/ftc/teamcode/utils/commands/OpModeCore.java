package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class OpModeCore extends CommandOpMode {
    public final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();

    public void logCycles() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.period.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.period.seconds());
    }

    @Override
    public void initialize() {
        CommandScheduler.getInstance().enable();
        CommandScheduler.getInstance().reset();
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void resetCycle() {
        this.period.reset();
    }
}
