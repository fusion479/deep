package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.opmodes.auton.paths.ParentSample;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;

public class BlueParentSample extends OpModeCore {
    private ParentSample auto;

    @Override
    public void initialize() {
        this.auto = new ParentSample(Trajectories.Color.BLUE, this);
        this.auto.generatePaths();
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

        this.initialize();
        waitForStart();

        this.auto.runPaths();

        super.end();
    }
}
