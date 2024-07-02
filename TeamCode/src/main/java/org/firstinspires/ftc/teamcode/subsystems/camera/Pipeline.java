package org.firstinspires.ftc.teamcode.subsystems.camera;

import com.acmerobotics.dashboard.config.Config;
import com.example.meepmeep.Trajectories;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class Pipeline extends OpenCvPipeline {
    // H: 0-179, S: 0-255, V: 0-255
    // BLUE COLOR FILTERS
    public static int BLUE_LOW_HUE = 40, BLUE_LOW_SAT = 40, BLUE_LOW_VAL = 0;
    public static int BLUE_HIGH_HUE = 180, BLUE_HIGH_SAT = 255, BLUE_HIGH_VAL = 180;

    // RED COLOR FILTERS
    public static int RED_LOW_HUE = 0, RED_LOW_SAT = 120, RED_LOW_VAL = 0;
    public static int RED_HIGH_HUE = 255, RED_HIGH_SAT = 255, RED_HIGH_VAL = 255;

    // RECT ATTRIBUTES
    public static int RIGHT_RECT_X = 565, RIGHT_RECT_Y = 125;
    public static int LEFT_RECT_X = 0, LEFT_RECT_Y = 125;

    public static int RECT_WIDTH = 75, RECT_HEIGHT = 125;

    // PIPELINE VARIABLES
    public Rect RIGHT_RECT, LEFT_RECT; // Initialize vars during loop to update on dashboard
    public Scalar LOW_FILTER, HIGH_FILTER;
    public static double TOLERANCE = 0.5;

    private final Mat output = new Mat();
    private int region;

    private final Trajectories.Color color;

    public Pipeline(Trajectories.Color color) {
        this.color = color;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

        RIGHT_RECT = new Rect(RIGHT_RECT_X, RIGHT_RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        LEFT_RECT = new Rect(LEFT_RECT_X, LEFT_RECT_Y, RECT_WIDTH, RECT_HEIGHT);

        if (this.color == Trajectories.Color.RED) {
            LOW_FILTER = new Scalar(RED_LOW_HUE, RED_LOW_SAT, RED_LOW_VAL);
            HIGH_FILTER = new Scalar(RED_HIGH_HUE, RED_HIGH_SAT, RED_HIGH_VAL);
        } else {
            LOW_FILTER = new Scalar(BLUE_LOW_HUE, BLUE_LOW_SAT, BLUE_LOW_VAL);
            HIGH_FILTER = new Scalar(BLUE_HIGH_HUE, BLUE_HIGH_SAT, BLUE_HIGH_VAL);
        }

        Core.inRange(output, LOW_FILTER, HIGH_FILTER, output);

        Imgproc.rectangle(output, LEFT_RECT, new Scalar(60, 255, 255), 3);
        Imgproc.rectangle(output, RIGHT_RECT, new Scalar(60, 255, 255), 3);

        if (Core.sumElems(output.submat(LEFT_RECT)).val[0] / LEFT_RECT.area() / 255 > TOLERANCE) {
            Imgproc.rectangle(output, LEFT_RECT, new Scalar(200, 255, 255), 7);
            this.region = 1;
        } else if (Core.sumElems(output.submat(RIGHT_RECT)).val[0] / RIGHT_RECT.area() / 255 > TOLERANCE) {
            Imgproc.rectangle(output, RIGHT_RECT, new Scalar(200, 255, 255), 7);
            this.region = 3;
        } else {
            this.region = 2;
        }

        return output;
    }

    public int getRegion() {
        return region;
    }
}