package com.knubisoft.utils;

import com.knubisoft.Point;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProcessingUtils {

    /**
     * Generates result using all methods.
     *
     * @param input1 First image.
     * @param input2 Second image.
     * @return Processed image.
     */
    public BufferedImage generateResultImage(BufferedImage input1, BufferedImage input2) {
        List<Point> points = new ArrayList<>();
        List<List<Point>> groups = new ArrayList<>();

        findPoints(input1, input2, points);
        buildGroup(points, groups);
        buildRectangle(input2, groups);

        return input2;
    }

    /**
     * Finds different points.
     *
     * @param input1 First image.
     * @param input2 Second image.
     * @param points List of points.
     */
    private void findPoints(BufferedImage input1, BufferedImage input2, List<Point> points) {
        int w1 = input1.getWidth();
        int h1 = input1.getHeight();

        for (int j = 0; j < h1; j++) {
            for (int i = 0; i < w1; i++) {
                int pixel1 = input1.getRGB(i, j);
                int pixel2 = input2.getRGB(i, j);

                if (pixel1 != pixel2) {
                    points.add(new Point(i, j));
                }
            }
        }
    }

    /**
     * Builds group by points distance.
     *
     * @param points List of points.
     * @param groups List of groups.
     */
    private void buildGroup(List<Point> points, List<List<Point>> groups) {
        Comparator<Point> comparator = Comparator.comparingInt(Point::getX);
        points.sort(comparator);

        while (!points.isEmpty()) {
            List<Point> group = new ArrayList<>();
            Point firstpoint = points.get(0);

            for (Point p : points) {
                if (calculateDistance(firstpoint.getX(), firstpoint.getY(), p.getX(), p.getY()) < 10) {
                    group.add(p);
                }
            }
            points.removeAll(group);
            mergeGroups(groups, group);
        }
    }

    /**
     * Calculates distance between different points.
     *
     * @param x X of first point.
     * @param y Y of first point.
     * @param x1 X of second point.
     * @param y1 Y of second point.
     * @return Distance.
     */
    private double calculateDistance(int x, int y, int x1, int y1) {
        return Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
    }

    /**
     * Merges groups.
     *
     * @param groups List of groups.
     * @param group Group.
     */
    private void mergeGroups(List<List<Point>> groups, List<Point> group) {
        boolean flag = false;

        for (List<Point> list : groups) {
            for(Point fmp : group) {
                for (Point p : list) {
                    if (calculateDistance(fmp.getX(), fmp.getY(), p.getX(), p.getY()) < 30) {
                        flag = true;
                        list.addAll(group);
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        if (!flag) {
            groups.add(group);
        }
    }

    /**
     * Builds rectangle of groups.
     *
     * @param input2 Image to process.
     * @param groups List of groups.
     */
    private void buildRectangle(BufferedImage input2, List<List<Point>> groups) {
        for (List<Point> list : groups) {
            int mostLeft = Integer.MAX_VALUE;
            int mostTop = Integer.MAX_VALUE;
            int mostBottom = 0;
            int mostRight = 0;

            for (Point p : list) {
                mostLeft = Math.min(p.getX(), mostLeft);
                mostTop = Math.min(p.getY(), mostTop);
                mostRight = Math.max(p.getX(), mostRight);
                mostBottom = Math.max(p.getY(), mostBottom);
            }

            highlightRectangle(input2, mostLeft, mostTop, mostBottom, mostRight);
        }
    }

    /**
     * Highlights rectangle with red line.
     *
     * @param input2 Image to highlight.
     * @param mostLeftPoint Extreme left point.
     * @param mostTop Extreme top point.
     * @param mostBottom Extreme bottom point.
     * @param mostRightPoint Extreme right point.
     */
    private void highlightRectangle(BufferedImage input2,
                                    int mostLeftPoint,
                                    int mostTop,
                                    int mostBottom,
                                    int mostRightPoint) {

        int width = mostRightPoint - mostLeftPoint;
        int height = mostBottom - mostTop;

        Graphics2D g2d = input2.createGraphics();

        g2d.setColor(Color.RED);
        g2d.drawRect(mostLeftPoint - 5, mostTop - 5, width + 10, height + 10);
        g2d.dispose();
    }

}
