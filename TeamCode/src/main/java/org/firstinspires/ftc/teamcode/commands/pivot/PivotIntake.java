package org.firstinspires.ftc.teamcode.commands.pivot;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotIntake extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Pivot pivot;

    public PivotIntake(final MultipleTelemetry telemetry, final Pivot pivot) {
        this.telemetry = telemetry;

        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Pivot.INTAKE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

