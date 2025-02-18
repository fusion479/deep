package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Bruh Test")
public class BruhTest extends OpModeCore {
    private Servo wire, wire2, wire3, wire4;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.wire = super.hardwareMap.get(Servo.class, "wire");
        this.wire.setPosition(1);

        this.wire2 = super.hardwareMap.get(Servo.class, "wire2");
        this.wire2.setPosition(1);

        this.wire3 = super.hardwareMap.get(Servo.class, "wire3");
        this.wire3.setPosition(1);

        this.wire4 = super.hardwareMap.get(Servo.class, "wire4");
        this.wire4.setPosition(1);

        this.gamepad = new GamepadEx(this.gamepad1);
        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> this.wire.setPosition(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> this.wire.setPosition(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> this.wire2.setPosition(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> this.wire2.setPosition(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> this.wire3.setPosition(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> this.wire3.setPosition(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> this.wire4.setPosition(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> this.wire4.setPosition(0)));
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