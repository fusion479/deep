package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.wire.WireZero;
import org.firstinspires.ftc.teamcode.subsystems.TestWire;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Wire Test")
public class WireTest extends OpModeCore {
    private TestWire testWire;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        // one of these is a cr servo as well - hopefully everything else will work except that one
        this.gamepad = new GamepadEx(super.gamepad1);
        testWire = new TestWire(super.hardwareMap);
        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new WireZero(this.testWire));
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
        }

        super.end();
    }
}