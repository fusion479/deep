package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.claw.ClawDown;
import org.firstinspires.ftc.teamcode.commands.claw.ClawUp;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Claw Test")
public class ClawTest extends OpModeCore {
    private Claw claw;
    private GamepadEx gamepad;
    private GamepadTrigger intakeAccept, intakeReject;
    private Claw.BlockCases prev;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ClawDown(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ClawDown(super.multipleTelemetry, this.claw));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ClawUp(super.multipleTelemetry, this.claw));

        this.intakeAccept = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, d -> this.claw.setPower(-d), this.gamepad);
        this.intakeReject = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, this.claw::setPower, this.gamepad);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        super.waitForStart();

        this.claw.startThread(this);
        this.intakeAccept.startThread(this);
        this.intakeReject.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.multipleTelemetry.addData("Trigger", this.gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
            this.claw.logSensorData();

            Claw.BlockCases detection = this.claw.hasValidBlock(Claw.Color.RED);
            if (detection == Claw.BlockCases.ACCEPT) {
                super.multipleTelemetry.addLine("STOP CLAW => READY POS");
            } else if (detection == Claw.BlockCases.REJECT) {
                super.multipleTelemetry.addLine("REJECT, WRONG COLOR");
                this.claw.setPower(1);
            } else {
                super.multipleTelemetry.addLine("WAITING FOR VALID BLOCK");
            }

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}