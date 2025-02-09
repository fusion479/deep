package org.firstinspires.ftc.teamcode.opmodes.auton.trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

@Config
public class SpecFiveTrajectories {
    private final ArrayList<Pose> poses;

    public SpecFiveTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/far-5-basket.pp"));
    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}