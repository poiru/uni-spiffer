/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.util.SettingManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Main application window.
 */
public class MainFrame extends JFrame {
    private GraphPanel mGraphPanel;
    private JButton mSolveButton;

    private SettingManager mSettingManager = new SettingManager();

    public MainFrame() {
        super("Spiffer");
        super.setPreferredSize(new Dimension(497, 500));
        super.setMinimumSize(new Dimension(297, 300));

        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(super.getContentPane());
        super.pack();

        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    private void createComponents(Container container) {
        mGraphPanel = new GraphPanel();
        mSolveButton = new JButton("Solve");
        mSolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGraphPanel.solve();
            }
        });

        container.add(mGraphPanel, BorderLayout.CENTER);

        final JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        buttonPane.add(mSolveButton);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(new JLabel("..."));

        container.add(buttonPane, BorderLayout.PAGE_START);
    }
}
