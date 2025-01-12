package org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

public class CloseBasketTrajectories {
    private final ArrayList<Pose> poses;

    public Path scorePreload, getTop, scoreTop, getMid, scoreMid, getBottom, scoreBottom, park;

    public CloseBasketTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/red/close-basket.json"));
    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}
