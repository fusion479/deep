package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.lift.LiftClimb;
import org.firstinspires.ftc.teamcode.commands.lift.LiftClimbDown;
import org.firstinspires.ftc.teamcode.commands.lift.LiftDecrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftHighRung;
import org.firstinspires.ftc.teamcode.commands.lift.LiftIncrement;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowBasket;
import org.firstinspires.ftc.teamcode.commands.lift.LiftLowRung;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Lift Test")
public class LiftTest extends OpModeCore {
    private Lift lift;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);
        this.lift = new Lift(super.hardwareMap);

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new LiftHighRung(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new LiftLowRung(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new LiftLowBasket(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new LiftIncrement(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new LiftDecrement(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> this.lift.setTarget(Lift.CLIMB_DOWN)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new LiftClimb(this.lift));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new LiftClimbDown(this.lift));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        this.lift.startThread(this);
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            this.lift.setTarget(this.lift.getTarget());
            TelemetryCore.getInstance().addData("Target", this.lift.getTarget());
            TelemetryCore.getInstance().addData("Position", this.lift.getPosition());
            TelemetryCore.getInstance().addData("Error", this.lift.getError());

            this.lift.setConstants();

            super.logCycles();
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}