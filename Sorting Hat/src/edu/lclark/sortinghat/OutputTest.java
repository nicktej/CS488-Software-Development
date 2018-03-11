package edu.lclark.sortinghat;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;


public class OutputTest {

    private SortingHat sortingHat;
    private GreedyAlgorithm greedy;
    private ArrayList<Section> sections;
    private ArrayList<Student> students;
    private Output output;

    @Before
    public void setUp() {
        sortingHat = new SortingHat(new File("csvparsetestSECT.csv"), new File("csvparsetestSTUD.csv"));
        sortingHat.run();

        greedy = new GreedyAlgorithm(sortingHat.getSections(), sortingHat.getStudents());
        students = greedy.getStudents();
        sections = greedy.getSections();
        output = new Output(sections, students);
    }

    @Test
    public void fileIsActuallyCreated() {
        // TODO: do this
        File file = new File ("eanddsorted.csv");
        Assert.assertTrue(file.exists());

    }


}