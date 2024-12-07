package org.firstinspires.ftc.teamcode.commands.claw;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class ClawClose extends CommandBase {
    private final Claw claw;
    private final MultipleTelemetry telemetry;

    public ClawClose(final MultipleTelemetry telemetry, final Claw claw) {
        this.telemetry = telemetry;

        this.claw = claw;
        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setClawPosition(Claw.CLOSE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
