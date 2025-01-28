package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmAccepting extends CommandBase {
    private final Arm arm;

    public ArmAccepting(final Arm arm) {
        this.arm = arm;
        super.addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.setPosition(Arm.ACCEPTING);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

