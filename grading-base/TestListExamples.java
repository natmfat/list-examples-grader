import static org.junit.Assert.*;
import org.junit.*;
import java.util.Arrays;
import java.util.List;

class IsMoon implements StringChecker {
    public boolean checkString(String s) {
        return s.equalsIgnoreCase("moon");
    }
}

public class TestListExamples {
    @Test(timeout = 500)
    public void testFilterMoonNone() {
        assertEquals(
                Arrays.asList(),
                ListExamples.filter(
                        Arrays.asList("a", "a", "b", "c", "d"),
                        new IsMoon()));
    }

    @Test(timeout = 500)
    public void testFilterMoon() {
        assertEquals(
                Arrays.asList("moon"),
                ListExamples.filter(
                        Arrays.asList("a", "moon", "b", "c", "d"),
                        new IsMoon()));
    }

    @Test(timeout = 500)
    public void testFilterMultipleMoon() {
        assertEquals(
                Arrays.asList("moon", "moon"),
                ListExamples.filter(
                        Arrays.asList("asdfsdf", "moon", "moon", "mooo", "ooon"),
                        new IsMoon()));
    }

    @Test(timeout = 500)
    public void testMergeLeft() {
        assertEquals(
                Arrays.asList("a", "a", "b", "c", "d"),
                ListExamples.merge(
                        Arrays.asList("a", "b", "c"),
                        Arrays.asList("a", "d")));
    }

    @Test(timeout = 500)
    public void testMergeRight() {
        assertEquals(
                Arrays.asList("a", "a", "b", "c", "d"),
                ListExamples.merge(
                        Arrays.asList("a", "d"),
                        Arrays.asList("a", "b", "c")));
    }

    @Test(timeout = 500)
    public void testMergeBalanced() {
        assertEquals(
                Arrays.asList("a", "a", "b", "d"),
                ListExamples.merge(
                        Arrays.asList("a", "d"),
                        Arrays.asList("a", "b")));
    }

    @Test(timeout = 500)
    public void testMergeEqual() {
        assertEquals(
                Arrays.asList("a", "a", "a", "a", "d", "d"),
                ListExamples.merge(
                        Arrays.asList("a", "a", "d"),
                        Arrays.asList("a", "a", "d")));
    }
}
