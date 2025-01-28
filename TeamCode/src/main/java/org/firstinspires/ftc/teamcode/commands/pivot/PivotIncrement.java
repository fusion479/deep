package org.firstinspires.ftc.teamcode.commands.pivot;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotIncrement extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Pivot pivot;

    public PivotIncrement(final MultipleTelemetry telemetry, final Pivot pivot) {
        this.telemetry = telemetry;

        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Range.clip(this.pivot.getPosition() + pivot.INCREMENT, 0, 1));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

