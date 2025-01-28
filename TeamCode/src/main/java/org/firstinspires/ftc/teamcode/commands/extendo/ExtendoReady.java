package org.firstinspires.ftc.teamcode.commands.extendo;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoReady extends CommandBase {
    private final Extendo extendo;

    public ExtendoReady(final Extendo extendo) {
        this.extendo = extendo;
        super.addRequirements(extendo);
    }

    @Override
    public void initialize() {
        this.extendo.setPosition(Extendo.READY);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
