package org.firstinspires.ftc.teamcode.commands.claw;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class IntakeUntilSpecimen extends CommandBase {
    private final MultipleTelemetry telemetry;
    private final Claw claw;
    private final Claw.Color color;
    private final int duration;
    private final ElapsedTime timer;

    public IntakeUntilSpecimen(final MultipleTelemetry telemetry, final Claw claw, final Claw.Color color, final int duration) {
        this.telemetry = telemetry;
        this.claw = claw;
        this.color = color;
        this.duration = duration;
        this.timer = new ElapsedTime();

        super.addRequirements(claw);
    }

    @Override
    public void initialize() {
        this.claw.setClawPower(1);
        this.timer.reset();
    }

    @Override
    public boolean isFinished() {
        return this.claw.hasValidBlock(color) != Claw.BlockCases.WAIT || this.timer.seconds() >= duration;
    }
}
