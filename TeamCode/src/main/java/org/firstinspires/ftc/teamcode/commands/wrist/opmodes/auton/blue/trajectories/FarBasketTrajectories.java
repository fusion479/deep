package org.firstinspires.ftc.teamcode.commands.wrist.opmodes.auton.blue.trajectories;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

public class FarBasketTrajectories {
    private final ArrayList<Pose> poses;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom;

    public FarBasketTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/blue/far-basket.json"));
    }

    public Pose getStart() {
        return this.poses.get(1);
    }
}
