package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

public class CommandCore extends CommandBase {
    private final MultipleTelemetry multipleTelemetry;

    public CommandCore(MultipleTelemetry multipleTelemetry) {
        this.multipleTelemetry = multipleTelemetry;
    }

    public MultipleTelemetry getTelemetry() {
        return this.multipleTelemetry;
    }
}
