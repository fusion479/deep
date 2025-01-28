package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmIntake extends CommandBase {
    private final Arm arm;

    public ArmIntake(final Arm arm) {
        this.arm = arm;
        super.addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.setPosition(Arm.INTAKE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

