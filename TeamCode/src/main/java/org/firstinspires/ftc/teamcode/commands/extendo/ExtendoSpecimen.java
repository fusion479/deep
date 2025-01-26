package org.firstinspires.ftc.teamcode.commands.extendo;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extendo;

public class ExtendoSpecimen extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Extendo extendo;

    public ExtendoSpecimen(final MultipleTelemetry telemetry, final Extendo extendo) {
        this.telemetry = telemetry;

        this.extendo = extendo;
        super.addRequirements(extendo);
    }

    @Override
    public void initialize() {
        this.extendo.setPosition(Extendo.SPECIMEN);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
