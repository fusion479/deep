package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CommandRobot;

public abstract class OpModeCore extends CommandOpMode {
    private final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime timer = new ElapsedTime();

    public void log() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.timer.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.timer.seconds());
        this.multipleTelemetry.update();
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void resetTimer() {
        this.timer.reset();
    }

    public MultipleTelemetry getTelemetry() {
        return this.multipleTelemetry;
    }
}
