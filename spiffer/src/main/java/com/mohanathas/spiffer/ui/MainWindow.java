/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.util.SettingManager;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Main application window.
 */
public class MainWindow implements Runnable {
    private JFrame mFrame;
    private SettingManager mSettingManager = new SettingManager();

    public MainWindow() {
    }

    @Override
    public void run() {
        mFrame = new JFrame("Spiffer");
        mFrame.setPreferredSize(new Dimension(500, 500));
        mFrame.setResizable(false);
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(mFrame.getContentPane());
        mFrame.pack();

        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.add(new Canvas());
    }
}
