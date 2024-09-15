package org.firstinspires.ftc.teamcode.commands.claw.pivot;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class PivotSetPosition extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Claw claw;
    private final double position;

    public PivotSetPosition(final MultipleTelemetry telemetry, final Claw claw, final double position) {
        this.telemetry = telemetry;
        this.position = position;

        this.claw = claw;
        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setPivotPosition(position);
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}
