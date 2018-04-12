package edu.lclark.sortinghat;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GUI extends JFrame {

    // The actual algorithm
    private SortingHatMain sortingHatMain;

    // These are the buttons that the user can click to interact with the GUI
    private JButton instructions;
    private JButton browseStudents;
    private JButton browseSections;
    private JButton runProgram;

    // These are to hold our reports
    private JTextPane programEfficiency;
    private JTextPane overallPerformance;

    // This is "useless," but makes the user feel like stuff is happening
    private JProgressBar progressBar;


    private int flag;

    private String studentFilePath;
    private String sectionFilePath;


    /**
     * Initializes the GUI
     */
    public GUI() throws HeadlessException {

        // Initialize parameters
        flag = 0;


        // Initialize the JButtons and the progress bar
        instructions = new JButton("Instructions");
        browseStudents = new JButton("Browse Students");
        browseSections = new JButton("Browse Sections");
        runProgram = new JButton("Run");

        // Initialize TextPanes and the Progress Bar
        programEfficiency = new JTextPane();
        overallPerformance = new JTextPane();
        progressBar = new JProgressBar();


        // Create and initialize grid and tooltips
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setSize(800, 600);
        ToolTipManager.sharedInstance().setInitialDelay(100);

        // Create buttons, progress bar and panels for input
        runProgram.setEnabled(false);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        // Create tooltips for buttons
        instructions.setToolTipText("Read the instruction");
        browseStudents.setToolTipText("Attach CSV file of students");
        browseSections.setToolTipText("Attach CSV file of sections");
        runProgram.setToolTipText("Run the program");

        // Initialize action listeners
        Instruction instructAction = new Instruction();
        BrowseStudents studentAction = new BrowseStudents();
        BrowseSections sectionAction = new BrowseSections();
        RunPrograms runAction = new RunPrograms();

//        programEfficiency.setBorder(BorderFactory.createTitledBorder("Program Efficiency"));
//        programEfficiency.setEditable(false);
//        overallPerformance.setBorder(BorderFactory.createTitledBorder("Overall Performance"));
//        overallPerformance.setEditable(false);

        // Add action listener
        instructions.addActionListener(instructAction);
        browseStudents.addActionListener(studentAction);
        browseSections.addActionListener(sectionAction);
        runProgram.addActionListener(runAction);

        // Add the buttons to the Window (Adding to the upper left going down, Width and Height both 1)
        int buttonWeight_x = 20;
        int buttonWeight_y = 100;
//        instructions.setPreferredSize();
        add(instructions, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(buttonWeight_x, buttonWeight_y));
        add(browseStudents, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(buttonWeight_x, buttonWeight_y));
        add(browseSections, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(buttonWeight_x, buttonWeight_y));
        add(runProgram, new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setWeight(buttonWeight_x, buttonWeight_y));

        // Add the text panels to the Window
        add(programEfficiency, new GBC(1, 0, 4, 2).setFill(GBC.BOTH).setWeight(100, 100));
        add(overallPerformance, new GBC(1, 2, 4, 2).setFill(GBC.BOTH).setWeight(100, 100));

        // Add the progress bar to the Window
        add(progressBar, new GBC(0, 4, 5, 1).setFill(GBC.BOTH).setWeight(100, 10));

    }


    private class Instruction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Welcome to Sorting Hat, where we sort students into E&D sections.\n" +
                    "1. Please click the \"Browse Students\" button and select a CSV file.\n" +
                    "2. Please click the \"Browse Sections\" button and select a CSV file.\n" +
                    "3. Please click the  \"Run\" button.", "Instructions", 1);
        }
    }


    private class BrowseStudents implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            jfc.setDialogTitle("Select a CSV file");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(GUI.this);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                if (filter.accept(selectedFile)) {
                    String text = selectedFile.getAbsolutePath().toString();
                    File f = new File(text);
                    flag++;
                    studentFilePath = text;
                    String previous = programEfficiency.getText();
                    programEfficiency.setText(previous + "\n" + "Student File found at " + f.getName() + '\n');
                    // Activate Run
                    if (flag == 2) {
                        runProgram.setEnabled(true);
                    }

                    revalidate();
                }
            }
        }
    }


    private class BrowseSections implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            jfc.setDialogTitle("Select a CSV file");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
            jfc.setFileFilter(filter);

            int returnValue = jfc.showOpenDialog(GUI.this);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                if (filter.accept(selectedFile)) {
                    String text = selectedFile.getAbsolutePath().toString();
                    File f = new File(text);
                    flag++;
                    sectionFilePath = text;
                    String previous = programEfficiency.getText();
                    programEfficiency.setText(previous + "\n" + "Section File found at " + f.getName() + '\n');
                    // Activate Run
                    if (flag == 2) {
                        runProgram.setEnabled(true);
                    }

                    revalidate();
                }
            }
        }
    }


    private class RunPrograms implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sortingHatMain = new SortingHatMain(sectionFilePath, studentFilePath);
