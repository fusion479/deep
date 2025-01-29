package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.wrist.WristAccepting;
import org.firstinspires.ftc.teamcode.commands.wrist.WristBasket;
import org.firstinspires.ftc.teamcode.commands.wrist.WristReady;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Wrist Test")
public class WristTest extends OpModeCore {
    private Wrist wrist;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.wrist = new Wrist(super.hardwareMap);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new WristBasket(this.wrist));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new WristReady(this.wrist));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new WristAccepting(this.wrist));
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