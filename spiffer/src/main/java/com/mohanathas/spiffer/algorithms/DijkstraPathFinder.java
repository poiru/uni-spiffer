/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import java.util.List;
import java.util.PriorityQueue;

/**
 * PathFinder implementation for Dijkstra's algorithm.
 */
public class DijkstraPathFinder implements PathFinder {
    public DijkstraPathFinder() {
    }

    /**
     * Finds a list of Points connecting the Nodes at |startPos| and |endPos|
     * using the Dijkstra's algorithm.
     */
    @Override
    public List<GraphPoint> findPath(Graph graph, Node startNode, Node endNode) {
        final PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.add(startNode);
        startNode.setDistance(0.0);

        do {
            final Node node = heap.poll();
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

                    final boolean diagonal = x != 0 && y != 0;
                    final Node neighborNode = graph.getNode(node.getX() + x, node.getY() + y);
                    if (neighborNode == null || neighborNode.isWall() || neighborNode.isVisited()) {
                        continue;
                    }

                    // Disallow diagonal movement if the shared neighbours of the node and the
                    // neighbour node are both walls.
                    if (diagonal &&
                        graph.getNode(node.getX(), node.getY() + y).isWall() &&
                        graph.getNode(node.getX() + x, node.getY()).isWall()) {
                        continue;
                    }

                    relax(heap, node, neighborNode, diagonal ? Math.sqrt(2.0) : 1.0);
                }
            }

            node.markVisited();
        } while (!heap.isEmpty());

        return null;
    }

    private void relax(PriorityQueue<Node> heap, Node node, Node neighborNode, double distance) {
        if (neighborNode.getDistance() > node.getDistance() + distance) {
            neighborNode.setDistance(node.getDistance() + distance);
            neighborNode.setParent(node);
            heap.add(neighborNode);
        }
    }
}
