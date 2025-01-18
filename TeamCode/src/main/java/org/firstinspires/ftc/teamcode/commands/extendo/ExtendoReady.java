package org.firstinspires.ftc.teamcode.commands.extendo;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoReady extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Extendo extendo;

    public ExtendoReady(final MultipleTelemetry telemetry, final Extendo extendo) {
        this.telemetry = telemetry;

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
