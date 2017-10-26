package obstances;

import animals.*;
import pavelkorzhenko.Obstacle;

/**
 * Write a description of class Water here.
 *
 * @author PavelKorzhenko
 * @version v.02 2017/10/25
 * @task HomeWork Java2 lesson 01
 */

public class Rock implements Obstacle {
    private float height;

    public Rock(float height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean doIt(Animal animal) {
        return animal instanceof Flyable && ((Flyable) animal).fly(height);
    }
}
