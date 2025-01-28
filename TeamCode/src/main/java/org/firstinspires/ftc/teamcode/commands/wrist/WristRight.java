package org.firstinspires.ftc.teamcode.commands.wrist;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class WristRight extends CommandBase {
    private final Wrist wrist;

    public WristRight(final Wrist wrist) {
        this.wrist = wrist;
        super.addRequirements(wrist);
    }

    @Override
    public void initialize() {
        double target = this.wrist.getPosition() + Wrist.INCREMENT;
        if (target > 1) target -= 1;

        this.wrist.setPosition(target);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
