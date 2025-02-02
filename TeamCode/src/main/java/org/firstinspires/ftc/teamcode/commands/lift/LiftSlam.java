package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftSlam extends CommandBase {
    private final Lift lift;

    public LiftSlam(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(this.lift.getTarget() - Lift.SLAM);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
