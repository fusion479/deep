package org.firstinspires.ftc.teamcode.utils.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;

public class PathCommand extends CommandBase {
    private final Path path;
    private final Follower follower;
    private final double speed;

    public PathCommand(Follower follower, Path path) {
        this.path = path;
        this.speed = 1;
        this.follower = follower;
    }

    public PathCommand(Follower follower, Path path, double speed) {
        this.path = path;
        this.follower = follower;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        this.follower.setMaxPower(speed);
        this.follower.followPath(path, false);
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