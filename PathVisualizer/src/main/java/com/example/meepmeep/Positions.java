package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;


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
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/2,
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH
                    );

        }

        interface FAR_BASKET {

        }

        interface GENERAL {
            Vector2d BASKET = new Vector2d(
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4,
                    -Constants.FIELD_LENGTH + Constants.TILE_LENGTH/4
            );
        }
    }

    interface BLUE {
        interface CLOSE_BASKET {

        }

        interface FAR_BASKET {

        }
    }
}
