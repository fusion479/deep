package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

@Photon
@TeleOp(name = "Main", group = "TeleOp")
public class Main extends OpModeCore {
    private CommandRobot robot;

    public void initialize() {
        this.robot = new CommandRobot(
                RobotCore.Type.TELEOP
        );

        super.enableBulkReads();
    }


    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

        super.waitForStart();
        super.resetStartUp();
        super.logStartUp();

        while (!isStopRequested() && opModeIsActive()) {
            super.bulkReads();
            CommandScheduler.getInstance().run();

            this.robot.updateTriggers();

            super.logCycles();
            super.getTelemetry().update();
        }

        super.end();
    }
}