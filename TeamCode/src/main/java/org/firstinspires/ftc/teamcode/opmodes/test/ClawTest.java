package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.claw.ClawSetPivotPosition;
import org.firstinspires.ftc.teamcode.commands.claw.ClawSetPosition;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Claw Test")
public class ClawTest extends OpModeCore {
    private Claw claw;
    private GamepadEx gamepad;
    private GamepadTrigger intake, outtake;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ClawSetPosition(super.multipleTelemetry, this.claw, Claw.OPEN));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ClawSetPosition(super.multipleTelemetry, this.claw, Claw.CLOSE));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ClawSetPivotPosition(super.multipleTelemetry, this.claw, Claw.SCORE));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ClawSetPivotPosition(super.multipleTelemetry, this.claw, Claw.ACCEPTING));
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