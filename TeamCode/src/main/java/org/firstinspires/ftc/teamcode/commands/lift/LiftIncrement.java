package org.firstinspires.ftc.teamcode.commands.lift;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftIncrement extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Lift lift;

    public LiftIncrement(final MultipleTelemetry telemetry, final Lift lift) {
        this.telemetry = telemetry;

        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(Lift.HIGH_BASKET);
    }

    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
