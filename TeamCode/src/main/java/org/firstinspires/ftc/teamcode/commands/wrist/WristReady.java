package org.firstinspires.ftc.teamcode.commands.wrist;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class WristReady extends CommandBase {
    private final Wrist wrist;

    public WristReady(final Wrist wrist) {
        this.wrist = wrist;
        super.addRequirements(wrist);
    }

    @Override
    public void initialize() {
        this.wrist.setPosition(Wrist.READY);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
