package org.firstinspires.ftc.teamcode.commands.claw;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class ClawReady extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Claw claw;

    public ClawReady(final MultipleTelemetry telemetry, final Claw claw) {
        this.telemetry = telemetry;

        this.claw = claw;
        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setPosition(Claw.READY);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
