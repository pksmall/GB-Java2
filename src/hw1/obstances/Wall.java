package obstances;
import animals.*;
import pavelkorzhenko.Obstacle;

/**
 * Write a description of class Wall here.
 *
 * @author pavelKorzhenko
 * @version  2017/10/25
 */

public class Wall implements Obstacle {
    private float height;

    public Wall(float height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean doIt(Animal animal) {
        if (animal instanceof Flyable) {
            return ((Flyable) animal).fly(height);
        } else
            return animal instanceof Jumpable && ((Jumpable) animal).jump(height);
    }
}