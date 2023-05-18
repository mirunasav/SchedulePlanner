package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Customize the font size
            Font font = component.getFont();
            component.setFont(font.deriveFont(font.getSize() + 5.0f)); // Increase the font size by 4 points

            if (component instanceof JLabel) {
                ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
            }

            return component;
        }
    }
    public ToDoListApp() {
        setTitle("To-Do List App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the list model and JList
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new CustomListCellRenderer());

        // Create the scroll pane for the task list
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        addButton(buttonPanel, "Add Task", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = JOptionPane.showInputDialog(ToDoListApp.this, "Enter task:");
                if (task != null && !task.isEmpty()) {
                    taskListModel.addElement(task);
                }
            }
        });

        addButton(buttonPanel, "Delete Task", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                }
            }
        });

        addButton(buttonPanel, "Generate Schedule", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement schedule generation logic
                JOptionPane.showMessageDialog(ToDoListApp.this, "Schedule generated!");
            }
        });

        addButton(buttonPanel, "Logout", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement logout logic
                JOptionPane.showMessageDialog(ToDoListApp.this, "Logged out!");
            }
        });

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.EAST);

        // Add component listener to handle frame size changes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int buttonPanelWidth = getWidth() / 3;
                buttonPanel.setPreferredSize(new Dimension(buttonPanelWidth, getHeight()));
                revalidate();
            }
        });

        // Set the frame size and make it visible
        setSize(900, 700);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 0, 5, 0);

        panel.add(button, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListApp();
            }
        });
    }
}
