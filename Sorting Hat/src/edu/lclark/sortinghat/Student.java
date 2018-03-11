package edu.lclark.sortinghat;

import java.util.ArrayList;
import java.util.Arrays;

public class Student {

    private String studentNo;
    private boolean male;
    private boolean athlete;

    private Section mySection;
    private ArrayList<Section> preferences;
    private ArrayList<String> prevProfs;
    private Section assignedSection;

    /**
     *
     * Constructs a Student that is NOT already assigned to a Section.
     * Student is assigned to default assignedSection: Professor DUMMY's snoop seminar
     */
    public Student(String studentNo, ArrayList<Section> preferences, boolean male, boolean athlete, ArrayList<String> prevProfs) {
        this.studentNo = studentNo;
        this.male = male;
        this.athlete = athlete;
        this.prevProfs = prevProfs;

        this.preferences = new ArrayList<Section>(preferences);
        this.assignedSection = new Section("0", "DUMMY", 420); //default section
    }

    /**
     *
     * Constructs a Student that is already assigned to a Section.
     */
    public Student(String studentNo, boolean male, boolean athlete, Section assignedSection) {
        this.studentNo = studentNo;
        this.male = male;
        this.athlete = athlete;
        this.prevProfs = prevProfs;
        this.assignedSection = assignedSection;

        preferences = new ArrayList<>();
    }

    public String getStudentNo() {
        return studentNo;
    }

    public ArrayList<Section> getPreferences() {
        return preferences;
    }

    public boolean getGender() {
        return male;
    }

    public boolean getAthlete() {
        return athlete;
    }

    public ArrayList<String> getPrevProfs() {
        return prevProfs;
    }

    public String toString() {
        return studentNo;
    }

    public Section getAssignedSection() {
        return assignedSection;
    }

    public boolean isAssigned() {
        return assignedSection.getIndex() != 420;
    }

    public void setAssignedSection(Section assignedSection) {
        this.assignedSection = assignedSection;
    }

    public Section getSection() {
        return mySection;
    }

    public void setSection(Section section) {
        this.mySection = section;
    }
}
