package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Config
@Autonomous(name = "Park", preselectTeleOp = "Main")
public class Park extends OpModeCore {
    private MecanumDrive drive;
    private ElapsedTime timer;
    private Claw claw;

    public static int DURATION = 3000;

    @Override
    public void initialize() {
        this.drive = new MecanumDrive(super.hardwareMap, new Pose2d(0, 0, 0));
        this.timer = new ElapsedTime();

        this.claw = new Claw(super.hardwareMap, super.multipleTelemetry);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();
        super.waitForStart();

        this.timer.reset();
        while (super.opModeIsActive()) {
            CommandScheduler.getInstance().run();
            super.telemetry.addData("TIMER", this.timer.milliseconds());
            super.telemetry.addData("CONDITION", this.timer.milliseconds() <= Park.DURATION);

            if (this.timer.milliseconds() <= Park.DURATION) {
                this.drive.setPowers(-0.25, -0.25, -0.25, -0.25);
            } else {
                this.drive.setPowers(0, 0, 0, 0);
            }
            super.telemetry.update();
        }

        super.end();

    }
}
