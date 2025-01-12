package org.firstinspires.ftc.teamcode.commands.wrist.opmodes.teleop.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.lift.LiftDecrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowRung;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Lift Test")
public class LiftTest extends OpModeCore {
    private Lift lift;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.lift = new Lift(super.hardwareMap, super.multipleTelemetry);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new LiftHighRung(super.multipleTelemetry, this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new LiftLowRung(super.multipleTelemetry, this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new LiftLowBasket(super.multipleTelemetry, this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new LiftHighBasket(super.multipleTelemetry, this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new LiftIncrement(super.multipleTelemetry, this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new LiftDecrement(super.multipleTelemetry, this.lift));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        super.waitForStart();

        this.lift.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.multipleTelemetry.addData("Target", this.lift.getTarget());
            super.multipleTelemetry.addData("Position", this.lift.getPosition());
            super.multipleTelemetry.addData("Error", this.lift.getError());

            this.lift.setConstants();

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}