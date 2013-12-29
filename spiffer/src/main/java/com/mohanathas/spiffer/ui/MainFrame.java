/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.algorithms.AStarPathFinder;
import com.mohanathas.spiffer.algorithms.DijkstraPathFinder;
import com.mohanathas.spiffer.algorithms.Heuristic;
import com.mohanathas.spiffer.algorithms.PathFinder;
import com.mohanathas.spiffer.util.SettingManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Main application window.
 */
public class MainFrame extends JFrame {
    private GraphPanel mGraphPanel;
    private JComboBox mAlgorithmComboBox;
    private JComboBox mHeuristicComboBox;

    private SettingManager mSettingManager = new SettingManager();

    public MainFrame() {
        super("Spiffer");
        super.setIconImage(new ImageIcon(MainFrame.class.getResource("icon.png")).getImage());
        super.setPreferredSize(new Dimension(600, 500));
        super.setMinimumSize(new Dimension(600, 300));
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(super.getContentPane());
        super.pack();

        super.setJMenuBar(new MainMenuBar(mGraphPanel));

        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    private void createComponents(Container container) {
        mGraphPanel = new GraphPanel();
        container.add(mGraphPanel, BorderLayout.CENTER);

        final JButton solveButton = new JButton("Find Path");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String algorithmString = (String)mAlgorithmComboBox.getSelectedItem();
                final String heuristicString = (String)mHeuristicComboBox.getSelectedItem();
                Heuristic heuristic = null;
                switch (heuristicString) {
                    case "Chebyshev": heuristic = Heuristic.Chebyshev; break;
                    case "Euclidean": heuristic = Heuristic.Euclidean; break;
                    case "Manhattan": heuristic = Heuristic.Manhattan; break;
                }

                PathFinder pathFinder = null;
                switch (algorithmString) {
                    case "Dijkstra": pathFinder = new DijkstraPathFinder(); break;
                    case "A*":       pathFinder = new AStarPathFinder(heuristic); break;
                }
                mGraphPanel.findPath(pathFinder);
            }
        });

        final JButton clearButton = new JButton("Clear Walls");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGraphPanel.clearWalls();
            }
        });

        mAlgorithmComboBox = new JComboBox(new String[] { "Dijkstra", "A*" });
        mAlgorithmComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    mHeuristicComboBox.setVisible(!e.getItem().equals("Dijkstra"));
                }
            }
        });

        mHeuristicComboBox = new JComboBox(new String[] { "Chebyshev", "Euclidean", "Manhattan" });
        mHeuristicComboBox.setVisible(false);
        
        final JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonPane.add(solveButton);
        buttonPane.add(clearButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(new JLabel("Algorithm: "));
        buttonPane.add(mAlgorithmComboBox);
        buttonPane.add(mHeuristicComboBox);
        buttonPane.add(Box.createHorizontalGlue());

        container.add(buttonPane, BorderLayout.PAGE_START);
    }
}
