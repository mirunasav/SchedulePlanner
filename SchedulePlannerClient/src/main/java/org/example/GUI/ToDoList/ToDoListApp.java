package org.example.GUI.ToDoList;

import org.example.GUI.Login.LoginPage;
import org.example.GUI.utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

    public DefaultListModel<String> getTaskListModel() {
        return taskListModel;
    }

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

    private class AddTaskButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create the dialog components
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            JTextField taskNameField = new JTextField(15);
            JSpinner hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
            JSpinner minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
            JFormattedTextField startTimeField = new JFormattedTextField(createTimeFormatter());
            JFormattedTextField endTimeField = new JFormattedTextField(createTimeFormatter());
            endTimeField.setPreferredSize(new Dimension(50, 20));
            startTimeField.setPreferredSize(new Dimension(50, 20));

            // Add the components to the panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Task Name:"), gbc);
            gbc.gridx = 1;
            gbc.gridwidth = 3; // Set grid width for the task name field
            panel.add(taskNameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // Reset grid width
            panel.add(new JLabel("Duration:"), gbc);
            gbc.gridx = 1;
            panel.add(hourSpinner, gbc);
            gbc.gridx = 2;
            panel.add(new JLabel("hours"), gbc);
            gbc.gridx = 3;
            panel.add(minuteSpinner, gbc);
            gbc.gridx = 4;
            panel.add(new JLabel("minutes"), gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("Start Time:"), gbc);
            gbc.gridx = 1;
            gbc.gridwidth = 3; // Set grid width for the start time field
            panel.add(startTimeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1; // Reset grid width
            panel.add(new JLabel("End Time:"), gbc);
            gbc.gridx = 1;
            gbc.gridwidth = 3; // Set grid width for the end time field
            panel.add(endTimeField, gbc);

            // Create the dialog
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                // Get the selected values from the dialog components
                String taskName = taskNameField.getText();
                int hours = (int) hourSpinner.getValue();
                int minutes = (int) minuteSpinner.getValue();
                int totalMinutesDuration = hours * 60 + minutes;

                String startTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");//make this a constant somewhere
                //store the tasks in a list of tasks since i am already making them into objects
                //so that in generateSchedule, I don't need to parse the tasks anymore
                //to the taskListModel i can add task.toString();

                try {
                    Utils.validateTask(new Task(taskName, totalMinutesDuration, timeFormat.parse(startTime), timeFormat.parse(endTime)));
                    // Add the task to the list
                    String taskDetails = taskName + " (Duration: " + hours + " hours " + minutes + " minutes, Start: " + startTime + ", End: " + endTime + ")";
                    taskListModel.addElement(taskDetails);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                catch (Exception ex){
                    StringBuilder errorMessage = new StringBuilder("Invalid task:");
                    errorMessage.append(ex.getMessage());
                    JOptionPane.showMessageDialog(null,errorMessage.toString(),"Invalid Task", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private SimpleDateFormat createTimeFormatter() {
            return new SimpleDateFormat("HH:mm");
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

        addButton(buttonPanel, "Add Task", new AddTaskButtonListener());

        addButton(buttonPanel, "Delete Task", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                }
            }
        });

        addButton(buttonPanel, "Generate Schedule", new GenerateScheduleButtonListener(taskListModel));

        addButton(buttonPanel, "Logout", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement logout logic
                JOptionPane.showMessageDialog(ToDoListApp.this, "Logged out!");
                dispose();
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
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

}
