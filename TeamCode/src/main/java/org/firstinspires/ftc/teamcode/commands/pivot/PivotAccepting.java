package org.firstinspires.ftc.teamcode.commands.pivot;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotAccepting extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Pivot pivot;

    public PivotAccepting(final MultipleTelemetry telemetry, final Pivot pivot) {
        this.telemetry = telemetry;

        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Pivot.ACCEPTING);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

