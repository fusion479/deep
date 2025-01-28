package org.firstinspires.ftc.teamcode.commands.pivot;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotAccepting extends CommandBase {
    private final Pivot pivot;

    public PivotAccepting(final Pivot pivot) {
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

