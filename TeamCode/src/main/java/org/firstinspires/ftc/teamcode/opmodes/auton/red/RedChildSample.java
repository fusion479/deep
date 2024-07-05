package org.firstinspires.ftc.teamcode.opmodes.auton.red;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.opmodes.auton.paths.ChildSample;
import org.firstinspires.ftc.teamcode.opmodes.auton.paths.ParentSample;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;

public class RedChildSample extends OpModeCore {
    private ParentSample auto;

    @Override
    public void initialize() {
        this.auto = new ChildSample(Trajectories.Color.RED, this);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();
        this.auto.generate();

        waitForStart();
        this.auto.run();

        super.end();
    }
}
