package org.example.GUI.ToDoList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.Login.LoginPage;
import org.example.GUI.models.Schedule;
import org.example.GUI.models.Task;
import org.example.GUI.models.UserIdObject;
import org.example.GUI.rest.ClientWindow;
import org.example.GUI.utilities.ToDoListRequestMaker;
import org.example.GUI.utilities.Utils;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class ToDoListApp extends ClientWindow {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private RestTemplate restTemplate;
    private Long userId;
    private List<Schedule> scheduleList;
    private Long scheduleId = 0L; //current operating  schedule

    public DefaultListModel<String> getTaskListModel() {
        return taskListModel;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return this.restTemplate;
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

                try {
                    Utils.validateTask(new Task(taskName, totalMinutesDuration, LocalTime.parse(startTime), LocalTime.parse(endTime)));
                    // Add the task to the list*/
                    String taskDetails = taskName + " (Duration: " + hours + " hours " + minutes + " minutes, Start: " + startTime + ", End: " + endTime + ")";
                    taskListModel.addElement(taskDetails);
                } catch (DateTimeParseException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid task:" + ex.getMessage(), "Invalid Task", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private SimpleDateFormat createTimeFormatter() {
            return new SimpleDateFormat("HH:mm");
        }
    }

    public ToDoListApp(RestTemplate restTemplate, Long id) {
        this.userId = id;
        this.scheduleList = new LinkedList<>();
        setTitle("To-Do List App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.restTemplate = restTemplate;
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

        addButton(buttonPanel, "Generate Schedule", new GenerateScheduleButtonListener(taskListModel, this));
        addButton(buttonPanel, "Save", new SaveScheduleButtonListener(this));

        addButton(buttonPanel, "Next", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get next schedule id
                //get current schedule index in the list
                Optional<Schedule> nextScheduleId = scheduleList.stream()
                        .filter(s -> Objects.equals(s.getId(), scheduleId)).findFirst();
                if (nextScheduleId.isEmpty()) {
                    return;
                }
                //altfel, iau indexul
                var index = scheduleList.indexOf(nextScheduleId.get());
                //iau urmatorul schedule
                if (scheduleList.size() - 1 >= ++index) {
                    scheduleId = scheduleList.get(index).getId();
                    loadScheduleActivities(scheduleId);
                    return;
                }
                JOptionPane.showMessageDialog(ToDoListApp.this, "You have reached the end of the list!");

            }
        });
        addButton(buttonPanel, "Previous", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get next schedule id
                //get current schedule index in the list
                Optional<Schedule> nextScheduleId = scheduleList.stream()
                        .filter(s -> Objects.equals(s.getId(), scheduleId)).findFirst();
                if (nextScheduleId.isEmpty()) {
                    return;//nu se intampla nimic
                }

                //altfel, iau indexul
                var index = scheduleList.indexOf(nextScheduleId.get());
                //iau precedentul schedule
                if (--index > -1) {
                    scheduleId = scheduleList.get(index).getId();
                    loadScheduleActivities(scheduleId);
                    return;
                }
                JOptionPane.showMessageDialog(ToDoListApp.this, "You have reached the beginning of the list!");

            }
        });
        // addButton(buttonPanel, "New Schedule", new GenerateScheduleButtonListener(taskListModel, this));
        addButton(buttonPanel, "Delete Schedule", new DeleteScheduleButtonListener(this));

        addButton(buttonPanel, "Logout", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement logout logic
                JOptionPane.showMessageDialog(ToDoListApp.this, "Logged out!");
                dispose();
                LoginPage loginPage = new LoginPage(restTemplate);
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

        //make a request to get all schedules
        ToDoListRequestMaker.getSchedulesRequest(this);
        //load the first one by setting id = ... and making the tasks shown to be the ones in the schedule
        loadScheduleActivities(scheduleId);
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


    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    void loadScheduleActivities(Long scheduleId) {
        //print all activities in the schedule
        //find the schedule first
        Optional<Schedule> scheduleOptional = scheduleList.stream()
                .filter(s -> Objects.equals(s.getId(), scheduleId))
                .findFirst();
        if (scheduleOptional.isEmpty())
            return;
        taskListModel.clear();
        //if the list of activities is not yet initialized( apparently the repository.save does not do that)
        if(scheduleOptional.get().getScheduleActivities() == null)
            scheduleOptional.get().setScheduleActivities(new LinkedList<>());
        //if i have tasks in the schedule, show them on the screen
        if (scheduleOptional.get().getScheduleActivities().size()>0)
            for (Task task : scheduleOptional.get().getScheduleActivities()) {
                taskListModel.addElement(task.toString());
            }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