//            sortingHatMain.printParse();
            // Run Sorting Hat
            runProgram.setEnabled(false);
            ProgressWorker pw = new ProgressWorker();
            pw.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String name = evt.getPropertyName();
                    if (name.equals("progress")) {
                        int progress = (int) evt.getNewValue();
                        progressBar.setValue(progress);
                        repaint();
                    } else if (name.equals("state")) {
                        SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                        switch (state) {
                            case DONE:
                                runProgram.setEnabled(true);
                                break;
                        }
                    }

                }

            });
            pw.execute();
        }


    }


    public class ProgressWorker extends SwingWorker<Object, Object> {
        @Override
        protected Object doInBackground() throws Exception {
            for (int i = 0; i <= 100; i++) {
                setProgress(i);
//                double progress = sortingHat.getGreedyAlgorithm().getSections().size() /
//                setProgress();
                if (i == 100) {

                    printProgramEfficiency();
                    printOverallPerformance();
                    validate();

                }
                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private void printProgramEfficiency() {
        ArrayList<Student> students = sortingHatMain.getSortingHat().getStudents(); // this includes preassigned students
        students.removeAll(sortingHatMain.getSortingHat().getAssigned()); // remove assigned students from students

//        for (Student s : sortingHatMain.getSortingHat().getAssigned()) {
//            if (students.contains(s)) {
//                System.out.println("hello");
//            }
//            System.out.println(s.getStudentNo());
//        }
//        System.out.println(students.size());
//

        Report report = new Report(sortingHatMain.getSortingHat().getSections(), students); // Note: Doesn't include preassigned students

        programEfficiency.setText("Program Efficiency" + "\nOutput file created at User folder\n" + report.getStatistics());
    }

    private void printOverallPerformance() { // TODO: Figure out why this prints out the same percentages as program efficiency.

        ArrayList<Student> allStudents = new ArrayList<>(sortingHatMain.getSortingHat().getStudents());
//
//        for (Student s: sortingHatMain.getSortingHat().getAssigned()) {
//            if (allStudents.contains(s)) {
//                System.out.println("hello");
//            }
//            System.out.println(s.getStudentNo());
//        }
//        System.out.println(allStudents.size());


        allStudents.addAll(sortingHatMain.getSortingHat().getAssigned());
        System.out.println(allStudents.size());

        Report report = new Report(sortingHatMain.getSortingHat().getSections(), allStudents);

        String stat = "";
        if (report.studentsWithIllegalPreferences().size() != 0) {
            stat = stat + "\nThese students listed a previous professor in their preferences:";
            for (String s : report.studentsWithIllegalPreferences()) {
                stat = stat + " " + s;
            }
            stat += "\n";
        }

        String duplicateReport = "";
        if(!idFrequencyReport().isEmpty()) {
            duplicateReport += "These students were listed in the student csv file multiple times:\n";
            duplicateReport += idFrequencyReport().toString().replaceAll("[\\[\\]]", "") + "\n";
        }

        overallPerformance.setText("Overall Performance\n" + report.getStatistics() + stat + duplicateReport);
    }

    /**
     *
     * Returns an ArrayList of students which were listed in the CSV student file more than once.
     * Accounts for duplicated instances: if they were listed 5 times, they are reported 4 times.
     */
    public ArrayList<String> idFrequencyReport() {
        ArrayList<String> badStudents = new ArrayList<>();
        Hashtable<String, Integer> table = sortingHatMain.getSortingHat().getIdFrequencyTable();

        for (String id : table.keySet()) {
            if (table.get(id) > 1) {
                for (int i = 1; i < table.get(id); i++) {
                    badStudents.add(id);
                }
            }
        }

        return badStudents;
    }

}