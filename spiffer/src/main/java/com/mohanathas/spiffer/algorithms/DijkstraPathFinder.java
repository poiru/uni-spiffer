/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

import com.mohanathas.spiffer.util.Point;
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
    public List<Point> findPath(Graph graph, Node startNode, Node endNode) {
        final PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.add(startNode);

        do {
            final Node node = heap.poll();
            if (node.equals(endNode)){
                return node.getParentPoints();
            }

            relax(heap, node, graph.getNode(node.getX() - 1, node.getY()));
            relax(heap, node, graph.getNode(node.getX() + 1, node.getY()));
            relax(heap, node, graph.getNode(node.getX(), node.getY() - 1));
            relax(heap, node, graph.getNode(node.getX(), node.getY() + 1));

            node.markVisited();
        } while (!heap.isEmpty());

        return null;
    }

    private void relax(PriorityQueue<Node> heap, Node node, Node neighborNode) {
        if (neighborNode != null && !neighborNode.isWall() && !neighborNode.isVisited()) {
            neighborNode.setWeight(node.getWeight() + 1);
            neighborNode.setParent(node);
            heap.add(neighborNode);
        }
    }
}
