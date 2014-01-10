/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.algorithm.Graph;
import com.mohanathas.spiffer.algorithm.PathFinder;
import com.mohanathas.spiffer.util.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 * A panel that allows the user to graphically change the properties of a Graph.
 */
public class GraphPanel extends JPanel implements MouseInputListener, MouseMotionListener {
    static final int BOX_SIZE = 20;

    final Graph mGraph = new Graph(0, 0);
    Point mStartPoint = new Point(0, 0);
    Point mGoalPoint = new Point(5, 5);
    private List<Point> mSolutionPoints = null;

    private static enum DragItem {
        NONE, WALL, UNWALL, START, END
    }

   /**
    * Item that is currently being dragged.
    */
    private DragItem mDragItem = DragItem.NONE;

    public GraphPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mGraph.resize(getWidth() / BOX_SIZE, getHeight() / BOX_SIZE);

        GraphDrawer.drawWallPoints(g, mGraph);
        GraphDrawer.drawVisitedPoints(g, mGraph);
        GraphDrawer.drawStartPoint(g, mStartPoint);
        GraphDrawer.drawGoalPoint(g, mGoalPoint);
        GraphDrawer.drawGrid(g, getWidth(), getHeight());

        if (mSolutionPoints != null) {
            GraphDrawer.drawSolutionLine(g, mStartPoint, mSolutionPoints);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final Point point = panelPointToGraphPoint(e.getPoint());

        // Get rid of the solution line and the visited boxes.
        mSolutionPoints = null;
        mGraph.reset();

        if (mStartPoint.equals(point)) {
            mDragItem = DragItem.START;
        } else if (mGoalPoint.equals(point)) {
            mDragItem = DragItem.END;
        } else {
            if (mGraph.isWall(point)) {
                mDragItem = DragItem.UNWALL;
            } else {
                mDragItem = DragItem.WALL;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Call mouseDragged in order to handle singe clicks.
        mouseDragged(e);

        mDragItem = DragItem.NONE;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        final Point point = panelPointToGraphPoint(e.getPoint());
        if (point.getX() < 0 || point.getX() >= mGraph.getWidth() ||
            point.getY() < 0 || point.getY() >= mGraph.getHeight()) {
            return;
        }

        switch (mDragItem) {
            case WALL:
                if (!mStartPoint.equals(point) && !mGoalPoint.equals(point)) {
                    mGraph.setWall(point, true);
                }
                break;

            case UNWALL:
                mGraph.setWall(point, false);
                break;

            case START:
                if (!mGraph.isWall(point) && !mGoalPoint.equals(point)) {
                    mStartPoint = point;
                }
                break;

            case END:
                if (!mGraph.isWall(point) && !mStartPoint.equals(point)) {
                    mGoalPoint = point;
                }
                break;
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    float findPath(PathFinder pathFinder) {
        mSolutionPoints = mGraph.findPath(pathFinder, mStartPoint, mGoalPoint);
        repaint();
        return mSolutionPoints == null ? 0.0f : Graph.calculatePathLength(mStartPoint, mSolutionPoints);
    }

    void clearWalls() {
        mSolutionPoints = null;
        mGraph.clearWalls();
        mGraph.reset();
        repaint();
    }

    private Point panelPointToGraphPoint(java.awt.Point point) {
        return new Point(point.x / BOX_SIZE, point.y / BOX_SIZE);
    }
}
