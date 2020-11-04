import org.junit.Test;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestCases
{
    @Test
    public void testExercise1()
    {
        final CourseSection one = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 40), LocalTime.of(11, 0));
        final CourseSection two = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 40), LocalTime.of(11, 0));

        assertEquals(one, two);
        assertEquals(two, one);
    }

    @Test
    public void testExercise2()
    {
        final CourseSection one = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 10), LocalTime.of(10, 0));
        final CourseSection two = new CourseSection("CSC", "203", 35,
                LocalTime.of(1, 10), null);

        assertNotEquals(one.hashCode(), two.hashCode());
    }

    @Test
    public void testExercise3()
    {
        final CourseSection one = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 40), LocalTime.of(11, 0));
        final CourseSection two = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 40), LocalTime.of(11, 0));

        assertEquals(one.hashCode(), two.hashCode());
    }

    @Test
    public void testExercise4()
    {
        final CourseSection one = new CourseSection("CSC", "203", 35,
                LocalTime.of(9, 10), LocalTime.of(10, 0));
        final CourseSection two = new CourseSection("CSC", "203", 34,
                LocalTime.of(9, 10), LocalTime.of(10, 0));

        assertNotEquals(one.hashCode(), two.hashCode());
    }

    //==============================================================//
//Student portion
    private static final List<CourseSection> lst = Arrays.asList(
            new CourseSection("CSC", "203", 35, LocalTime.of(9, 40),
                    LocalTime.of(11, 0)),
            new CourseSection("CSC", "203", 35, LocalTime.of(9, 40),
                    LocalTime.of(11, 0))
    );

    @Test
    public void testExercise5()
    {
        final Student one = new Student("Bob", "Jerry", 35, lst);
        final Student two = new Student("Bob", "Jerry", 35, lst);

        assertEquals(one, two);
        assertEquals(two, one);
    }

    @Test
    public void testExercise6()
    {
        final Student one = new Student("Bob", "Jerry", 35, lst);
        final Student two = new Student("Bob", "Jerry", 10, lst);

        assertNotEquals(one, two);
        assertNotEquals(two, one);
    }

    @Test
    public void testExercise7()
    {
        final Student one = new Student("Bob", "Jerry", 35, lst);
        final Student two = new Student("Bob", "Jerry", 35, lst);

        assertEquals(one.hashCode(), two.hashCode());
    }

    @Test
    public void testExercise8()
    {
        final Student one = new Student("Bob", "Jerry", 35, lst);
        final Student two = new Student("Bob", "Jerry", 10001, lst);

        assertNotEquals(one.hashCode(), two.hashCode());
    }
}