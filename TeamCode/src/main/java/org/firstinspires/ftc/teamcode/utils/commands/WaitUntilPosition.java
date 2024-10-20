package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class WaitUntilPosition {
    private final Pose2d target;
    private final Drivetrain drivetrain;
    private final double tolerance;

    public WaitUntilPosition(Pose2d target, Drivetrain drivetrain, double tolerance) {
        this.target = target;
        this.drivetrain = drivetrain;
        this.tolerance = tolerance;
    }

    public boolean isFinished() {
        return Math.hypot(this.drivetrain.getDrive().pose.position.x - this.target.position.x, this.drivetrain.getDrive().pose.position.y - this.target.position.y) < this.tolerance && Math.abs(this.drivetrain.getDrive().pose.heading.toDouble() - this.target.heading.toDouble()) < this.tolerance;
    }
}
