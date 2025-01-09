package org.firstinspires.ftc.teamcode.commands.wire;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TestWire;

public class WireZero extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final TestWire testWire;

    public WireZero(final MultipleTelemetry telemetry, final TestWire testWire) {
        this.telemetry = telemetry;

        this.testWire = testWire;
        super.addRequirements(testWire);
    }

    @Override
    public void initialize() {
        this.testWire.setPosition0(testWire.ZERO);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

