/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer;

import com.mohanathas.spiffer.ui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point.
 *
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame mainFrame = new MainFrame();
            }
        });
    }
}
