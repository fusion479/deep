package org.firstinspires.ftc.teamcode.commands.sweeper;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Sweeper;

public class SweeperDown extends CommandBase {
    private final Sweeper sweeper;

    public SweeperDown(final Sweeper sweeper) {
        this.sweeper = sweeper;

        super.addRequirements(sweeper);
    }

    @Override
    public void initialize() {
        this.sweeper.setPosition(Sweeper.DOWN);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

