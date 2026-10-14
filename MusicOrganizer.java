import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played, including random playback
 * and a shuffle that plays every track exactly once.
 *
 * @author David J. Barnes and Michael Kölling
 * Modified by Jordy Robalino
 * @version 2026.10.13
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;

    /**
     * Create a MusicOrganizer.
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary("../audio");
        System.out.println("Music library loaded. "
                           + getNumberOfTracks() + " tracks.");
        System.out.println();
    }

    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }

    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }

    /**
     * Play a sample of a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.playSample(track.getFilename());
            System.out.println("Now playing: "
                               + track.getArtist() + " - "
                               + track.getTitle());
        }
    }

    /**
     * Exercise 4.43: Play a randomly selected track.
     */
    public void playRandomTrack()
    {
        if(tracks.isEmpty()) {
            System.out.println("There are no tracks to play.");
            return;
        }

        Random random = new Random();
        int index = random.nextInt(tracks.size());
        playTrack(index);
    }

    /**
     * Exercise 4.45: Play every track exactly once
     * in a random order.
     */
    public void playAllRandom()
    {
        if(tracks.isEmpty()) {
            System.out.println("There are no tracks to play.");
            return;
        }

        Random random = new Random();
        ArrayList<Integer> remaining = new ArrayList<>();

        // Store each track index in a temporary list.
        for(int index = 0; index < tracks.size(); index++) {
            remaining.add(index);
        }

        // Remove each selected index to prevent repeats.
        while(!remaining.isEmpty()) {
            int position = random.nextInt(remaining.size());
            int trackIndex = remaining.remove(position);
            playTrack(trackIndex);
        }
    }

    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }

    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }

    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }

    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }

    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }

    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }

    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        boolean valid;

        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    /**
     * Read music tracks from the given folder.
     * @param folderName The folder containing the MP3 files.
     */
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks =
            reader.readTracks(folderName, ".mp3");

        // Put all the tracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
}