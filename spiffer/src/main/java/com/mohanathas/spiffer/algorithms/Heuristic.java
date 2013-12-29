/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.algorithms;

/**
 * Implements various heuristic distance functions.
 */
public enum Heuristic {
    Chebyshev,
    Euclidean,
    Manhattan,
    Zero;

    public double distance(int dx, int dy) {
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        switch (this) {
            case Chebyshev: return Math.max(dx, dy);
            case Euclidean: return Math.sqrt(dx * dx + dy * dy);
            case Manhattan: return dx + dy;
        }

        return 0.0;
    }
}
