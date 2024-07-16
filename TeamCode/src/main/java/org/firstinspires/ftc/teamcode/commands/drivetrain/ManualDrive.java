package org.firstinspires.ftc.teamcode.commands.drivetrain;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.commands.CommandCore;

public class ManualDrive extends CommandCore {
    private final Drivetrain drivetrain;
    private final GamepadEx gamepad;

    public ManualDrive(Drivetrain drivetrain, GamepadEx gamepad, MultipleTelemetry telemetry) {
        super(telemetry);

        this.drivetrain = drivetrain;
        this.gamepad = gamepad;

        super.addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        this.drivetrain.manualDrive(this.gamepad);
    }
}
