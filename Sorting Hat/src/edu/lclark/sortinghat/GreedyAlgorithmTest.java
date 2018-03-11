package edu.lclark.sortinghat;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;

public class GreedyAlgorithmTest {

    private SortingHat sortingHat;
    private GreedyAlgorithm greedy;

    private ArrayList<Section> sections;
    private ArrayList<Student> students;

    @Before
    public void setUp() {
        sortingHat = new SortingHat(new File("csvparsetestSECT.csv"), new File("csvparsetestSTUD.csv"));
        greedy = new GreedyAlgorithm(sortingHat.getSections(), sortingHat.getStudents());

        students = greedy.getStudents();
        sections = greedy.getSections();
    }

    @Test
    public void detectsNumberOfPreferences() {
        Assert.assertEquals(students.get(0).getPreferences().size(), 6);
        Assert.assertEquals(students.get(1).getPreferences().size(), 0);
        Assert.assertEquals(students.get(2).getPreferences().size(), 2);
    }

    @Test
    public void allStudentsGetSections() {
        for (Student s : students) {
            Assert.assertTrue(s.isAssigned());
            if (!s.isAssigned()) {
                System.out.println(s.getStudentNo());
            }
        }
    }

    @Test
    public void allStudentsAssignedToPreference() {
        for (Student s : students) {

//            //test print statement
//            for (Section pref : s.getPreferences()) {
//                System.out.println("Section no " + pref.getSectionNo() + " has " + pref.getStudents().size() + " students");
//            }

            //continues if no preferences
            if (s.getPreferences().isEmpty()) {
                continue;
            }

            //test area
            if (!s.getPreferences().contains(s.getAssignedSection())) {

            }

            //assertion
            Assert.assertTrue(s.getPreferences().contains(s.getAssignedSection()));

        }
    }

}