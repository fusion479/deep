package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.arm.ArmAccepting;
import org.firstinspires.ftc.teamcode.commands.wire.WireOne;
import org.firstinspires.ftc.teamcode.commands.wire.WireThree;
import org.firstinspires.ftc.teamcode.commands.wire.WireTwo;
import org.firstinspires.ftc.teamcode.commands.wire.WireZero;
import org.firstinspires.ftc.teamcode.commands.wrist.WristReady;
import org.firstinspires.ftc.teamcode.commands.wrist.WristScore;
import org.firstinspires.ftc.teamcode.subsystems.TestWire;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Wire Test")
public class WireTest extends OpModeCore {
    private TestWire testWire;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        testWire = new TestWire(super.hardwareMap, super.multipleTelemetry);
        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new WireZero(super.multipleTelemetry, this.testWire));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new WireOne(super.multipleTelemetry, this.testWire));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new WireTwo(super.multipleTelemetry, this.testWire));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new WireThree(super.multipleTelemetry, this.testWire));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new WireTwo(super.multipleTelemetry, this.testWire));
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