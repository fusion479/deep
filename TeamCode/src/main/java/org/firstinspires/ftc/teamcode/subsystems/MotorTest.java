package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
@TeleOp(name = "Motor Test")
public class MotorTest extends OpModeCore {
    public static double POWER = 0.5;

    private DcMotorEx rightLiftPri, rightLiftSec, leftLiftPri, leftLiftSec, rightFront, rightRear, leftFront, leftRear;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        super.initialize();

        this.gamepad = new GamepadEx(super.gamepad1);

        this.rightLiftPri = super.hardwareMap.get(DcMotorEx.class, "rightLiftPri");
        this.rightLiftSec = super.hardwareMap.get(DcMotorEx.class, "rightLiftSec");
        this.leftLiftSec = super.hardwareMap.get(DcMotorEx.class, "leftLiftSec");
        this.leftLiftPri = super.hardwareMap.get(DcMotorEx.class, "leftLiftPri");
        this.leftFront = super.hardwareMap.get(DcMotorEx.class, "leftFront");
        this.leftRear = super.hardwareMap.get(DcMotorEx.class, "leftRear");
        this.rightFront = super.hardwareMap.get(DcMotorEx.class, "rightFront");
        this.rightRear = super.hardwareMap.get(DcMotorEx.class, "rightRear");

        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whileHeld(new InstantCommand(() -> this.rightLiftPri.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.A).whenReleased(new InstantCommand(() -> this.rightLiftPri.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whileHeld(new InstantCommand(() -> this.leftLiftPri.setPower(-POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.B).whenReleased(new InstantCommand(() -> this.leftLiftPri.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new InstantCommand(() -> this.rightLiftSec.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.X).whenReleased(new InstantCommand(() -> this.rightLiftSec.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new InstantCommand(() -> this.leftLiftSec.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.Y).whenReleased(new InstantCommand(() -> this.leftLiftSec.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whileHeld(new InstantCommand(() -> this.rightFront.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenReleased(new InstantCommand(() -> this.rightFront.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(new InstantCommand(() -> this.leftFront.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenReleased(new InstantCommand(() -> this.leftFront.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new InstantCommand(() -> this.rightRear.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenReleased(new InstantCommand(() -> this.rightRear.setPower(0)));

        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whileHeld(new InstantCommand(() -> this.leftRear.setPower(POWER)));
        this.gamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenReleased(new InstantCommand(() -> this.leftRear.setPower(0)));
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        super.waitForStart();

        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            TelemetryCore.getInstance().addLine("A: RIGHT LIFT PRIMARY");
            TelemetryCore.getInstance().addLine("B: LEFT LIFT PRIMARY");
            TelemetryCore.getInstance().addLine("X: RIGHT LIFT SECONDARY");
            TelemetryCore.getInstance().addLine("Y: LEFT LIFT SECONDARY");
            TelemetryCore.getInstance().addLine("DPAD_UP: RIGHT FRONT");
            TelemetryCore.getInstance().addLine("DPAD_DOWN: LEFT FRONT");
            TelemetryCore.getInstance().addLine("DPAD_LEFT: RIGHT REAR");
            TelemetryCore.getInstance().addLine("DPAD_RIGHT: LEFT REAR");

            super.logCycles();
            TelemetryCore.getInstance().update();
        }

        super.end();
    }
}