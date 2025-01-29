package org.firstinspires.ftc.teamcode.commands.extendo;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoBasket extends CommandBase {
    private final Extendo extendo;

    public ExtendoBasket(final Extendo extendo) {
        this.extendo = extendo;
        super.addRequirements(extendo);
    }

    @Override
    public void initialize() {
        this.extendo.setPosition(Extendo.SCORE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
