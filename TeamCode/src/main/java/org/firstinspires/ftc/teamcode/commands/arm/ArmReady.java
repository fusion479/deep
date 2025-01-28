package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmReady extends CommandBase {
    private final Arm arm;

    public ArmReady(final Arm arm) {
        this.arm = arm;
        super.addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.setPosition(Arm.READY);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

