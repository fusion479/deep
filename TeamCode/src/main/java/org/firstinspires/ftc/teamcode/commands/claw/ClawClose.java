package org.firstinspires.ftc.teamcode.commands.claw;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class ClawClose extends CommandBase {
    private final Claw claw;

    public ClawClose(final Claw claw) {
        this.claw = claw;
        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setPosition(Claw.CLOSE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

