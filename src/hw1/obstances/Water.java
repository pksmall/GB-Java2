package obstances;

import animals.*;
import pavelkorzhenko.Obstacle;

/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Water implements Obstacle {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean doIt(Animal animal) {
        return animal instanceof Swimable && ((Swimable) animal).swim(length);
    }
}