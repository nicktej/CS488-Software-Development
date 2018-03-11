package edu.lclark.sortinghat;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;


public class StudentTest {

    Section pref1 = new Section("107-11", "Ely", 0);
    Section pref2 = new Section("107-12", "Mache", 1);
    Section pref3 = new Section("107-13", "Harmon", 2);

    private ArrayList<Section> preferences;
    private ArrayList<String> prevProfs;
    private Student student;

    @Before
    public void setUp() {
        preferences = new ArrayList<>();
        preferences.add(pref1);
        preferences.add(pref2);
        preferences.add(pref3);
        prevProfs = new ArrayList<String>();
        prevProfs.add("Drake");
        student = new Student("420", preferences, true, false, prevProfs);
    }

    @Test
    public void studentCreated() {
        Assert.assertEquals(student.getStudentNo(), 420);
        Assert.assertEquals(student.getPreferences(), preferences);
        Assert.assertEquals(student.getGender(), true);
        Assert.assertEquals(student.getAthlete(), false);
        Assert.assertEquals(student.getPrevProfs().get(0), "Drake");
    }
}