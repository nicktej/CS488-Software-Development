package edu.lclark.sortinghat;

import javafx.scene.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SortingHat {


    private ArrayList<Section> sections;
    private ArrayList<Student> students;
    private ArrayList<Student> unassigned;

    private int numStudents;
    public int numSeats;
    private SeatIndexMap seatMap;

    private double[][] cost;
    private int[] result;

    /**
     * This constructor used for testing purposes, I think
     * @param sections
     * @param students
     */
    public SortingHat(ArrayList<Section> sections, ArrayList<Student> students) {
        this.sections = sections;
        this.students = students;
        unassigned = new ArrayList<>();

        // iterate through sections and sum the number of seats available
        numSeats = 0;
        for (Section s : this.sections) {
            numSeats += s.getSize();
        }
        Section[] sectionsArray = new Section[sections.size()];
        seatMap = new SeatIndexMap(sections, numSeats);
    }

    /**
     * The proper constructor which takes files as parameters
     * @param sectionsFile
     * @param studentsFile
     */
    public SortingHat(File sectionsFile, File studentsFile) {
        sections = new ArrayList<>();
        students = new ArrayList<>();
        unassigned = new ArrayList<>();

        parseSectionCSV(sectionsFile);
        parseStudentCSV(studentsFile);

        // iterate through sections and sum the number of seats available
        numSeats = 0;
        for (Section s : this.sections) {
            numSeats += s.getSize();
        }
        Section[] sectionsArray = new Section[sections.size()];
        seatMap = new SeatIndexMap(sections, numSeats);
    }

    /**
     * Puts section information from CSV file into section objects
     * @param file
     */
    public void parseSectionCSV(File file) {

        int index = 0;

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {

            List<String> row = CSVReader.parseLine(scanner.nextLine());

            String sectionNo = row.get(0);
            String prof = row.get(1);

            sections.add(new Section(sectionNo, prof, ++index));
        }

        scanner.close();
    }

    /**
     * Puts student information from CSV file into student objects
     * @param file
     */
    public void parseStudentCSV(File file) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            List<String> row = CSVReader.parseLine(scanner.nextLine());

            String id = row.get(1);

            ArrayList<Section> preferences = new ArrayList<>(); // TODO: reference already parsed sections arraylist

            //TODO: check that this should be going to 6
            for (int i = 0; i < 6; i++)
                for (Section s : sections) {
                    if (s.getSectionNo().equals(row.get(11 + i))) {
                        preferences.add(sections.get(s.getIndex() - 1)); // Goes out of bounds (removed a -1 and it works!)
                    }
                }

            boolean male = row.get(2).equals("M");
            boolean athlete = row.get(3).equals("y");

            // List<String> = CSVReader.parseLine(row.get(10))

            ArrayList<String> prevProfs = new ArrayList<>(); // TODO: this should add the entire list..
            prevProfs.add(row.get(9));

            students.add(new Student(id, preferences, male, athlete, prevProfs));
            //public Student(int studentNo, Section[] preferences, boolean male, boolean athlete, String previousProf) {
        }

        scanner.close();

    }

    /**
     * Non-static run boy
     */
    public void run() {
        sortHungarian();
        new Output(sections, students);
    }

    /**
     * Sorts the students into sections using the hungarian algorithm
     */
    public void sortHungarian() {   //TODO: Refactor to use copies of sections & students

        // Convert ArrayLists to arrays as prep for hungarian algorithm
        Section[] sectionsArray = new Section[sections.size()];
        Student[] studentArray = new Student[students.size()];
        sectionsArray = sections.toArray(sectionsArray);
        studentArray = students.toArray(studentArray);

        // Build the cost matrix and execute the hungarian algorithm
        buildCostMatrix();
        HungarianAlgorithm hungary = new HungarianAlgorithm(cost);
        result = hungary.execute();


        for (int i = 0; i < studentArray.length; i++) {
            // Handle cases where people[i] is -1 (unassigned student)
            if (result[i] < 0) {
                unassigned.add(studentArray[i]);
                continue;
            }
            // Assign students to sections, and update section field of students
            Section currentSection = seatMap.getSection(result[i]); // people[i] might be negative
            currentSection.addStudent(studentArray[i]);
            studentArray[i].setSection(currentSection);
        }

    }

    /**
     * Builds a the cost matrix for use in the Hungarian algorithm.
     * @return the cost matrix, a 2D array of doubles.  All entries should be finite and positive.
     */
    public double[][] buildCostMatrix() { // sectionsArray will be used later to hard encode male/female ratios, etc
        Section[] sectionsArray = new Section[sections.size()];
        Student[] studentsArray = new Student[students.size()];
        sectionsArray = sections.toArray(sectionsArray);
        studentsArray = students.toArray(studentsArray);
        // Initialize cost array
        cost = new double[numSeats][numSeats];

        // Sets everything to 100
        for (int i = 0; i < numSeats; i++) {
            for (int j = 0; j < numSeats; j++) {
                cost[i][j] = 100;
            }
        }

        //fills in appropriate numbers for student's preferences
        for (int i = 0; i < studentsArray.length; i++) { // loop through students
            ArrayList<Section> preferences = studentsArray[i].getPreferences(); // grab the student's preferences
            for (int j = 0; j < preferences.size(); j++) {
                int[] indices = seatMap.getIndices(preferences.get(j));
                for (int k = 0; k < indices.length; k++) {
                    cost[i][indices[k]] = j + 1;
                }
            }
        }

        return cost;
    }

    public String prettifyMatrix(double[][] mat) {
        String s = "";
        for (double[] row : mat) {
            for (double element : row) {
                s += (int) element + "\t\t";
            }
            s += "\n";
        }
        return s;
    }

    public String prettifyMatrix(int[] mat) {
        String s = "";
            for (double element : mat) {
                s +=  element + "\t\t";
            }
            s += "\n";
        return s;
    }

    public double[][] getCost() {
        return cost;
    }

    public int[] getResult() {
        return result;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Main static boy
     * @param args
     */
    public static void main(String[] args) {
        SortingHat sortingHat = new SortingHat(new File("csvparsetestSECT.csv"), new File("csvparsetestSTUD.csv"));
        System.out.println("The Cost Array is:");
        System.out.println(sortingHat.prettifyMatrix(sortingHat.buildCostMatrix()));
        System.out.println("The assignment array is:");
        System.out.println("The Cost Array is:");
    }

}

