package org.firstinspires.ftc.teamcode.commands.wrist;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class WristAccepting extends CommandBase {
    private final Wrist wrist;

    public WristAccepting(final Wrist wrist) {
        this.wrist = wrist;
        super.addRequirements(wrist);
    }

    @Override
    public void initialize() {
        this.wrist.setPosition(Wrist.ACCEPTING);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
