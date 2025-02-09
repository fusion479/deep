package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
@TeleOp(name = "Manual Lift Test")
public class ManualLiftTest extends OpModeCore {
    public static double POWER_UP = 0.75;
    public static double POWER_DOWN = -0.5;
    private Lift lift;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.lift = new Lift(super.hardwareMap);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whileHeld(new InstantCommand(() -> this.lift.setPower(POWER_DOWN)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whileHeld(new InstantCommand(() -> this.lift.setPower(POWER_UP)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenReleased(new InstantCommand(() -> this.lift.setPower(0)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenReleased(new InstantCommand(() -> this.lift.setPower(0)));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            TelemetryCore.getInstance().addData("Position", this.lift.getPosition());

            super.logCycles();
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}