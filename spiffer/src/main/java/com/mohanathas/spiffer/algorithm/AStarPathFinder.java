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

    /**
     * Finds a list of Points connecting |startNode| and |endNode|.
     */
    @Override
    public List<Point> findPath(Graph graph, Node startNode, Node endNode) {
        final BinaryMinHeap<Node> heap = new BinaryMinHeap<>();
        heap.add(startNode);
        startNode.setStartDistance(0.0f);

        do {
            final Node node = heap.pop();
            if (node.equals(endNode)) {
                return node.getParentPoints();
            }

            if (node.isVisited()) {
                continue;
            }

            for (final Node neighbor : graph.findNodeNeighbors(node)) {
                relax(heap, node, neighbor, endNode);
            }

            node.markVisited();
        } while (!heap.isEmpty());

        return null;
    }

    private void relax(final BinaryMinHeap<Node> heap, final Node node, final Node neighbor,
            final Node endNode) {
        final float distance = node.getPoint().distanceTo(neighbor.getPoint());
        if (neighbor.getStartDistance() > node.getStartDistance() + distance) {
            neighbor.setParent(node);
            neighbor.setStartDistance(node.getStartDistance() + distance);

            final int dx = neighbor.getX() - endNode.getX();
            final int dy = neighbor.getY() - endNode.getY();
            neighbor.setGoalDistance(mHeuristic.distance(dx, dy));

            heap.add(neighbor);
        }
    }
}
