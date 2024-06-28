package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

public class CommandCore extends CommandBase {
    private MultipleTelemetry multipleTelemetry;

    public CommandCore(MultipleTelemetry multipleTelemetry) {
        this.multipleTelemetry = multipleTelemetry;
    }

    public CommandCore() {
    }

    public MultipleTelemetry getTelemetry() {
        return this.multipleTelemetry;
    }
}
