package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotAccepting;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotScore;
import org.firstinspires.ftc.teamcode.commands.claw.ClawRotateDown;
import org.firstinspires.ftc.teamcode.commands.claw.ClawRotateUp;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Claw Test")
public class ClawTest extends OpModeCore {
    private Claw claw;
    private Extendo extendo;
    private GamepadEx gamepad;
    private GamepadTrigger intakeAccept, intakeReject;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);
        this.extendo = new Extendo(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ClawRotateDown(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ClawRotateDown(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ClawRotateUp(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ClawPivotScore(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ClawPivotAccepting(super.multipleTelemetry, this.claw));

        this.intakeAccept = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, d -> this.claw.setClawPower(-d), this.gamepad);
        this.intakeReject = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, this.claw::setClawPower, this.gamepad);
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