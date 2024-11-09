package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoSetPosition;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;


@TeleOp(name = "Extendo Test")
public class ExtendoTest extends OpModeCore {
    private Extendo extendo;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.extendo = new Extendo(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ExtendoSetPosition(super.multipleTelemetry, this.extendo, Extendo.ACCEPTING));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExtendoSetPosition(super.multipleTelemetry, this.extendo, Extendo.SCORE));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ExtendoSetPosition(super.multipleTelemetry, this.extendo, Extendo.READY));
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