/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.algorithm.Graph;
import com.mohanathas.spiffer.util.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Provides methods to draw a Graph object to the given Graphics instance.
 */
class GraphDrawer {
    private static final Color WALL_COLOR = new Color(170, 170, 170);
    private static final Color VISITED_COLOR = new Color(227, 255, 227);
    private static final Color START_COLOR = new Color(94, 185, 94);
    private static final Color END_COLOR = new Color(243, 123, 29);
    private static final Color GRID_COLOR = new Color(220, 220, 220);
    private static final Color SOLUTION_COLOR = new Color(250, 210, 50);

    private static void fillBox(Graphics g, int x, int y) {
        g.fillRect(
                x * GraphPanel.BOX_SIZE + 1,
                y * GraphPanel.BOX_SIZE + 1,
                GraphPanel.BOX_SIZE - 1,
                GraphPanel.BOX_SIZE - 1);
    }

    public static void drawWallPoints(Graphics g, Graph graph) {
        g.setColor(WALL_COLOR);
        for (int y = 0; y < graph.getHeight(); ++y) {
            for (int x = 0; x < graph.getWidth(); ++x) {
                if (graph.isWall(new Point(x, y))) {
                    fillBox(g, x, y);
                }
            }
        }
    }

    public static void drawVisitedPoints(Graphics g, Graph graph) {
        g.setColor(VISITED_COLOR);
        for (int y = 0; y < graph.getHeight(); ++y) {
            for (int x = 0; x < graph.getWidth(); ++x) {
                if (graph.isVisited(new Point(x, y))) {
                    fillBox(g, x, y);
                }
            }
        }
    }

    public static void drawStartPoint(Graphics g, Point point) {
        g.setColor(START_COLOR);
        fillBox(g, point.getX(), point.getY());
    }

    public static void drawEndPoint(Graphics g, Point point) {
        g.setColor(END_COLOR);
        fillBox(g, point.getX(), point.getY());
    }

    public static void drawGrid(Graphics g, int w, int h) {
        // Fill partial boxes on the right and bottom with the wall color.
        g.setColor(WALL_COLOR);
        g.fillRect(w - (w % GraphPanel.BOX_SIZE), 0, w, h);
        g.fillRect(0, h - (h % GraphPanel.BOX_SIZE), w, h);

        g.setColor(GRID_COLOR);
        for (int x = 0; x < w; x += GraphPanel.BOX_SIZE) {
            g.drawLine(x, 0, x, h);
        }
        for (int y = 0; y < h; y += GraphPanel.BOX_SIZE) {
            g.drawLine(0, y, w, y);
        }
    }

    public static void drawSolutionLine(Graphics g, Point startPoint, List<Point> solutionPoints) {
        g.setColor(SOLUTION_COLOR);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(4));
        Point prevPoint = startPoint;
        for (Point point : solutionPoints) {
            g.drawLine(
                    prevPoint.getX() * GraphPanel.BOX_SIZE + (GraphPanel.BOX_SIZE / 2),
                    prevPoint.getY() * GraphPanel.BOX_SIZE + (GraphPanel.BOX_SIZE / 2),
                    point.getX() * GraphPanel.BOX_SIZE + (GraphPanel.BOX_SIZE / 2),
                    point.getY() * GraphPanel.BOX_SIZE + (GraphPanel.BOX_SIZE / 2));
            prevPoint = point;
        }
    }

}
