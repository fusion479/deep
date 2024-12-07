package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
public class Park extends OpModeCore {
    private MecanumDrive drive;
    private ElapsedTime timer;

    public static int DURATION = 5000;

    @Override
    public void initialize() {
        this.drive = new MecanumDrive(super.hardwareMap, new Pose2d(0, 0, 0));
        this.timer = new ElapsedTime();
    }

    @Override
    public void runOpMode() {
        this.initialize();

        super.waitForStart();

        while (super.opModeIsActive()) {
            if (this.timer.milliseconds() <= Park.DURATION) {
                this.drive.setPowers(1.0, 1.0, 1.0, 1.0);
            }
        }

    }
}
