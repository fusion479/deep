package org.firstinspires.ftc.teamcode.commands.pivot;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

public class PivotDecrement extends CommandBase {
    private final Pivot pivot;

    public PivotDecrement(final Pivot pivot) {
        this.pivot = pivot;
        super.addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.pivot.setPosition(Range.clip(this.pivot.getPosition() - Pivot.INCREMENT, 0, 1));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

