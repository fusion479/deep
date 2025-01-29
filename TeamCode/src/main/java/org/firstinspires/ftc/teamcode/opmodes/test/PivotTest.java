package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.pivot.PivotAccepting;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotBasket;
import org.firstinspires.ftc.teamcode.commands.pivot.PivotReady;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Pivot Test")
public class PivotTest extends OpModeCore {
    private Pivot pivot;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.pivot = new Pivot(super.hardwareMap);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new PivotAccepting(this.pivot));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new PivotReady(this.pivot));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new PivotBasket(this.pivot));

    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.logCycles();
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}