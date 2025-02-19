package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmSweep extends CommandBase {
    private final Arm arm;

    public ArmSweep(final Arm arm) {
        this.arm = arm;
        super.addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.setPosition(Arm.SWEEP);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
