package edu.lclark.sortinghat;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class SortingHat {


    private ArrayList<Section> sections;
    private ArrayList<Student> students;
    private ArrayList<Student> unassigned;
    private ArrayList<Student> assigned;

    private Hashtable<String, Integer> idFrequencyTable; // TODO: TEST

    private int numSeats;
    private SeatIndexMap seatMap;

    private double[][] cost;

    private Parser parser;

    /**
     * The proper constructor which takes files as parameters
     *
     * @param sectionsFile

     * @param studentsFile
     */
    public SortingHat(File sectionsFile, File studentsFile) {
        parser = new Parser(sectionsFile, studentsFile);
        sections = parser.getSections();
        students = parser.getStudents();
        unassigned = parser.getUnassigned();
        assigned = parser.getAssigned();

        idFrequencyTable = parser.getIdFrequencyTable();

        numSeats = 0;
        for (Section s : this.sections) {
            numSeats += s.getNumAvailableSeats();
        }
        seatMap = new SeatIndexMap(sections, numSeats);
    }


    /**
     * Non-static run boy
     */
    public void run() {
        sortHungarian();
        students.addAll(assigned);
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
        int[] result = hungary.execute();


        for (int i = 0; i < studentArray.length; i++) {
            // Handle cases where people[i] is -1 (unassigned student)
            if (result[i] < 0) {
                unassigned.add(studentArray[i]);
                continue;
            }
            // Assign students to sections, and update section field of students
            Section currentSection = seatMap.getSection(result[i]); // people[i] might be negative
            currentSection.addStudent(studentArray[i]);
            studentArray[i].setAssignedSection(currentSection);
        }
    }

    /**
     * Builds a the cost matrix for use in the Hungarian algorithm.
     *
     * @return the cost matrix, a 2D array of doubles.  All entries should be finite and positive.
     */
    public double[][] buildCostMatrix() { // sectionsArray will be used later to hard encode male/female ratios, etc

            double alpha = 3.5;
            double defaultWeight = Math.pow(alpha, 7);
            double illegalWeight = Math.pow(alpha, 9);
            double minGenderRatio = .3; //minimum ratio for a gender
            double minNerdRatio = .15; //minimum ratio for nerds
            int numFemaleNerds;
            int numMaleNerds;
            int numFemales;
            int numMales;
            int numNoGenderNerds;

            Section currentSection;

            Section[] sectionsArray = new Section[sections.size()];
            Student[] studentsArray = new Student[students.size()];
            sectionsArray = sections.toArray(sectionsArray);
            studentsArray = students.toArray(studentsArray);
            // Initialize cost array
            cost = new double[numSeats][numSeats];

            // Sets everything to nullWeight
            for (int i = 0; i < numSeats; i++) {
                for (int j = 0; j < numSeats; j++) {
                    cost[i][j] = defaultWeight;
                }
            }

            // Loop through students, grab their preferences, and update the cost matrix for each preference
            // Currently accounts for gender by setting the first 'n' seats to female, next 'n' to male.
            for (int i = 0; i < studentsArray.length; i++) {
                // Loop through the preferences
                ArrayList<Section> preferences = studentsArray[i].getPreferences();
                for (int j = 0; j < preferences.size(); j++) {
                    currentSection = preferences.get(j);
                    // Get the indices in the cost matrix corresponding to the section of current preference
                    int[] indices = seatMap.getIndices(currentSection);
                    //set numMaleNerds and numFemaleNerds
                    numFemales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumFemaleStudents();
                    numMales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumMaleStudents();
                    numFemaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumFemaleNerds();
                    numMaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumMaleNerds();
                    numNoGenderNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumFemaleNerds() - currentSection.getNumMaleNerds();
                    if (numFemaleNerds < 0) {numFemaleNerds = 0;}
                    if (numMaleNerds < 0) {numMaleNerds = 0;}
                    for (int k = 0; k < indices.length; k++) {
                        // Set the first seats to be female nerds
                        if (k < numFemaleNerds) {
                            if (!studentsArray[i].getGender() && !studentsArray[i].getAthlete()) {
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                        // Set the next seats to be females
                        else if (k < numFemales) {
                            if (!studentsArray[i].getGender()) {
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                        // Set the next seats to be male nerds
                        else if (k < numMaleNerds + numFemales) {
                            if (studentsArray[i].getGender() && !studentsArray[i].getAthlete()) { //never enter
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                        // Set the next seats to be males
                        else if (k < numFemales + numMales) {
                            if (studentsArray[i].getGender()) {
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                        //Set the next seat to be agender Nerds
                        else if (k < numFemales + numMales + numNoGenderNerds){
                            if(!studentsArray[i].getAthlete()){
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                        // The remaining seats should be biased only by the preference level
                        else {
                            if(!studentsArray[i].getAthlete()) {
                                cost[i][indices[k]] = weightFunction(alpha, j);
                            }
                        }
                    }
                }

                // Loop through remaining non-preferred sections and account for gender balance
                for (int j = 0; j < sections.size(); j++) {
                    currentSection = sections.get(j);
                    if (preferences.contains(currentSection)) {
                        continue;
                    } // skip if we've already done it in preferences

                    // Get the indices in the cost matrix corresponding to the section of current preference
                    int[] indices = seatMap.getIndices(currentSection);

                    //set numMaleNerds and numFemaleNerds
                    numFemales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumFemaleStudents();
                    numMales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumMaleStudents();
                    numFemaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumFemaleNerds();
                    numMaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumMaleNerds();
                    numNoGenderNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumFemaleNerds() - currentSection.getNumMaleNerds();
                    if (numFemaleNerds < 0) {numFemaleNerds = 0;}
                    if (numMaleNerds < 0) {numMaleNerds = 0;}
                    for (int k = 0; k < indices.length; k++) {
                        // Set the first seats to be biased against female athletes and all males
                        if (k < numFemaleNerds) {
                            if (studentsArray[i].getGender() || studentsArray[i].getAthlete()) {
                                cost[i][indices[k]] = illegalWeight;

                            }
                        }
                        // Set the next seats to be biased against all males
                        else if (k < numFemales){
                            if (studentsArray[i].getGender()) {
                                cost[i][indices[k]] = illegalWeight;

                            }
                        }
                        // Set the next seats to be biased against male athletes and all females
                        else if (k < numMaleNerds + numFemales) {
                            if (!studentsArray[i].getGender() || studentsArray[i].getAthlete()) {
                                cost[i][indices[k]] = illegalWeight;

                            }
                        }
                        //Set the next seats to be biased against all females
                        else if (k < numMales + numFemales) {
                            if (!studentsArray[i].getGender()) {
                                cost[i][indices[k]] = illegalWeight;

                            }
                        }
                        //Set the next seats to be biased againts athletes
                        else if (k < numFemales + numMales + numNoGenderNerds){
                            if(studentsArray[i].getAthlete()){
                                cost[i][indices[k]] = illegalWeight;
                            }
                        }
                        else {
                            if(studentsArray[i].getAthlete()){
                                cost[i][indices[k]] = illegalWeight;
                            }
                        }
                    }
                }

            }

            // make illegal sections have illegalWeight
            for (int i = 0; i < studentsArray.length; i++) {
                ArrayList<String> illegalSections = studentsArray[i].getIllegalSections();
                for (int j = 0; j < illegalSections.size(); j++) {
                    if (!illegalSections.get(j).equals("")) { // max is suspicious
                        int[] indices = seatMap.getIndices(illegalSections.get(j));
                        for (int k = 0; k < indices.length; k++) {
                            cost[i][indices[k]] = illegalWeight;
                        }
                    }
                }
            }

            // Go through the dummy students and set the gendered seats to illegalWeight
            for (int i = studentsArray.length; i < numSeats; i++) {
                for (int j = 0; j < sections.size(); j++) {
                    currentSection = sections.get(j);
                    // Get the indices in the cost matrix corresponding to the section of current preference
                    int[] indices = seatMap.getIndices(currentSection);
                    //set numMaleNerds and numFemaleNerds
                    numFemales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumFemaleStudents();
                    numMales = (int) (currentSection.getCap() * minGenderRatio + .5) - currentSection.getNumMaleStudents();
                    numFemaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumFemaleNerds();
                    numMaleNerds = (int) (currentSection.getCap() * minNerdRatio + .5) - currentSection.getNumMaleNerds();
                    for (int k = 0; k < indices.length; k++) {
                        if (k < numMales + numFemales) {
                            cost[i][indices[k]] = illegalWeight;
                        }
                    }
                }
            }

            return cost;

    }

    /**
     * Weight function takes alpha constant and j variable. Likely will have to change later in order to optimize
     * preferences, gender, and other factors.
     */
    private double weightFunction(double alpha, int j) {
        return Math.pow(alpha, j + 1);
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
            s += element + "\t\t";
        }
        s += "\n";
        return s;
    }

    public double[][] getCost() {
        return cost;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Student> getAssigned() {
        return assigned;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public Hashtable<String, Integer> getIdFrequencyTable() {
        return idFrequencyTable;
    }

    public Parser getParser() {
        return parser;
    }

    /**
     * Main static boy
     *
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
