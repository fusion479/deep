package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftDecrement extends CommandBase {
    private final Lift lift;

    public LiftDecrement(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(this.lift.getTarget() - Lift.INCREMENT);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
