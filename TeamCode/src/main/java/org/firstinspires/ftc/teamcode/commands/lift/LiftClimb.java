package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftClimb extends CommandBase {
    private final Lift lift;

    public LiftClimb(final Lift lift) {
        this.lift = lift;

        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.CLIMB);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
