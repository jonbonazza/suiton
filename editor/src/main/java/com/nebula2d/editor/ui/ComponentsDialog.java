/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
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

import com.nebula2d.editor.framework.GameObject;
import com.nebula2d.editor.framework.components.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for manipulating GameObject components
 */
public class ComponentsDialog extends JDialog {

    private JPanel rightPanel;
    private GameObject gameObject;
    private JPanel mainPanel;
    private JList<Component> componentList;

    public ComponentsDialog(GameObject gameObject) {
        this.gameObject = gameObject;

        JPanel componentListPanel = forgeComponentsListPanel();
        rightPanel = forgeEmptyPanel();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(componentListPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel);
        add(mainPanel);
        setSize(new Dimension(800, 600));
        setResizable(false);
        setVisible(true);
    }

    public JList<Component> getComponentList() {
        return componentList;
    }

    private JPanel forgeEmptyPanel() {
        JPanel panel = new JPanel();
        JLabel emptyLbl = new JLabel("No component currently selected.");
        panel.add(emptyLbl);

        return panel;
    }

    private JPanel forgeComponentsListPanel() {
        componentList = new JList<Component>();
        final DefaultListModel<Component> model = new DefaultListModel<Component>();
        componentList.setModel(model);

        populateComponentList(componentList);

        JScrollPane sp = new JScrollPane(componentList);
        sp.setPreferredSize(new Dimension(200, 400));

        final JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewComponentPopup(gameObject, componentList).show(addButton, -1, addButton.getHeight());
            }
        });
        final JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component selectedComponent = componentList.getSelectedValue();
                gameObject.removeComponent(selectedComponent);
                ((DefaultListModel<Component>) componentList.getModel()).removeElement(selectedComponent);
                mainPanel.remove(rightPanel);
                mainPanel.add(forgeEmptyPanel());
                revalidate();
            }
        });
        removeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        componentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                    return;

                Component component = componentList.getSelectedValue();

                removeButton.setEnabled(component != null);

                if (component != null) {
                    mainPanel.remove(rightPanel);
                    rightPanel = component.forgeComponentContentPanel(ComponentsDialog.this);
                    mainPanel.add(rightPanel);
                    revalidate();
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(sp);

        return mainPanel;
    }

    private void populateComponentList(JList<Component> listBox) {
        DefaultListModel<Component> model = (DefaultListModel<Component>) listBox.getModel();
        for (Component component : gameObject.getComponents()) {
            model.addElement(component);
        }
    }

}