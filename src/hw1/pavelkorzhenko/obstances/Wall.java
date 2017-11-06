package pavelkorzhenko.obstances;

/**
 * Write a description of class Wall here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import animals.Animal;
import animals.Jumpable;
import pavelkorzhenko.Obstacle;

public class Wall implements Obstacle {
    private float height;

    public Wall(float height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    public boolean doIt(Animal animal) {
        if (animal instanceof Jumpable)
            return ((Jumpable) animal).jump(height);
        else
            return false;
    }
}