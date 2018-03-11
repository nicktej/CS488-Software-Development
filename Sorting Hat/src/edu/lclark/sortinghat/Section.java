package edu.lclark.sortinghat;

import java.util.*;

public class Section {

    private String sectionNo;
    private int size;
    private String prof;
    private int index;
    private ArrayList<Student> students;

    /**
     * I don't know why we have three different Section constructors but I think this first one is the best one?
     * @param sectionNo
     * @param prof
     * @param index
     */
    public Section(String sectionNo, String prof, int index) {
        this.sectionNo = sectionNo;
        this.prof = prof;
        this.index = index;
        this.size = 19;
        students = new ArrayList<>();
    }

    public Section(String sectionNo, int index) {
        this.sectionNo = sectionNo;
        this.index = index;
        students = new ArrayList<>();
    }

    public Section(String sectionNo, String prof, int index, int size) {
        this.sectionNo = sectionNo;
        this.prof = prof;
        this.index = index;
        this.size = size;
        students = new ArrayList<>();
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public String getProf() {
        return prof;
    }

    public int getSize() {
        return size;
    }

    public boolean addStudent(Student student) {
        if (students.size() == 19) {
            //TODO: Have this throw an error
            return false;
        }
        students.add(student);
        return true;
    }

    public int getIndex() {
        return index;
    }

    public boolean equals(String sectionNo) {
        return this.sectionNo.equals(sectionNo);
    }

    public boolean equals(Section s) {
        return equals(s.getSectionNo());
    }

    public String toString() {
        return sectionNo;
    }

    public boolean hasStudent(String id) {
        for ( int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentNo().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasStudent(Student stud) {
        return students.contains(stud);
    }

    public int getNumStudents() {
        return students.size();
    }


    public ArrayList<Student> getStudents() {
        return students;
    }
}
