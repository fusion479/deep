package org.firstinspires.ftc.teamcode.commands.lift;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftLowBasket extends CommandBase {
    private final Lift lift;

    public LiftLowBasket(final Lift lift) {
        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.LOW_BASKET);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
