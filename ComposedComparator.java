import java.util.Comparator;

public class ComposedComparator implements Comparator<Song>
{
    private final Comparator<Song> comparator_1;
    private final Comparator<Song> comparator_2;

    public ComposedComparator(Comparator<Song> comparator_1, Comparator<Song> comparator_2)
    {
        this.comparator_1 = comparator_1;
        this.comparator_2 = comparator_2;
    }

    public int compare(Song song_1, Song song_2)
    {
        if(comparator_1.compare(song_1, song_2) == 0)
        {
            return comparator_2.compare(song_1, song_2);
        }
        else
        {
            return comparator_1.compare(song_1, song_2);
        }
    }
}
