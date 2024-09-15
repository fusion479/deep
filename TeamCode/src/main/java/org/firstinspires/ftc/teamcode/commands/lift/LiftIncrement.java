package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftIncrement extends CommandBase {
    private final Lift lift;
    private double increment;

    public LiftIncrement(final Lift lift, final double increment) {
        this.lift = lift;
        this.increment = increment;
        super.addRequirements(this.lift);

    }

    @Override
    public void initialize() {
        this.lift.setTarget(lift.getTarget() + increment);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}

