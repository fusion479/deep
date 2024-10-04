package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import java.awt.Robot;


public final class Positions {
    public static Vector2d modifyPose(Vector2d pose, double dX, double dY) {
        return new Vector2d(pose.x + dX, pose.y + dY);
    }

    public static Pose2d modifyPose(Pose2d pose, double dX, double dY) {
        return new Pose2d(pose.position.x + dX, pose.position.y + dY, pose.heading.imag);
    }

    public static Pose2d vectorToPose(Vector2d vector, double heading) {
        return new Pose2d(vector.x, vector.y, heading);
    }

    interface RED {
        interface CLOSE_BASKET {
            Pose2d START = new Pose2d(
                    -Constants.TILE_LENGTH * 1.5,
                    -Constants.FIELD_LENGTH + Constants.ROBOT_LENGTH/2,
                    Math.toRadians(90));

            Vector2d SPIKEMARK_SETUP = new Vector2d(
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/2 + 2.5,
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH
                    );

            Pose2d SCORE_SETUP = new Pose2d(
                    -Constants.FIELD_LENGTH + 3 * Constants.TILE_LENGTH/ 4 - 0.4,
                    -Constants.FIELD_LENGTH + 3 * Constants.TILE_LENGTH/ 4,
                    Math.toRadians(225)
            );

            Pose2d SUBMERSIBLE_SETUP = new Pose2d(
                    -1.5 * Constants.TILE_LENGTH,
                    -0.5 * Constants.TILE_LENGTH,
                    Math.toRadians(180)
            );
        }

        interface FAR_BASKET {
            Pose2d START = new Pose2d(
                    Constants.TILE_LENGTH,
                    -Constants.FIELD_LENGTH + Constants.ROBOT_LENGTH/2,
                    Math.toRadians(90));

            Vector2d SPIKEMARK_SETUP = new Vector2d(
                    Constants.FIELD_LENGTH - Constants.TILE_LENGTH/2 - 2.5,
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH
            );

            Pose2d SUBMERSIBLE_SETUP = new Pose2d(
                    1.5 * Constants.TILE_LENGTH,
                    0.5 * Constants.TILE_LENGTH,
                    Math.toRadians(0)
            );
        }

        interface GENERAL {
            Vector2d BASKET = new Vector2d(
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4,
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4
            );

            Pose2d RUNGS = new Pose2d(
                    0,
                    -Constants.TILE_LENGTH * 2 + Constants.ROBOT_LENGTH/2,
                    Math.toRadians(90)
            );

            double SPIKEMARK1_ANGLE = Math.atan(22/10) + Math.PI;
            double SPIKEMARK3_ANGLE = Math.atan(22/-10);
        }
    }

    interface BLUE {
        interface CLOSE_BASKET {
            Pose2d START = new Pose2d(
                    Constants.TILE_LENGTH * 1.5,
                    Constants.FIELD_LENGTH - Constants.ROBOT_LENGTH/2,
                    Math.toRadians(270));

            Vector2d SPIKEMARK_SETUP = new Vector2d(
                    Constants.FIELD_LENGTH - Constants.TILE_LENGTH/2 - 2.5,
                    Constants.FIELD_LENGTH - Constants.TILE_LENGTH
            );

            Pose2d SCORE_SETUP = new Pose2d(
                    Constants.FIELD_LENGTH - 3 * Constants.TILE_LENGTH/ 4 + 0.4,
                    Constants.FIELD_LENGTH - 3 * Constants.TILE_LENGTH/ 4,
                    Math.toRadians(45)
            );

            Pose2d SUBMERSIBLE_SETUP = new Pose2d(
                    1.5 * Constants.TILE_LENGTH,
                    0.5 * Constants.TILE_LENGTH,
                    Math.toRadians(0)
            );
        }

        interface FAR_BASKET {
            Pose2d START = new Pose2d(
                    -Constants.TILE_LENGTH,
                    Constants.FIELD_LENGTH - Constants.ROBOT_LENGTH/2,
                    Math.toRadians(270));

            Vector2d SPIKEMARK_SETUP = new Vector2d(
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/2 + 2.5,
                    Constants.FIELD_LENGTH - Constants.TILE_LENGTH
            );
        }

        interface GENERAL {
            Vector2d BASKET = new Vector2d(
                    Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4,
                    Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4
            );

            Pose2d RUNGS = new Pose2d(
                    0,
                    Constants.TILE_LENGTH * 2 - Constants.ROBOT_LENGTH/2,
                    Math.toRadians(270)
            );

            double SPIKEMARK1_ANGLE = Math.atan(-22/-10);
            double SPIKEMARK3_ANGLE = Math.atan(-22/10) + Math.PI;
        }
    }
}
