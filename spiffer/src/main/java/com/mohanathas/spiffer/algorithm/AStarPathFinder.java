/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithm;

import com.mohanathas.spiffer.util.BinaryMinHeap;
import com.mohanathas.spiffer.util.Point;
import java.util.List;

/**
 * PathFinder implementation of the A* algorithm.
 */
public class AStarPathFinder implements PathFinder {
    private final Heuristic mHeuristic;

    public AStarPathFinder(Heuristic heuristic) {
        mHeuristic = heuristic;
    }

    @Override
    public List<Point> findPath(Graph graph, Point startPoint, Point goalPoint) {
        final Node startNode = graph.getNode(startPoint);
        final Node goalNode = graph.getNode(goalPoint);
        final BinaryMinHeap<Node> heap = new BinaryMinHeap<>();
        startNode.setStartDistance(0.0f);
        heap.add(startNode);
        startNode.setQueued();

        do {
            final Node node = heap.poll();
            node.setProcessed();
            if (node.equals(goalNode)) {
                return node.getParentPoints();
            }

            handleNeighbors(graph, heap, node, goalNode);
        } while (!heap.isEmpty());

        return null;
    }

    void handleNeighbors(Graph graph, BinaryMinHeap<Node> heap, Node node, Node goalNode) {
        for (final Node neighbor : graph.findNodeNeighbors(node)) {
            relax(heap, node, neighbor, goalNode);
        }
    }

    void relax(BinaryMinHeap<Node> heap, Node node, Node neighbor, Node goalNode) {
        if (neighbor.isProcessed()) {
            return;
        }

        final float distance = node.getPoint().distanceTo(neighbor.getPoint());
        if (neighbor.getStartDistance() > node.getStartDistance() + distance) {
            neighbor.setParent(node);
            neighbor.setStartDistance(node.getStartDistance() + distance);

            if (neighbor.isQueued()) {
                heap.reorder(neighbor);
            } else {
                final int dx = neighbor.getX() - goalNode.getX();
                final int dy = neighbor.getY() - goalNode.getY();
                neighbor.setGoalDistance(mHeuristic.distance(dx, dy));

                heap.add(neighbor);
                neighbor.setQueued();
            }
        }
    }
}
