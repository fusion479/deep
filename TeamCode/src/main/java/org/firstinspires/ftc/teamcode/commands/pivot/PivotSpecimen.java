package org.firstinspires.ftc.teamcode.commands.pivot;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotSpecimen extends CommandBase {
    private final Pivot pivot;

    public PivotSpecimen(final Pivot pivot) {
        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Pivot.SPECIMEN);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

