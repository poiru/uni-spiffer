/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Canvas is a drawable panel.
 */
public class Canvas extends JPanel {
    public Canvas() {
        super.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
