package ToDo6;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author sapaythhossain
 */
public class ToDo6 extends JFrame {

    Color greenSea = Color.decode("#157770");
    Color torquish = Color.decode("#1abc9c");
    Color clouds = Color.decode("#ecf0f1");

    Border greenSeaLine = BorderFactory.createLineBorder(greenSea);
    //Specify the file name and path here
    File fileAllTasks = new File("AllTasks.txt");
    File fileCompletedTasks = new File("CompletedTasks.txt");
    File fileRemovedTasks = new File("RemovedTasks.txt");

    private static ArrayList<JPanel> PanelSingleTask = new ArrayList<>();

    private static ArrayList<String> allTasksAL = new ArrayList<>();
    private static ArrayList<String> completedTasksAL = new ArrayList<>();
    private static ArrayList<String> removedTasksAL = new ArrayList<>();

    private JPanel headerPanel;
    private JPanel PanelAllTasks;
    private JLabel headerLabel;
    private JLabel warnings;
    private JButton btnCompletedTasks;
    private JButton btnRemovedTasks;

    private JTextField TFnewTask;
    private JButton btnAddTask;

    public static void main(String[] args) {

        ToDo6 toDo = new ToDo6();
        toDo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toDo.setSize(400, 600);
        toDo.setLocationRelativeTo(null);
        toDo.setVisible(true);
    }

    public static ArrayList<String> getAllTasksAL() {
        return allTasksAL;
    }

    public static void setAllTasksAL(ArrayList<String> allTasksAL) {
        ToDo6.allTasksAL = allTasksAL;
    }

    public static ArrayList<String> getCompletedTasks() {
        return completedTasksAL;
    }

    public static void setCompletedTasks(ArrayList<String> completedTasks) {
        ToDo6.completedTasksAL = completedTasks;
    }

    public static ArrayList<String> getRemovedTasks() {
        return removedTasksAL;
    }

    public static void setRemovedTasks(ArrayList<String> removedTasks) {
        ToDo6.removedTasksAL = removedTasks;
    }

    public ToDo6() {
        super();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        setLayout(layout);

        // header area design
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(400, 150));
        headerPanel.setBackground(greenSea);
        add(headerPanel);

