package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoBasket;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoReady;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Extendo Test")
public class ExtendoTest extends OpModeCore {
    private Extendo extendo;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.extendo = new Extendo(super.hardwareMap);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ExtendoAccepting(this.extendo));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExtendoReady(this.extendo));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ExtendoBasket(this.extendo));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        this.extendo.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            TelemetryCore.getInstance().addData("Position", this.extendo.getPosition());
            TelemetryCore.getInstance().addData("Target", this.extendo.getTarget());
            TelemetryCore.getInstance().addData("Error", this.extendo.getError());

            this.extendo.setConstants();

            super.logCycles();
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}