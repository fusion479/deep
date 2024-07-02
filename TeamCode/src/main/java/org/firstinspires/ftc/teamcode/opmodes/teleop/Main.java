package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

@TeleOp(name = "Main", group = "TeleOp")
public class Main extends OpModeCore {
    private CommandRobot robot;

    public void initialize() {
        this.robot = new CommandRobot(
                RobotCore.Type.TELEOP
        );
    }


    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

        this.initialize();

        super.waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            super.resetTimer();
            CommandScheduler.getInstance().run();

            this.robot.updateTriggers();

            super.log();
        }


        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }
}