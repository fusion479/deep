package org.firstinspires.ftc.teamcode.commands.extendo;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoIncrement extends CommandBase {
    private final Extendo extendo;
    private double increment;

    public ExtendoIncrement(final Extendo extendo, final double increment) {
        this.extendo = extendo;
        this.increment = increment;
        super.addRequirements(this.extendo);

    }

    @Override
    public void initialize() {
        this.extendo.setPosition(extendo.getPosition() + increment);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

