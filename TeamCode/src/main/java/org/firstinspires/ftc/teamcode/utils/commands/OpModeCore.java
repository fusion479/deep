package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.util.ElapsedTime;

@Photon
public abstract class OpModeCore extends CommandOpMode {
    public final MultipleTelemetry multipleTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    private final ElapsedTime period = new ElapsedTime();
    private final ElapsedTime startUp = new ElapsedTime();

    public void logCycles() {
        this.multipleTelemetry.addData("Period (Seconds / 1 Cycle): ", this.period.seconds());
        this.multipleTelemetry.addData("Frequency (Hz, Cycles / 1 Second ): ", 1 / this.period.seconds());
    }

    public void end() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }

    public void resetStartUp() {
        this.startUp.reset();
    }

    public void logStartUp() {
        this.multipleTelemetry.addData("Start Up Time (Seconds): ", this.startUp.seconds());
    }

    public void resetCycle() {
        this.period.reset();
    }
}
