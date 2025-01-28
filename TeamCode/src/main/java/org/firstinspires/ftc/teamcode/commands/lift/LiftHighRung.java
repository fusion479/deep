package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftHighRung extends CommandBase {
    private final Lift lift;

    public LiftHighRung(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.HIGH_RUNG);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