        // heading
        headerLabel = new JLabel("To Do List 6");
        headerLabel.setPreferredSize(new Dimension(400, 35));
        headerLabel.setFont(new java.awt.Font("Open Sans", 1, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        // completed tasks and removed tasks buttons
        btnCompletedTasks = new JButton("Completed Tasks");
        btnRemovedTasks = new JButton("Removed Tasks");
        headerPanel.add(btnCompletedTasks);
        headerPanel.add(btnRemovedTasks);

        // add new task text field and button
        TFnewTask = new JTextField();
        TFnewTask.setColumns(26);
        TFnewTask.setEditable(true);
        headerPanel.add(TFnewTask);

        btnAddTask = new JButton("+");
        btnAddTask.setFont(new java.awt.Font("Open Sans", 1, 24));
        btnAddTask.setForeground(greenSea);
        btnAddTask.setPreferredSize(new Dimension(35, 35));
        headerPanel.add(btnAddTask);

        // warnings label
        warnings = new JLabel();
        warnings.setForeground(Color.WHITE);
        headerPanel.add(warnings);

        // show all tasks area design
        PanelAllTasks = new JPanel();
        PanelAllTasks.setLayout(new GridLayout(100, 1));
        PanelAllTasks.setBackground(greenSea);
        PanelAllTasks.setEnabled(true);
        add(PanelAllTasks);

        // add new task button action
        btnAddTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                allTasksAL.add(TFnewTask.getText());

                addNewTask();
                PanelAllTasks.revalidate();
                PanelAllTasks.repaint();

                TFnewTask.setText("");
            }
        });

        // enter button action
        TFnewTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                allTasksAL.add(TFnewTask.getText());

                addNewTask();
                showPreviousTasks();
                PanelAllTasks.revalidate();
                PanelAllTasks.repaint();

                TFnewTask.setText("");
            }
        });

        // completed button acion
        btnCompletedTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // open CompletedTasks frame
                CompletedTasks ct = new CompletedTasks();
                ct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ct.setSize(400, 600);
                ct.setLocationRelativeTo(null);
                ct.setVisible(true);
            }
        });
        
        // removed button acion
        btnRemovedTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // open CompletedTasks frame
                RemovedTasks rt = new RemovedTasks();
                rt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                rt.setSize(400, 600);
                rt.setLocationRelativeTo(null);
                rt.setVisible(true);

            }
        });
        showPreviousTasks();
    } // end constructor ToDo5

    public void addNewTask() {
        if (TFnewTask.getText().trim().equals("")) {
            warnings.setText("No task to add!");
        } else {
            // clear warnings
            warnings.setText("");

            // build single tasks
            final JPanel taskPanel = new JPanel();      // make it final so that it is accessible from inner anon class
            //taskPanel.setPreferredSize(new Dimension(390, 35));
            taskPanel.setBackground(clouds);
            taskPanel.setBorder(greenSeaLine);
            PanelSingleTask.add(taskPanel);     // add to PanelSingleTask ArrayList
            PanelAllTasks.add(taskPanel); // add ArrayList to PanelAllTasks

            // create and add textfield to show task
            final JTextField TFsingleTask = new JTextField();
            TFsingleTask.setColumns(26);
            TFsingleTask.setBackground(clouds);
            TFsingleTask.setForeground(greenSea);
            TFsingleTask.setBorder(null);
            TFsingleTask.setEditable(false);
            taskPanel.add(TFsingleTask);

            // create and add "Complete" button
            JButton btnSingleCompleted = new JButton("√");
            btnSingleCompleted.setPreferredSize(new Dimension(35, 25));
            btnSingleCompleted.setToolTipText("Task completed");
            btnSingleCompleted.setBorder(null);
            btnSingleCompleted.setBorderPainted(false);
            btnSingleCompleted.setMargin(new Insets(0, 0, 0, 0));
            btnSingleCompleted.setForeground(greenSea);
            taskPanel.add(btnSingleCompleted);

            // create and add "remove" button
            JButton btnSingleRemove = new JButton("X");
            btnSingleRemove.setPreferredSize(new Dimension(35, 25));
            btnSingleRemove.setToolTipText("Remove task");
            btnSingleRemove.setBorder(null);
            btnSingleRemove.setBorderPainted(false);
            btnSingleRemove.setMargin(new Insets(0, 0, 0, 0));
            btnSingleRemove.setForeground(Color.RED);
            taskPanel.add(btnSingleRemove);

            // show the task
            TFsingleTask.setText(TFnewTask.getText());

            // save into file
            try {
                /* This logic is to create the file if the
                * file is not already present
                 */
                if (!fileAllTasks.exists()) {
                    fileAllTasks.createNewFile();
                }
                //Here true is to append the content to file
                FileWriter fw = new FileWriter(fileAllTasks, true);
                //BufferedWriter writer give better performance
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(TFnewTask.getText() + "\n");
                //Closing BufferedWriter Stream
                bw.close();
            } catch (IOException err) {
                JOptionPane.showMessageDialog(null, "Error creating file!");
            }

            // remove button acion
            btnSingleRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // save into file
                    try {
                        /* This logic is to create the file if the
                        * file is not already present
                         */
                        if (!fileRemovedTasks.exists()) {
                            fileRemovedTasks.createNewFile();
                        }
                        //Here true is to append the content to file
                        FileWriter fw = new FileWriter(fileRemovedTasks, true);
                        //BufferedWriter writer give better performance
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(TFsingleTask.getText() + "\n");
                        //Closing BufferedWriter Stream
                        bw.close();
                    } catch (IOException err) {
                        JOptionPane.showMessageDialog(null, "Error creating file!");
                    }

                    removedTasksAL.add(TFsingleTask.getText());
                    PanelSingleTask.remove(taskPanel);
                    PanelAllTasks.remove(taskPanel);
                    PanelAllTasks.revalidate();
                    PanelAllTasks.repaint();
                    JOptionPane.showMessageDialog(null, "Task Removed!");
                }
            });

            // completed button acion
            btnSingleCompleted.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    // save into file
                    try {
                        /* This logic is to create the file if the
                        * file is not already present
                         */
                        if (!fileCompletedTasks.exists()) {
                            fileCompletedTasks.createNewFile();
                        }
                        //Here true is to append the content to file
                        FileWriter fw = new FileWriter(fileCompletedTasks, true);
                        //BufferedWriter writer give better performance
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(TFsingleTask.getText() + "\n");
                        //Closing BufferedWriter Stream
                        bw.close();
                    } catch (IOException err) {
                        JOptionPane.showMessageDialog(null, "Error creating file!");
                    }

                    completedTasksAL.add(TFsingleTask.getText());
                    PanelSingleTask.remove(taskPanel);
                    PanelAllTasks.remove(taskPanel);
                    PanelAllTasks.revalidate();
                    PanelAllTasks.repaint();
                    JOptionPane.showMessageDialog(null, "Task completed!");
                }
            });
        } // end of else
    } // end method addNewTask

    public void showPreviousTasks() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileAllTasks));
            String t = "";
            while ((t = br.readLine()) != null) {
                allTasksAL.add(t);
            }
            br.close();
            for (String task : allTasksAL) {
                // build single tasks
                JPanel taskPanel = new JPanel();      // make it final so that it is accessible from inner anon class
                //taskPanel.setPreferredSize(new Dimension(390, 35));
                taskPanel.setBackground(clouds);
                taskPanel.setBorder(greenSeaLine);
                PanelSingleTask.add(taskPanel);     // add to PanelSingleTask ArrayList
                PanelAllTasks.add(taskPanel); // add ArrayList to PanelAllTasks

                // create and add textfield to show task
                JTextField TFsingleTask = new JTextField();
                TFsingleTask.setColumns(26);
                TFsingleTask.setBackground(clouds);
                TFsingleTask.setForeground(greenSea);
                TFsingleTask.setBorder(null);
                TFsingleTask.setEditable(false);
                TFsingleTask.setText(task);
                taskPanel.add(TFsingleTask);

                // create and add "Complete" button
                JButton btnSingleCompleted = new JButton("√");
                btnSingleCompleted.setPreferredSize(new Dimension(35, 25));
                btnSingleCompleted.setToolTipText("Task completed");
                btnSingleCompleted.setBorder(null);
                btnSingleCompleted.setBorderPainted(false);
                btnSingleCompleted.setMargin(new Insets(0, 0, 0, 0));
                btnSingleCompleted.setForeground(greenSea);
                taskPanel.add(btnSingleCompleted);

                // create and add "remove" button
                JButton btnSingleRemove = new JButton("X");
                btnSingleRemove.setPreferredSize(new Dimension(35, 25));
                btnSingleRemove.setToolTipText("Remove task");
                btnSingleRemove.setBorder(null);
                btnSingleRemove.setBorderPainted(false);
                btnSingleRemove.setMargin(new Insets(0, 0, 0, 0));
                btnSingleRemove.setForeground(Color.RED);
                taskPanel.add(btnSingleRemove);

                // remove button acion
                btnSingleRemove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // save into file
                        try {
                            /* This logic is to create the file if the
                            * file is not already present
                             */
                            if (!fileRemovedTasks.exists()) {
                                fileRemovedTasks.createNewFile();
                            }
                            //Here true is to append the content to file
                            FileWriter fw = new FileWriter(fileRemovedTasks, true);
                            //BufferedWriter writer give better performance
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(TFsingleTask.getText() + "\n");
                            //Closing BufferedWriter Stream
                            bw.close();
                        } catch (IOException err) {
                            JOptionPane.showMessageDialog(null, "Error creating file!");
                        }
                        removedTasksAL.add(TFsingleTask.getText());
                        removeTaskFromFile(
                                String.valueOf(fileAllTasks),
                                TFsingleTask.getText() );
                        PanelSingleTask.remove(taskPanel);
                        PanelAllTasks.remove(taskPanel);
                        PanelAllTasks.revalidate();
                        PanelAllTasks.repaint();
                        JOptionPane.showMessageDialog(null, "Task Removed!");
                    }
                });

                // completed button acion
                btnSingleCompleted.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        // save into file
                        try {
                            /* This logic is to create the file if the
                        * file is not already present
                             */
                            if (!fileCompletedTasks.exists()) {
                                fileCompletedTasks.createNewFile();
                            }
                            //Here true is to append the content to file
                            FileWriter fw = new FileWriter(fileCompletedTasks, true);
                            //BufferedWriter writer give better performance
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(TFsingleTask.getText() + "\n");
                            //Closing BufferedWriter Stream
                            bw.close();
                        } catch (IOException err) {
                            JOptionPane.showMessageDialog(null, "Error creating file!");
                        }

                        completedTasksAL.add(TFsingleTask.getText());
                        PanelSingleTask.remove(taskPanel);
                        PanelAllTasks.remove(taskPanel);
                        PanelAllTasks.revalidate();
                        PanelAllTasks.repaint();
                        JOptionPane.showMessageDialog(null, "Task completed!");
                    }
                });
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Exception!");
        }
    }

    public void removeTaskFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");
            }

        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
    } // end method removeTaskFromFile

} // end class ToDo5
