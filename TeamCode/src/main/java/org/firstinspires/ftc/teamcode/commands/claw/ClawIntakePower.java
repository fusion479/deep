package org.firstinspires.ftc.teamcode.commands.claw;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class ClawIntakePower extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Claw claw;
    private final double power;

    public ClawIntakePower(final MultipleTelemetry telemetry, final Claw claw, final double power) {
        this.telemetry = telemetry;
        this.power = power;
        this.claw = claw;

        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setClawPower(power);
    }
}
