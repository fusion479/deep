package org.firstinspires.ftc.teamcode.commands.lift;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Lift;


public class LiftSetPosition extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Lift lift;
    private final double position;

    public LiftSetPosition(final MultipleTelemetry telemetry, final Lift lift, final double position) {
        this.telemetry = telemetry;
        this.position = position;

        this.lift = lift;
        super.addRequirements(lift);
    }

    @Override
    public void initialize() {
        this.lift.setTarget(position);
    }
    @Override
    public boolean isFinished() {
        return this.lift.isFinished();
    }
}
