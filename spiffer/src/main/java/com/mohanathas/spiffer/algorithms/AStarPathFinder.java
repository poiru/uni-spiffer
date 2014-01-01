/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import com.mohanathas.spiffer.util.BinaryMinHeap;
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
    public List<GraphPoint> findPath(Graph graph, Node startNode, Node endNode) {
        final BinaryMinHeap<Node> heap = new BinaryMinHeap<>();
        heap.add(startNode);
        startNode.setStartDistance(0.0);

        do {
            final Node node = heap.pop();
            if (node.equals(endNode)) {
                return node.getParentPoints();
            }

            if (node.isVisited()) {
                continue;
            }

            for (int y = -1; y <= 1; ++y) {
                for (int x = -1; x <= 1; ++x) {
                    if (y == 0 && x == 0) {
                        continue;
                    }

                    final Node neighborNode = graph.getNode(node.getX() + x, node.getY() + y);
                    if (neighborNode == null || neighborNode.isWall() || neighborNode.isVisited()) {
                        continue;
                    }

                    // Disallow diagonal movement if the shared neighbors of the node and the
                    // neighbor node are both walls.
                    final boolean diagonal = x != 0 && y != 0;
                    if (diagonal &&
                        graph.getNode(node.getX(), node.getY() + y).isWall() &&
                        graph.getNode(node.getX() + x, node.getY()).isWall()) {
                        continue;
                    }

                    final double distance = diagonal ? Math.sqrt(2.0) : 1.0;
                    if (neighborNode.getStartDistance() > node.getStartDistance() + distance) {
                        neighborNode.setParent(node);
                        neighborNode.setStartDistance(node.getStartDistance() + distance);

                        final int dx = neighborNode.getX() - endNode.getX();
                        final int dy = neighborNode.getY() - endNode.getY();
                        neighborNode.setGoalDistance(mHeuristic.distance(dx, dy));

                        heap.add(neighborNode);
                    }
                }
            }

            node.markVisited();
        } while (!heap.isEmpty());

        return null;
    }
}
