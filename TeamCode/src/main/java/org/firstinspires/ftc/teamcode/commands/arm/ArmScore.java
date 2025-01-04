package org.firstinspires.ftc.teamcode.commands.arm;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmScore extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Arm arm;

    public ArmScore(final MultipleTelemetry telemetry, final Arm arm) {
        this.telemetry = telemetry;

        this.arm = arm;
        super.addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.setPosition(Arm.SCORE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

