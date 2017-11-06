package pavelkorzhenko.obstances;

/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import animals.Animal;
import animals.Swimable;
import pavelkorzhenko.Obstacle;

public class Water implements Obstacle {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    public boolean doIt(Animal animal) {
        if (animal instanceof Swimable)
            return ((Swimable) animal).swim(length);
        else
            return false;
    }
}