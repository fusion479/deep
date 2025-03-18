package org.firstinspires.ftc.teamcode.commands.sweeper;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Sweeper;

public class SweeperUp extends CommandBase {
    private final Sweeper sweeper;

    public SweeperUp(final Sweeper sweeper) {
        this.sweeper = sweeper;

        super.addRequirements(sweeper);
    }

    @Override
    public void initialize() {
        this.sweeper.setPosition(Sweeper.UP);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

