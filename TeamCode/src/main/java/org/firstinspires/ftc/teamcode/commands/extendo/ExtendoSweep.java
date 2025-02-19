package org.firstinspires.ftc.teamcode.commands.extendo;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoSweep extends CommandBase {
    private final Extendo extendo;

    public ExtendoSweep(final Extendo extendo) {
        this.extendo = extendo;
        super.addRequirements(extendo);
    }

    @Override
    public void initialize() {
        this.extendo.setPosition(Extendo.SWEEP);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
