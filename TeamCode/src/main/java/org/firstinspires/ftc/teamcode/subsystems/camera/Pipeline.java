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
    // BLUE COLOR FILTERS
    public static final int BLUE_LOW_RED = 40, BLUE_LOW_GREEN = 40, BLUE_LOW_BLUE = 0;
    public static final int BLUE_HIGH_RED = 180, BLUE_HIGH_GREEN = 255, BLUE_HIGH_BLUE = 180;

    // RED COLOR FILTERS
    public static final int RED_LOW_RED = 0, RED_LOW_GREEN = 120, RED_LOW_BLUE = 0;
    public static final int RED_HIGH_RED = 255, RED_HIGH_GREEN = 255, RED_HIGH_BLUE = 255;

    // RECT ATTRIBUTES
    public static final int RIGHT_RECT_X = 565, RIGHT_RECT_Y = 125;
    public static final int LEFT_RECT_X = 0, LEFT_RECT_Y = 125;

    public static final int RECT_WIDTH = 75, RECT_HEIGHT = 125;

    // PIPELINE VARIABLES
    public static final double TOLERANCE = 0.3;

    private static final Rect RIGHT_RECT = new Rect(RIGHT_RECT_X, RIGHT_RECT_Y, RECT_WIDTH, RECT_HEIGHT), LEFT_RECT = new Rect(LEFT_RECT_X, LEFT_RECT_Y, RECT_WIDTH, RECT_HEIGHT);
    private static final Mat output = new Mat();

    private final Scalar LOW_FILTER, HIGH_FILTER;
    private int region;

    public Pipeline(Trajectories.Color color) {
        if (color == Trajectories.Color.RED) {
            LOW_FILTER = new Scalar(RED_LOW_RED, RED_LOW_GREEN, RED_LOW_BLUE);
            HIGH_FILTER = new Scalar(RED_HIGH_RED, RED_HIGH_GREEN, RED_HIGH_BLUE);
        } else {
            LOW_FILTER = new Scalar(BLUE_LOW_RED, BLUE_LOW_GREEN, BLUE_LOW_BLUE);
            HIGH_FILTER = new Scalar(BLUE_HIGH_RED, BLUE_HIGH_GREEN, BLUE_HIGH_BLUE);
        }
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, output, Imgproc.COLOR_RGB2HSV);

        Core.inRange(output, LOW_FILTER, HIGH_FILTER, output);

        Mat rightBox = output.submat(RIGHT_RECT);
        Mat leftBox = output.submat(LEFT_RECT);

        Imgproc.rectangle(output, LEFT_RECT, new Scalar(60, 255, 255), 3);
        Imgproc.rectangle(output, RIGHT_RECT, new Scalar(60, 255, 255), 3);

        if (Core.sumElems(leftBox).val[0] / LEFT_RECT.area() > TOLERANCE) {
            Imgproc.rectangle(output, LEFT_RECT, new Scalar(0, 255, 255), 7);
            this.region = 1;
        } else if (Core.sumElems(rightBox).val[0] / RIGHT_RECT.area() > TOLERANCE) {
            Imgproc.rectangle(output, RIGHT_RECT, new Scalar(255, 255, 255), 7);
            this.region = 3;
        } else {
            this.region = 2;
        }

        rightBox.release();
        leftBox.release();

        return output;
    }

    public int getRegion() {
        return region;
    }
}