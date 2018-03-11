package edu.lclark.sortinghat;

import java.util.ArrayList;

public class GreedyAlgorithm {

    private ArrayList<Section> sections;
    private ArrayList<Student> students;

    public GreedyAlgorithm(ArrayList<Section> sections, ArrayList<Student> students) {
        this.sections = new ArrayList<>(sections); //makes copies of the arraylists from SortingHat so as to not fuck anything up
        this.students = new ArrayList<>(students);
        run();
    }

    public void run() {

        //Goes through each student
        for (Student student : students) {

            //test print statement
//            for (Section pref : student.getPreferences()) {
////                System.out.println("Section no " + pref.getSectionNo() + " has " + pref.getStudents().size() + " students");
////            }


            //checks to see whether student is already assigned, if so continues to next student
            if (student.isAssigned()) {
                continue;
            }

            //goes through all student's preferences IF THEY HAVE THEM and tries to add student to first one possible
            for (Section preference : student.getPreferences()) {
                if (preference.addStudent(student)) {
                    break;
                }
            }
            //TODO: add student to a section even if all preferences are full?
        }

        //Goes through all students who have NO preferences and tries to add them to first section possible
        for (Student student : students) { //TODO: Only go through students with no preferences
            if (!student.isAssigned()) {
                for (Section s : sections) {
                    if (s.addStudent(student)) {
                        break;
                    }
                }
            }
        }

    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

}
