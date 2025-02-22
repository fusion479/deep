package com.example.meepmeep;

import com.example.meepmeep.trajectories.BlueCloseBasket;
import com.example.meepmeep.trajectories.BlueFarBasket;
import com.example.meepmeep.trajectories.BlueFarPush;
import com.example.meepmeep.trajectories.RedCloseBasket;
import com.example.meepmeep.trajectories.RedFarBasket;
import com.example.meepmeep.trajectories.RedFarPush;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        // BlueCloseBasket trajectories = new BlueCloseBasket(0);
        // BlueFarBasket trajectories = new BlueFarBasket(0);
        // BlueFarPush trajectories = new BlueFarPush(0);
        // RedCloseBasket trajectories = new RedCloseBasket(0);
        // RedFarBasket trajectories = new RedFarBasket(0);
         RedFarPush trajectories = new RedFarPush(0);

        myBot.runAction(trajectories.start(myBot.getDrive().actionBuilder(trajectories.getStart())));

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}