package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.BlueCloseBasket;


@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpMode {
    private BlueCloseBasket auto;
    private CommandRobot robot;

    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {


        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot
        auto.follower.update();
        autonomousPathUpdate();


        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", auto.follower.getPose().getX());
        telemetry.addData("y", auto.follower.getPose().getY());
        telemetry.addData("heading", auto.follower.getPose().getHeading());
        telemetry.update();
    }

    //- Follower State: "if(!follower.isBusy() {}" (Though, I don't recommend this because it might not return due to holdEnd
//- Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
//- Robot Position: "if(follower.getPose().getX() > 36) {}"
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                auto.follower.followPath(auto.scorePreload);
                setPathState(1);
                break;
            case 1:
                if (auto.follower.getPose().getX() > (auto.SCORE.getX() - 1) && auto.follower.getPose().getY() > (auto.SCORE.getY() - 1)) {
                    /* Score Preload */


                    auto.follower.followPath(auto.grabSpikemark1, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (auto.follower.getPose().getX() > (auto.BOTTOM_SPIKEMARK.getX() - 1) && auto.follower.getPose().getY() > (auto.BOTTOM_SPIKEMARK.getY() - 1)) {
                    /* Grab Sample */


                    auto.follower.followPath(auto.scoreSpikemark1, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (auto.follower.getPose().getX() > (auto.SCORE.getX() - 1) && auto.follower.getPose().getY() > (auto.SCORE.getY() - 1)) {
                    /* Score Sample */


                    auto.follower.followPath(auto.grabSpikemark2, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (auto.follower.getPose().getX() > (auto.MID_SPIKEMARK.getX() - 1) && auto.follower.getPose().getY() > (auto.MID_SPIKEMARK.getY() - 1)) {
                    /* Grab Sample */


                    auto.follower.followPath(auto.scoreSpikemark2, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (auto.follower.getPose().getX() > (auto.SCORE.getX() - 1) && auto.follower.getPose().getY() > (auto.SCORE.getY() - 1)) {
                    /* Score Sample */


                    auto.follower.followPath(auto.grabSpikemark3, true);
                    setPathState(6);
                }
                break;
            case 6:
                if (auto.follower.getPose().getX() > (auto.TOP_SPIKEMARK.getX() - 1) && auto.follower.getPose().getY() > (auto.TOP_SPIKEMARK.getY() - 1)) {
                    /* Grab Sample */


                    auto.follower.followPath(auto.scoreSpikemark3, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (auto.follower.getPose().getX() > (auto.SCORE.getX() - 1) && auto.follower.getPose().getY() > (auto.SCORE.getY() - 1)) {
                    /* Score Sample */


                    auto.follower.followPath(auto.park, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (auto.follower.getPose().getX() > (auto.SUBMERSIBLE.getX() - 1) && auto.follower.getPose().getY() > (auto.SUBMERSIBLE.getY() - 1)) {
                    /* Level 1 Ascent */


                    setPathState(-1);
                }
                break;
        }
    }


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}


    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }


    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}
