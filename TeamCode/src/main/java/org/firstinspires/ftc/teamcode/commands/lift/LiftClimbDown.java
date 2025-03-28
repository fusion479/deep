package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftClimbDown extends CommandBase {
    private final Lift lift;

    public LiftClimbDown(final Lift lift) {
        this.lift = lift;

        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.CLIMB_DOWN);
    }

    @Override
    public void execute() {
        this.lift.setMinPower(-1.0);

    }

    @Override
    public void end(boolean interrupted) {
        this.lift.setMinPower(-0.6);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
