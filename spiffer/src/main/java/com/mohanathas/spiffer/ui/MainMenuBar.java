/*
 * Copyright (C) 2013 Birunthan Mohanathas
 *
 * Licensed under the MIT license <http://opensource.org/licenses/MIT>. This
 * file may not be copied, modified, or distributed except according to those
 * terms.
 */

package com.mohanathas.spiffer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Menubar on the top of the main application window.
 */
public class MainMenuBar extends JMenuBar {
    public MainMenuBar(final GraphPanel graphPanel) {
        add(createFileMenu(graphPanel));
    }

    /**
     * Creates the File menu.
     *
     * @param graphPanel An instance of GraphPanel to associate the menu with.
     * @return The created menu.
     */
    private JMenu createFileMenu(final GraphPanel graphPanel) {
        final JMenuItem fileOpen = new JMenuItem("Open...");
        fileOpen.setMnemonic(KeyEvent.VK_O);
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(fileOpen) == JFileChooser.APPROVE_OPTION) {
                    try {
                        graphPanel.mGraph.deserialize(
                            new String(Files.readAllBytes(
                                Paths.get(fc.getSelectedFile().getPath())), StandardCharsets.UTF_8),
                            graphPanel.mStartPoint,
                            graphPanel.mGoalPoint);
                        graphPanel.repaint();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                            null, "Error reading from:\n" + fc.getSelectedFile().getPath(),
                            "Spiffer", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        final JMenuItem fileSave = new JMenuItem("Save As...");
        fileSave.setMnemonic(KeyEvent.VK_S);
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(fileSave) == JFileChooser.APPROVE_OPTION) {
                    try (FileWriter writer = new FileWriter(fc.getSelectedFile())) {
                        writer.append(graphPanel.mGraph.serialize(
                            graphPanel.mStartPoint, graphPanel.mGoalPoint));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                            null, "Error writing to:\n" + fc.getSelectedFile().getPath(),
                            "Spiffer", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        final JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.add(fileOpen);
        menu.add(fileSave);
        return menu;
    }
}
