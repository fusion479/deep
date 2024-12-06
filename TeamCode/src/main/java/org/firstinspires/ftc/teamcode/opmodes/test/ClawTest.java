package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.claw.ClawClose;
import org.firstinspires.ftc.teamcode.commands.claw.ClawOpen;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotAccepting;
import org.firstinspires.ftc.teamcode.commands.claw.ClawPivotScore;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoAccepting;
import org.firstinspires.ftc.teamcode.commands.extendo.ExtendoScore;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Claw Test")
public class ClawTest extends OpModeCore {
    private Claw claw;
    private Extendo extendo;
    private GamepadEx gamepad;
    private GamepadTrigger intake, outtake;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);
        this.extendo = new Extendo(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ClawOpen(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ClawClose(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ClawPivotScore(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ClawPivotAccepting(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ExtendoScore(super.multipleTelemetry, this.extendo));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ExtendoAccepting(super.multipleTelemetry, this.extendo));
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