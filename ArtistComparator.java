import java.util.Comparator;

public class ArtistComparator implements Comparator<Song>
{
    public int compare(Song song_1, Song song_2)
    {
        return song_1.getArtist().compareTo(song_2.getArtist());
    }
}
