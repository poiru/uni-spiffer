/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import com.mohanathas.spiffer.algorithm.AStarPathFinder;
import com.mohanathas.spiffer.algorithm.DijkstraPathFinder;
import com.mohanathas.spiffer.algorithm.Heuristic;
import com.mohanathas.spiffer.algorithm.PathFinder;
import com.mohanathas.spiffer.util.SettingManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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
    private static final String SETTING_FILE_PATH = "spiffer.cfg";
    private final SettingManager mSettingManager = new SettingManager();

    private GraphPanel mGraphPanel;
    private JComboBox mAlgorithmComboBox;
    private JComboBox mHeuristicComboBox;
    private JLabel mLengthLabel;

    public MainFrame() {
        super("Spiffer");
        setIconImage(new ImageIcon(MainFrame.class.getResource("icon.png")).getImage());
        setPreferredSize(new Dimension(600, 500));
        setMinimumSize(new Dimension(600, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(getContentPane());
        pack();

        setJMenuBar(new MainMenuBar(mGraphPanel));
        setLocationRelativeTo(null);

        readSettingFile();
        applySettings();

        // Save the settings when the window is closed.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                updateSettings();
                writeSettingFile();
            }
        });

        setVisible(true);
    }

    /**
     * Creates and adds the components of the UI to the given container.
     *
     * @param container Container to add components to.
     */
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

                final float length = mGraphPanel.findPath(pathFinder);
                mLengthLabel.setText(new DecimalFormat("0.##").format(length));
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
        container.add(buttonPane, BorderLayout.PAGE_START);

        mLengthLabel = new JLabel("0");
        final JPanel statusPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPane.add(new JLabel("Length:"));
        statusPane.add(mLengthLabel);
        container.add(statusPane, BorderLayout.PAGE_END);
    }

    /**
     * Updates the settings to reflect the frame and panels..
     */
    private void updateSettings() {
        // TODO: Store the unmaximized position and size.
        mSettingManager.setSetting("X", getX());
        mSettingManager.setSetting("Y", getY());
        mSettingManager.setSetting("W", getWidth());
        mSettingManager.setSetting("H", getHeight());
        mSettingManager.setSetting("Maximize", (getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0);
    }

    /**
     * Updates the frame and panels to reflect the settings.
     */
    private void applySettings() {
        // TODO: Validate settings.
        if (mSettingManager.getSetting("Maximize", false)) {
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        } else {
            final int x = mSettingManager.getSetting("X", getX());
            final int y = mSettingManager.getSetting("Y", getX());
            final int w = mSettingManager.getSetting("W", getWidth());
            final int h = mSettingManager.getSetting("H", getHeight());
            setBounds(x, y, w, h);
        }
    }

    /**
     * Reads saved settings from the setting file.
     */
    private void readSettingFile() {
        if (Files.exists(Paths.get(SETTING_FILE_PATH))) {
            try {
                mSettingManager.deserialize(new String(
                    Files.readAllBytes(Paths.get(SETTING_FILE_PATH)), StandardCharsets.UTF_8));
            } catch (IOException ex) {
                // Failing to read the setting file is not critical so ignore the exception.
            }
        }
    }

    /**
     * Writes current settings to the setting file..
     */
    private void writeSettingFile() {
        final byte[] data = mSettingManager.serialize().getBytes(Charset.forName("UTF-8"));
        try {
            Files.write(Paths.get(SETTING_FILE_PATH), data);
        } catch (IOException ex) {
            // Failing to save the setting file is not critical so ignore the exception.
        }
    }
}
