package edu.lclark.sortinghat;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class SectionTest {

    private Section section;

    @Before
    public void setUp() {
        section = new Section("107-11", "Drake", 0);
    }

    @Test
    public void sectionCreated() {
        Assert.assertEquals(section.getSectionNo(), "107-11");
        Assert.assertEquals(section.getProf(), "Drake");
    }

    @Test
    public void sectionContainsStudents() {
        //TODO: Write this
        Assert.fail("Not written yet");
    }

    @Test
    public void sectionCappedAt19() {
        //TODO: Write this
        Assert.fail("Not written yet");
    }


}