package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class AutonomousHelpers {

    public enum HeadingInterpolation {
        LINEAR,
        CONSTANT,
        TANGENT
    }

    public static Path buildLine(Pose startPose, Pose endPose, HeadingInterpolation interpolation) {
        Point startPoint = new Point(startPose.getX(), startPose.getY());
        Point endPoint = new Point(endPose.getX(), endPose.getY());

        Path path = new Path(new BezierLine(startPoint, endPoint));
        setHeadingInterpolation(path, startPose.getHeading(), endPose.getHeading(), interpolation);

        return path;
    }

    public static Path buildCurve(Pose startPose, Point controlPoint, Pose endPose,
                                  HeadingInterpolation interpolation) {
        Point startPoint = new Point(startPose.getX(), startPose.getY());
        Point endPoint = new Point(endPose.getX(), endPose.getY());

        Path path = new Path(new BezierCurve(startPoint, controlPoint, endPoint));
        setHeadingInterpolation(path, startPose.getHeading(), endPose.getHeading(), interpolation);

        return path;
    }

    public static Path buildCurve(Pose startPose, Point firstControlPoint, Point secondControlPoint,
                                  Pose endPose, HeadingInterpolation interpolation) {
        Point startPoint = new Point(startPose.getX(), startPose.getY());
        Point endPoint = new Point(endPose.getX(), endPose.getY());

        Path path = new Path(new BezierCurve(startPoint, firstControlPoint, secondControlPoint, endPoint));
        setHeadingInterpolation(path, startPose.getHeading(), endPose.getHeading(), interpolation);

        return path;
    }

    public static ArrayList<Pose> getPoses(String path) {
        try {
            ArrayList<Pose> poses = new ArrayList<Pose>();
            String jsonString = "";

            File file = new File(path);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine())
                jsonString += reader.nextLine();

            JSONObject data = new JSONObject(jsonString);
            poses.add(AutonomousHelpers.getPose(data.getJSONObject("startPoint")));

            JSONArray lines = data.getJSONArray("lines");
            for (int i = 0; i < lines.length(); i++) {
                JSONObject line = lines.getJSONObject(i);
                poses.add(AutonomousHelpers.getPose(line.getJSONObject("endPoint")));

                JSONArray controlPoints = line.getJSONArray("controlPoints");
                for (int j = 0; j < controlPoints.length(); j++) {
                    poses.add(AutonomousHelpers.getPose(controlPoints.getJSONObject(i)));
                }
            }

            return poses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Pose> getPoses(String path, MultipleTelemetry telemetry) {
        try {
            ArrayList<Pose> poses = new ArrayList<Pose>();
            String jsonString = "";

            File file = new File(new File("").getAbsolutePath().concat("/src/main/java/org/example/test.pp"));
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine())
                jsonString += reader.nextLine();

            JSONObject data = new JSONObject(jsonString);
            poses.add(AutonomousHelpers.getPose(data.getJSONObject("startPoint")));

            JSONArray lines = data.getJSONArray("lines");
            for (int i = 0; i < lines.length(); i++) {
                JSONObject line = lines.getJSONObject(i);
                poses.add(AutonomousHelpers.getPose(line.getJSONObject("endPoint")));

                JSONArray controlPoints = line.getJSONArray("controlPoints");
                for (int j = 0; j < controlPoints.length(); j++) {
                    poses.add(AutonomousHelpers.getPose(controlPoints.getJSONObject(i)));
                }
            }

            return poses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Point poseToPoint(Pose pose) {
        return new Point(pose.getX(), pose.getY());
    }

    private static Pose getPose(JSONObject object) throws JSONException {
        return new Pose(
                object.getDouble("x"),
                object.getDouble("y"),
                object.has("heading") ? object.getDouble("heading") : 0.0
        );
    }

    private static void setHeadingInterpolation(Path path, double startHeading, double endHeading,
                                                HeadingInterpolation interpolation) {
        switch (interpolation) {
            case LINEAR:
                path.setLinearHeadingInterpolation(startHeading, endHeading);
                break;
            case CONSTANT:
                path.setConstantHeadingInterpolation(startHeading);
                break;
            case TANGENT:
                path.setTangentHeadingInterpolation();
                break;
        }
    }
}