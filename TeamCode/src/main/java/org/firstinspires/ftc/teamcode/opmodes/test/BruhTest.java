package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Bruh Test")
public class BruhTest extends OpModeCore {
    private DcMotorEx motor;
    private Servo servo;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.motor = hardwareMap.get(DcMotorEx.class, "motor");
        this.servo = hardwareMap.get(Servo.class, "servo");

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> this.servo.setPosition(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> this.servo.setPosition(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new InstantCommand(() -> this.motor.setPower(1)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenReleased(new InstantCommand(() -> this.motor.setPower(0)));

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
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}