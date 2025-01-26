package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoReady;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoScore;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Extendo Test")
public class ExtendoTest extends OpModeCore {
    private Extendo extendo;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.extendo = new Extendo(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ExtendoAccepting(super.multipleTelemetry, this.extendo));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExtendoReady(super.multipleTelemetry, this.extendo));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ExtendoScore(super.multipleTelemetry, this.extendo));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        this.extendo.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.multipleTelemetry.addData("Position", this.extendo.getPosition());
            super.multipleTelemetry.addData("Target", this.extendo.getTarget());
            super.multipleTelemetry.addData("Error", this.extendo.getError());

            this.extendo.setConstants();

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}