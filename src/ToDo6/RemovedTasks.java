/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ToDo6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class RemovedTasks extends JFrame {
    

    Color greenSea = Color.decode("#157770");
    Color torquish = Color.decode("#1abc9c");
    Border greenSeaLine = BorderFactory.createLineBorder(greenSea);

    private static ArrayList<String> removedTasksAL = new ArrayList<>();
    private static JPanel panelAllTasks;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private JButton btnClear;
    private JButton btnBack;

    public RemovedTasks() {
        super("To Do List 6");
        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        setLayout(layout);

        // header area design
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(400, 50));
        headerPanel.setBackground(greenSea);
        add(headerPanel);

        // heading
        headerLabel = new JLabel("Removed Tasks");
        headerLabel.setPreferredSize(new Dimension(400, 35));
        headerLabel.setFont(new java.awt.Font("Open Sans", 1, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        btnBack = new JButton("Back");
        add(btnBack);
        
//        btnClear = new JButton("Clear all");
//        add(btnClear);
        
        // show all tasks area design
        panelAllTasks = new JPanel();
        panelAllTasks.setLayout(new GridLayout(100, 1));
        panelAllTasks.setEnabled(true);
        add(panelAllTasks);

        showRemovedTasks();
        //clearBtn();
        
        // back button action
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // open CompletedTasks frame
//                ToDo6 todo = new ToDo6();
//                todo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                todo.setSize(400, 600);
//                todo.setLocationRelativeTo(null);
                new ToDo6().setVisible(true);
                setVisible(false);

            }
        });
    }

    public void showRemovedTasks() {
        removedTasksAL.clear();
        File fileRemovedTasks = new File("RemovedTasks.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileRemovedTasks));
            String t = "";
            while ((t = br.readLine()) != null) {
                removedTasksAL.add(t);
            }
            for ( String s : removedTasksAL ) {
                // build single tasks
                final JPanel taskPanel = new JPanel();      // make it final so that it is accessible from inner anon class
                taskPanel.setBackground(torquish);
                taskPanel.setBorder(greenSeaLine);
                panelAllTasks.add(taskPanel); // add ArrayList to panelAllTasks

                // create and add textfield to show task
                final JTextField TFsingleTask = new JTextField();
                TFsingleTask.setColumns(32);
                TFsingleTask.setBackground(torquish);
                TFsingleTask.setForeground(Color.WHITE);
                TFsingleTask.setBorder(null);
                TFsingleTask.setText(s);
                TFsingleTask.setEditable(false);
                taskPanel.add(TFsingleTask);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IO Exception!");
        }
    }

//    public void clearBtn() {
//        btnClear.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                File fileCompletedTasks = new File("RemovedTasks.txt");
//                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileCompletedTasks));
//                    bw.close();
//                } catch (FileNotFoundException ex) {
//                    JOptionPane.showMessageDialog(null, "File not found!");
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "IO Exception!");
//                }
//
//            }
//        });
//    }

//    public static void main(String[] args) {
//        RemovedTasks rt = new RemovedTasks();
//        rt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        rt.setSize(400, 600);
//        rt.setLocationRelativeTo(null);
//        rt.setVisible(true);
//    }
}