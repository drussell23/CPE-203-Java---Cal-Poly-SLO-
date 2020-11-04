import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;





public class TestCases_Comparator
{
    private static final Song[] songs = new Song[] {
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Gerry Rafferty", "Baker Street", 1978)
    };

    @Test
    public void testArtistComparator()
    {
        ArtistComparator comparator_Artist = new ArtistComparator();

        boolean [] test_Compare = {comparator_Artist.compare(songs[0], songs[1]) < 0,
                comparator_Artist.compare(songs[5], songs[4]) > 0,
                comparator_Artist.compare(songs[3], songs[7]) == 0};

        assertTrue(test_Compare[0]);
        assertTrue(test_Compare[1]);
        assertTrue(test_Compare[2]);
    }

    @Test
    public void testLambdaTitleComparator()
    {
        Comparator<Song> lambda = Comparator.comparing(Song::getArtist);
        boolean [] test_Compare = { lambda.compare(songs[3], songs[0]) > 0,
                lambda.compare(songs[5], songs[6]) < 0,
                lambda.compare(songs[0], songs[7]) < 0};

        assertTrue(test_Compare[0]);
        assertTrue(test_Compare[1]);
        assertTrue(test_Compare[2]);
    }

    @Test
    public void testYearExtractorComparator()
    {
        Comparator<Song> comparator_Key_Extractor = Comparator.comparingInt(Song::getYear);
        comparator_Key_Extractor = comparator_Key_Extractor.reversed();
        int [] compare_Test = {
                 comparator_Key_Extractor.compare(songs[2], songs[3]),
                comparator_Key_Extractor.compare(songs[7], songs[6]),
        };
        boolean [] test_Compare = { compare_Test[0] < 0, compare_Test[1] < 0 };

        assertTrue(test_Compare[0]);
        assertTrue(test_Compare[1]);
    }

    @Test
    public void testComposedComparator()
    {
        Comparator<Song> comparator_1 = Comparator.comparing(Song::getTitle);
        Comparator<Song> comparator_2 = Comparator.comparing(Song::getYear);

        ComposedComparator comparator = new ComposedComparator(comparator_1, comparator_2);

        int [] compare_Test = { comparator.compare(songs[7], songs[6]), comparator.compare(songs[5], songs[7]) };
        boolean [] test_Compare = { compare_Test[0] < 0, compare_Test[1] > 0 };

        assertTrue(test_Compare[0]);
        assertTrue(test_Compare[1]);
    }

    @Test
    public void testThenComparing()
    {
        Comparator<Song> comparator_Key_Extractor = (Comparator.comparing(Song::getTitle)).thenComparing(Song::getArtist);
        int [] compare_Test = { comparator_Key_Extractor.compare(songs[7], songs[6]), comparator_Key_Extractor.compare(songs[5], songs[7]) };
        boolean [] test_Compare = { compare_Test[0] < 0, compare_Test[1] < 0 };

        assertTrue(test_Compare[0]);
        assertTrue(test_Compare[1]);
    }

    @Test
    public void runSort()
    {
        Comparator<Song> comparator_Sort = ((Comparator.comparing(Song::getArtist)).
                thenComparing(Song::getTitle)).
                thenComparing(Song::getYear);

        List<Song> songList = new ArrayList<>(Arrays.asList(songs));
        List<Song> expectedList = Arrays.asList(
                new Song("Avett Brothers", "Talk on Indolence", 2006),
                new Song("City and Colour", "Sleeping Sickness", 2007),
                new Song("Decemberists", "The Mariner's Revenge Song", 2005),
                new Song("Foo Fighters", "Baker Street", 1997),
                new Song("Gerry Rafferty", "Baker Street", 1978),
                new Song("Gerry Rafferty", "Baker Street", 1998),
                new Song("Queen", "Bohemian Rhapsody", 1975),
                new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
        );
        // pass comparator here
        songList.sort( comparator_Sort);
        assertEquals(songList, expectedList);
    }
}
