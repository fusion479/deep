package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        Trajectories trajectories = new Trajectories(Positions.Color.BLUE);

        myBot.runAction(trajectories.pathOne(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))));
        myBot.runAction(trajectories.pathTwo(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))));
        myBot.runAction(trajectories.pathThree(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))));

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}