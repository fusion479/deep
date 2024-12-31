package org.firstinspires.ftc.teamcode.utils.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;

public class PathChainCommand extends CommandBase {
    private final PathChain pathChain;
    private final double speed;
    private final Follower follower;

    public PathChainCommand(Follower follower, Path... paths) {
        this.pathChain = new PathChain(paths);
        this.speed = 1;
        this.follower = follower;
    }

    public PathChainCommand(Follower follower, double speed, Path... paths) {
        this.pathChain = new PathChain(paths);
        this.follower = follower;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        follower.setMaxPower(speed);
        follower.followPath(pathChain, false);
    }

    @Override
    public void execute() {
        follower.update();
        follower.getDashboardPoseTracker().update();
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        follower.setMaxPower(1);
    }
}