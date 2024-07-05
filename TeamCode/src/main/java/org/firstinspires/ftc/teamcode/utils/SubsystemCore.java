package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class SubsystemCore extends SubsystemBase {
    private final MultipleTelemetry telemetry;

    public SubsystemCore(final MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }
}