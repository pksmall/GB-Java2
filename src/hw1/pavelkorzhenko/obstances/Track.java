package pavelkorzhenko.obstances;

/**
 * Write a description of class Track here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import animals.Animal;
import pavelkorzhenko.Obstacle;

public class Track implements Obstacle{
    private int length;

    public Track(int length) {
        this.length = length;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    public boolean doIt(Animal animal) {
        return animal.run(length);
    } 
}