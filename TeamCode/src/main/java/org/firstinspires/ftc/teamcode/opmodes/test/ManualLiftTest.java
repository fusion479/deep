package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Manual Lift Test")
public class ManualLiftTest extends OpModeCore {
    private Lift lift;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.lift = new Lift(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whileHeld(new InstantCommand(() -> this.lift.setPower(0.25)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whileHeld(new InstantCommand(() -> this.lift.setPower(-0.25)));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        super.waitForStart();

        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}