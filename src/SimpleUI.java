import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleUI extends JFrame {
    // UI components
    private JLabel label1;
    private JButton managerButton;
    private JButton employeeButton;
    private JButton warehouseWorkerButton;
    private JPanel loginPanel;
    private JTabbedPane tabbedPane;
    private JLabel label2;
    private JTextArea textArea2;
    private JLabel label3;
    private JTextArea textArea3;
    private JButton button3;
    private JButton button4;
    private JLabel label4;
    private JTextArea textArea4;
    private JLabel label5;
    private JTextArea textArea5;
    private JLabel label6;
    private JTextArea textArea6;
    private JLabel label7;
    private JTextArea textArea7;
    private JButton button5;
    private JButton button6;
    private JLabel label8;
    private JTextArea textArea8;
    private JLabel label9;
    private JTextArea textArea9;
    private JLabel label10;
    private JTextArea textArea10;
    private JLabel label11;
    private JTextArea textArea11;
    private JRadioButton radioButton1;
    private JButton button7;
    private JButton button8;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    // Enum for the different states of the frame
    private enum State {
        LOGIN,
        MANAGER,
        EMPLOYEE,
        WAREHOUSE_WORKER
    }

    // Current state of the frame
    private State state;

    public SimpleUI() {
        // Set up the UI components
        label1 = new JLabel("Select your role:");
        managerButton = new JButton("Manager");
        employeeButton = new JButton("Employee");
        warehouseWorkerButton = new JButton("Warehouse Worker");
        loginPanel = new JPanel(new GridBagLayout());
        tabbedPane = new JTabbedPane();
        label2 = new JLabel("Text label for tab 1, input 1:");
        textArea2 = new JTextArea(5, 20);
        label3 = new JLabel("Text label for tab 1, input 2:");
        textArea3 = new JTextArea(5, 20);
        button3 = new JButton("Button 3");
        button4 = new JButton("Button 4");
        label4 = new JLabel("Text label for tab 2, input 1:");
        textArea4 = new JTextArea(5, 20);
        label5 = new JLabel("Text label for tab 2, input 2:");
        textArea5 = new JTextArea(5, 20);
        label6 = new JLabel("Text label for tab 2, input 3:");
        textArea6 = new JTextArea(5, 20);
        label7 = new JLabel("Text label for tab 2, input 4:");
        textArea7 = new JTextArea(5, 20);
        button5 = new JButton("Button 5");
        button6 = new JButton("Button 6");
        label8 = new JLabel("Text label for tab 3, input 1:");
        textArea8 = new JTextArea(5, 20);
        label9 = new JLabel("Text label for tab 3, input 2:");
        textArea9 = new JTextArea(5, 20);
        label10 = new JLabel("Text label for tab 3, input 3:");
        textArea10 = new JTextArea(5, 20);
        label11 = new JLabel("Text label for tab 3, input 4:");
        textArea11 = new JTextArea(5, 20);
        radioButton1 = new JRadioButton("Radio button");
        button7 = new JButton("Button 7");
        button8 = new JButton("Button 8");
        panel1 = new JPanel(new GridBagLayout());
        panel2 = new JPanel(new GridBagLayout());
        panel3 = new JPanel(new GridBagLayout());

        // Set the initial state
        state = State.LOGIN;

        // Set up the constraints for the GridBagLayout
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Top, left, bottom, right padding
        c.fill = GridBagConstraints.HORIZONTAL;

        // Add the UI components to the login panel
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(label1, c);
        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(managerButton, c);
        c.gridx = 1;
        c.gridy = 1;
        loginPanel.add(employeeButton, c);
        c.gridx = 2;
        c.gridy = 1;
        loginPanel.add(warehouseWorkerButton, c);

        // Add the UI components to the first panel
        c.gridx = 0;
        c.gridy = 0;
        panel1.add(label2, c);
        c.gridx = 1;
        c.gridy = 0;
        panel1.add(textArea2, c);
        c.gridx = 0;
        c.gridy = 1;
        panel1.add(label3, c);
        c.gridx = 1;
        c.gridy = 1;
        panel1.add(textArea3, c);
        c.gridx = 0;
        c.gridy = 2;
        panel1.add(button3, c);
        c.gridx = 1;
        c.gridy = 2;
        panel1.add(button4, c);

        // Add the UI components to the second panel
        c.gridx = 0;
        c.gridy = 0;
        panel2.add(label4, c);
        c.gridx = 1;
        c.gridy = 0;
        panel2.add(textArea4, c);
        c.gridx = 0;
        c.gridy = 1;
        panel2.add(label5, c);
        c.gridx = 1;
        c.gridy = 1;
        panel2.add(textArea5, c);
        c.gridx = 0;
        c.gridy = 2;
        panel2.add(label6, c);
        c.gridx = 1;
        c.gridy = 2;
        panel2.add(textArea6, c);
        c.gridx = 0;
        c.gridy = 3;
        panel2.add(label7, c);
        c.gridx = 1;
        c.gridy = 3;
        panel2.add(textArea7, c);
        c.gridx = 0;
        c.gridy = 4;
        panel2.add(button5, c);
        c.gridx = 1;
        c.gridy = 4;
        panel2.add(button6, c);

        // Add the UI components to the third panel
        c.gridx = 0;
        c.gridy = 0;
        panel3.add(label8, c);
        c.gridx = 1;
        c.gridy = 0;
        panel3.add(textArea8, c);
        c.gridx = 0;
        c.gridy = 1;
        panel3.add(label9, c);
        c.gridx = 1;
        c.gridy = 1;
        panel3.add(textArea9, c);
        c.gridx = 0;
        c.gridy = 2;
        panel3.add(label10, c);
        c.gridx = 1;
        c.gridy = 2;
        panel3.add(textArea10, c);
        c.gridx = 0;
        c.gridy = 3;
        panel3.add(label11, c);
        c.gridx = 1;
        c.gridy = 3;
        panel3.add(textArea11, c);
        c.gridx = 0;
        c.gridy = 4;
        panel3.add(radioButton1, c);
        c.gridx = 1;
        c.gridy = 4;
        panel3.add(button7, c);
        c.gridx = 2;
        c.gridy = 4;
        panel3.add(button8, c);

        // Add the panels to the tabbed pane
        tabbedPane.addTab("Tab 1", panel1);
        tabbedPane.addTab("Tab 2", panel2);
        tabbedPane.addTab("Tab 3", panel3);

        // Set up the button actions
        managerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action for the manager button goes here
                // Change to the manager state
                state = State.MANAGER;
                updateUI();
            }
        });
        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action for the employee button goes here
                // Change to the employee state
                state = State.EMPLOYEE;
                updateUI();
            }
        });
        warehouseWorkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action for the warehouse worker button goes here
                // Change to the warehouse worker state
                state = State.WAREHOUSE_WORKER;
                updateUI();
            }
        });

        // Set up the initial UI
        updateUI();
    }

    // Method to update the UI based on the current state
    private void updateUI() {
        // Clear the frame
        getContentPane().removeAll();

        // Add the appropriate components based on the current state
        if (state == State.LOGIN) {
            getContentPane().add(loginPanel);
        } else if (state == State.MANAGER) {
            getContentPane().add(tabbedPane);
        } else if (state == State.EMPLOYEE) {
            // Add the components for the employee state
        } else if (state == State.WAREHOUSE_WORKER) {
            // Add the components for the warehouse worker state
        }

        // Refresh the frame
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {
        // Set up the frame
        SimpleUI frame = new SimpleUI();
        frame.setTitle("Simple UI");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}