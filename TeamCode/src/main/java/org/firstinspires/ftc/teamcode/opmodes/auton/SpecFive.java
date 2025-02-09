package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.SpecFourTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
@Autonomous(name = "5-Spec Far Basket", preselectTeleOp = "Main")
public class SpecFive extends OpModeCore {
    private CommandRobot robot;
    private SpecFourTrajectories trajectories;

    @Override
    public void initialize() {
        this.trajectories = new SpecFourTrajectories();

        robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                // ...
        );

        this.robot.startAutoThreads(this);
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
