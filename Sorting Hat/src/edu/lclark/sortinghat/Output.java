package edu.lclark.sortinghat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;

public class Output {

    private Section section;
    private SortingHat sortingHat;
    private SortingHatMain sortingHatMain;
    private GreedyAlgorithm greedyAlgorithm;
    private HungarianAlgorithm hungarianAlgorithm;
    private Student student;
    private FileWriter csvOutput;
    ArrayList<Section> sections;
    ArrayList<Student> students;

    // Constructor creates Output
    public Output(ArrayList<Section> sections, ArrayList<Student> students) {

        greedyAlgorithm = new GreedyAlgorithm(sections, students);
        greedyAlgorithm.run();

        sections = greedyAlgorithm.getSections();
        students = greedyAlgorithm.getStudents();
        StringBuilder sb = new StringBuilder();
        String outputFile = "eanddsorted.csv";
        String userHomeFolder = System.getProperty("user.home");

        try {
            // before we open the file check to see if it already exists
            boolean alreadyExists = new File(userHomeFolder, outputFile).exists();

            // use FileWriter constructor that specifies open for appending
            int fileEnd = 2;
            while (alreadyExists) {
                String testOutputFileName = "eanddsorted" + Integer.toString(fileEnd) + ".csv";
                alreadyExists =  new File(testOutputFileName).exists();
                fileEnd ++;
                outputFile = testOutputFileName;
            }
            PrintWriter pw = new PrintWriter(new File(userHomeFolder, outputFile));
            if (!alreadyExists) {
                sb.append("section");
                sb.append(",");
                sb.append("student id");
                sb.append("\n");

                for (int i = 0; i < sections.size(); i++) {
                    sb.append(sections.get(i));
                    sb.append(",");
                    int size = sections.get(i).getSize();
                    ArrayList<Student> studentList = sections.get(i).getStudents();
                    int innerSize = studentList.size();
                    for (int j = 0; j < innerSize; j++) {
                        String data = studentList.get(j).getStudentNo();
                        sb.append(data);
                        if (j != innerSize - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append("\n");

                }

                pw.write(sb.toString());
                pw.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
