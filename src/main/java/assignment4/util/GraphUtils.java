package assignment4.util;

import org.math.plot.Plot2DPanel;
import weka.classifiers.trees.J48;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GraphUtils {
    private static List<Color> colors = Arrays.asList(Color.BLACK, Color.ORANGE, Color.RED, Color.GREEN,
            Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.DARK_GRAY, Color.PINK);

    public static void plotGraph(Map<String, Map<Double, Double>> resultCollection, String xAxis, String yAxis, double xMax, double yMax) {
        plotGraph(resultCollection, xAxis, yAxis, 0.0, xMax, 0.0, yMax);
    }

    public static void plotGraph(Map<String, Map<Double, Double>> resultCollection, String xAxis, String yAxis, double xMin, double xMax, double yMin, double yMax) {
        Plot2DPanel plot = new Plot2DPanel();
        plot.setAxisLabels(xAxis, yAxis);
        int i = 0;

        for (String key : resultCollection.keySet()) {
            Map<Double, Double> resultMap = resultCollection.get(key);
            double[][] xy = mapToArray(resultMap);
            plot.addLinePlot(key, randomColor(), xy);
            plot.setFixedBounds(0, xMin, xMax);
            plot.setFixedBounds(1, yMin, yMax);
            plot.addLegend("SOUTH");
            i = ++i % colors.size();
        }

        JFrame frame = new JFrame("Graph");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }

    private static double[][] mapToArray(Map<Double, Double> resultMap) {
        double[][] results = new double[resultMap.size()][2];
        int i = 0;
        for (Double key : resultMap.keySet()) {
            results[i][0] = key;
            results[i][1] = resultMap.get(key);
            i++;
        }
        return results;
    }

    private static Color randomColor() {
        Random random = new Random();
        float red = random.nextFloat() / 2f;
        float green = random.nextFloat() / 2f;
        float blue = random.nextFloat() / 2f;
        return new Color(red, green, blue).brighter();
    }
}
