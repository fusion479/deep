package org.firstinspires.ftc.teamcode.commands.wrist;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class WristAccepting extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Wrist wrist;

    public WristAccepting(final MultipleTelemetry telemetry, final Wrist wrist) {
        this.telemetry = telemetry;

        this.wrist = wrist;
        super.addRequirements(wrist);
    }

    @Override
    public void initialize() {
        this.wrist.setPosition(Wrist.ACCEPTING);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}