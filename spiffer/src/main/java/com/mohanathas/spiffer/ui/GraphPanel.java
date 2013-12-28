/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.algorithms.DijkstraPathFinder;
import com.mohanathas.spiffer.algorithms.Graph;
import com.mohanathas.spiffer.algorithms.GraphPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
    private GraphPoint mStartPoint = new GraphPoint(0, 0);
    private GraphPoint mEndPoint = new GraphPoint(5, 5);
    private List<GraphPoint> mSolutionPoints = null;

    private static enum DragItem {
        NONE, WALL, UNWALL, START, END
    }

   /**
    * Item that is currently being dragged.
    */
    private DragItem mDragItem = DragItem.NONE;

    public GraphPanel() {
        super.setBackground(Color.WHITE);
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        mGraph.resize(super.getWidth() / BOX_SIZE, super.getHeight() / BOX_SIZE);

        GraphDrawer.drawWallPoints(g, mGraph);
        GraphDrawer.drawVisitedPoints(g, mGraph);
        GraphDrawer.drawStartPoint(g, mStartPoint);
        GraphDrawer.drawEndPoint(g, mEndPoint);
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
        final GraphPoint graphPoint = pointToGraphPoint(e.getPoint());

        // Get rid of the solution line and the visited boxes.
        mSolutionPoints = null;
        mGraph.reset();

        if (mStartPoint.equals(graphPoint)) {
            mDragItem = DragItem.START;
        } else if (mEndPoint.equals(graphPoint)) {
            mDragItem = DragItem.END;
        } else {
            if (mGraph.isWall(graphPoint)) {
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
        final GraphPoint graphPoint = pointToGraphPoint(e.getPoint());
        if (graphPoint.getX() < 0 || graphPoint.getX() >= mGraph.getWidth() ||
            graphPoint.getY() < 0 || graphPoint.getY() >= mGraph.getHeight()) {
            return;
        }

        switch (mDragItem) {
            case WALL:
                if (!mStartPoint.equals(graphPoint) && !mEndPoint.equals(graphPoint)) {
                    mGraph.setWall(graphPoint, true);
                }
                break;

            case UNWALL:
                mGraph.setWall(graphPoint, false);
                break;

            case START:
                if (!mGraph.isWall(graphPoint) && !mEndPoint.equals(graphPoint)) {
                    mStartPoint = graphPoint;
                }
                break;

            case END:
                if (!mGraph.isWall(graphPoint) && !mStartPoint.equals(graphPoint)) {
                    mEndPoint = graphPoint;
                }
                break;
        }

        super.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void findPath() {
        mSolutionPoints = mGraph.findPath(new DijkstraPathFinder(), mStartPoint, mEndPoint);
        repaint();
    }

    public void clearWalls() {
        mSolutionPoints = null;
        mGraph.clearWalls();
        repaint();
    }

    private GraphPoint pointToGraphPoint(Point point) {
        return new GraphPoint((int)point.getX() / BOX_SIZE, (int)point.getY() / BOX_SIZE);
    }
}
