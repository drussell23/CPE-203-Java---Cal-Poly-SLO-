/**
    Lab 2, CSC/CPE 203 - Static Methods vs. Instance Methods

 Orientation:
 -----------
    - This lab explores writing static methods versus instance methods in Java. Both are useful ways to get
      something done (a computation), but as we develop more complex code, there are lots of reasons to
      strongly associate a computation with a specific instance of an object (associate data and computation
      together in one object).

 Objectives:
 ----------
    - To be able to write static methods that can compute the perimeter of three different types of
      geometric shapes (Circle, Rectangle and Polygon)(part 1).

    - To be able to write instance methods to compute the perimeter of three different types of
      geometric shapes (part 2).

    - To be able write a static method to compare the perimeters of different objects (part 1 & 2).

    - To be able to write your own test cases to create geometric shapes and test the computation of
      their perimeter, and the computation of comparison of their sizes (measured as perimeter) (part 1
      & 2).

 Resources:
 ---------
    - Same as the last two labs.
    - You can get the base code on Polylearn.

 Part 1: Classes and Overload Methods:
 ------------------------------------
    - In the provided "part 1" subdirectory, implement the following classes with these general requirements.
        * Each instance variable must be "private" and "final".

        * Provide the appropriate "accessor"/"getter" methods for the instance variables. (see
      PartOneTestCases.java for names)

        * You may add methods beyond those required, but any such additional method must be "private".
 **/
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartOneTestCases
{
    public static final double DELTA = 0.00001;


    @Test
    public void testPerimRect() {
        double d = Util.perimeter(new Rectangle(new Point(-1,0),new Point(0,-2)));
        assertEquals(6, d, DELTA);
    }


    @Test
    public void testPerimCirc() {
        double d1 = Util.perimeter(new Circle(new Point(1, 2), 2));
        assertEquals(Math.PI*4, d1, DELTA);

        double d2 = Util.perimeter(new Circle(new Point(2, 4), 8));
        assertEquals(Math.PI*16, d2, DELTA);

    }


    @Test
    public void testPerimPoly() {
        List < Point >points = new ArrayList < Point >();
        points.add(new Point(0, 0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        double d = Util.perimeter(new Polygon(points));
        assertEquals(12.0, d, DELTA);
    }

    @Test
    public void testBiggerShape()
    {

        List<Point> point = Arrays.asList(new Point(0, 0), new Point(3, 1), new Point(1, 4), new Point(-1, 4));

        Circle c1 = new Circle(new Point(1.0, 1.0), 1.0);
        Rectangle R1 = new Rectangle(new Point(-1.0, 2.0), new Point(1.0,-1.6 ));
        Polygon p1 = new Polygon(point);

        int  n = (int)Bigger.whichIsBigger(c1,R1,p1);

        switch (n)
        {

            case -1:
                System.out.println("Circle has biggest perimeter");
                break;

            case 1:
                System.out.println("Rectangle has biggest perimeter");
                break;

            default:
                System.out.println("polygon has biggest perimeter");
                break;
        }


    }


    @Test
    public void testCircleImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getCenter", "getRadius");

        final List<Class> expectedMethodReturns = Arrays.asList(
                Point.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[0], new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testRectangleImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getTopLeft", "getBottomRight");

        final List<Class> expectedMethodReturns = Arrays.asList(
                Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[0], new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testPolygonImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "getPoints");

        final List<Class> expectedMethodReturns = Arrays.asList(
                List.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[][] {new Class[0]});

        verifyImplSpecifics(Polygon.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    @Test
    public void testUtilImplSpecifics()
            throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
                "perimeter", "perimeter", "perimeter");

        final List<Class> expectedMethodReturns = Arrays.asList(
                double.class, double.class, double.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
                new Class[] {Circle.class},
                new Class[] {Polygon.class},
                new Class[] {Rectangle.class});

        verifyImplSpecifics(Util.class, expectedMethodNames,
                expectedMethodReturns, expectedMethodParameters);
    }

    private static void verifyImplSpecifics(
            final Class<?> clazz,
            final List<String> expectedMethodNames,
            final List<Class> expectedMethodReturns,
            final List<Class[]> expectedMethodParameters)
            throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
                0, Point.class.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
                clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
                expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
                expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
                expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                    expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }
    }
}