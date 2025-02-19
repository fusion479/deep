package org.firstinspires.ftc.teamcode.commands.pivot;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotSweep extends CommandBase {
    private final Pivot pivot;

    public PivotSweep(final Pivot pivot) {
        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Pivot.SWEEP);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

