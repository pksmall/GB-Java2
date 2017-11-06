package pavelkorzhenko;

/**
 * Write a description of interface Flyable here.
 *
 * @author PavelKorzhenko
 * @version v.01 2017/10/24
 * @task HomeWork Java2 lesson 01
 */

import animals.Animal;

public interface Obstacle {
    String getName();
    boolean doIt(Animal animal);
}
