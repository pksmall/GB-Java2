package obstances;

import animals.*;
import pavelkorzhenko.Obstacle;

/**
 * Write a description of class Track here.
 *
 * @author pavelKorzhenko
 * @version  2017/10/25
 */

public class Track implements Obstacle {
    private int length;

    public Track(int length) {
        this.length = length;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean doIt(Animal animal) {
        return animal.run(length);
    } 
}