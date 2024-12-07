package org.firstinspires.ftc.teamcode.commands.lift;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftReset extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Lift lift;

    public LiftReset(final MultipleTelemetry telemetry, final Lift lift) {
        this.telemetry = telemetry;

        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.reset();
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}