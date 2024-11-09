package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.utils.commands.GamepadTrigger;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Photon
@TeleOp(name = "Claw Test")
public class ClawTest extends OpModeCore {
    private Claw claw;
    private GamepadEx gamepad;
    private GamepadTrigger intake, outtake;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);

        this.intake = new GamepadTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER, this.claw::setClawPower, this.gamepad);
        this.outtake = new GamepadTrigger(GamepadKeys.Trigger.LEFT_TRIGGER, (d) -> this.claw.setClawPower(-d), this.gamepad);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        super.waitForStart();

        this.intake.startThread(this);
        this.outtake.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}