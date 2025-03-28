package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftAccepting extends CommandBase {
    private final Lift lift;

    public LiftAccepting(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.ACCEPTING);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
