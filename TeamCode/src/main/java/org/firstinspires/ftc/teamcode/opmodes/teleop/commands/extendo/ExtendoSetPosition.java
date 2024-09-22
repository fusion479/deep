package org.firstinspires.ftc.teamcode.opmodes.teleop.commands.extendo;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoSetPosition extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Extendo extendo;
    private final double position;

    public ExtendoSetPosition(final MultipleTelemetry telemetry, final Extendo extendo, final double position) {
        this.telemetry = telemetry;
        this.position = position;

        this.extendo = extendo;
        super.addRequirements(extendo);
    }

    @Override
    public void initialize() {
        this.extendo.setPosition(position);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
