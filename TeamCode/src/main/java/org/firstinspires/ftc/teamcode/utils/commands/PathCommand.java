package org.firstinspires.ftc.teamcode.utils.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.CommandRobot;

public class PathCommand extends CommandBase {
    private final Path path;
    private final Follower follower;
    private final double speed;

    public PathCommand(CommandRobot robot, Path path) {
        this.path = path;
        this.speed = 1;
        this.follower = robot.getFollower();
    }

    public PathCommand(CommandRobot robot, Path path, double speed) {
        this.path = path;
        this.follower = robot.getFollower();
        this.speed = speed;
    }

    @Override
    public void initialize() {
        this.follower.setMaxPower(speed);
        this.follower.followPath(path, true);
    }

    @Override
    public void execute() {
        this.follower.update();
        this.follower.getDashboardPoseTracker().update();
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !this.follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        this.follower.setMaxPower(1);
    }
}