/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nebula2d.editor.ui;

import com.nebula2d.editor.framework.Project;
import com.nebula2d.editor.framework.Scene;
import com.nebula2d.editor.ui.controls.N2DLabel;
import com.nebula2d.editor.ui.controls.N2DPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class NewSceneDialog extends JDialog {

    private JTextField nameTf;

    public NewSceneDialog() {
        setTitle("New Scene");
        setupContents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupContents() {
        nameTf = new JTextField(20);

        final Project project = MainFrame.getProject();
        final Color defaultFg = nameTf.getForeground();
        final N2DLabel nameLbl = new N2DLabel("Scene Name: ");
        final JButton okBtn = new JButton("Ok");
        final JButton cancelBtn = new JButton("Cancel");

        final N2DPanel namePanel = new N2DPanel();
        final N2DPanel btnPanel = new N2DPanel();

        nameTf.setText("Untitled Scene " + project.getScenes().size());

        namePanel.add(nameLbl);
        namePanel.add(nameTf);

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        nameTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!validateText()) {
                    nameTf.setForeground(Color.RED);
                    okBtn.setEnabled(false);
                } else {
                    nameTf.setForeground(defaultFg);
                    okBtn.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        okBtn.addActionListener(e -> {
            String newSceneName = nameTf.getText();
            Scene scene = new Scene(newSceneName);
            SceneGraph sceneGraph = MainFrame.getSceneGraph();
            Project project1 = MainFrame.getProject();
            project1.addScene(scene);
            project1.setCurrentScene(newSceneName);
            sceneGraph.init();
            project1.loadCurrentScene();
            sceneGraph.refresh();
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        add(btnPanel, BorderLayout.SOUTH);
        add(namePanel, BorderLayout.CENTER);
        pack();
    }

    private boolean validateText() {
        String txt = nameTf.getText();
        Project project = MainFrame.getProject();
        return !txt.isEmpty() && !project.containsSceneWithName(txt);
    }
}
