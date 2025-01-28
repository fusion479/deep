package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftLowRung extends CommandBase {
    private final Lift lift;

    public LiftLowRung(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.LOW_RUNG);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
