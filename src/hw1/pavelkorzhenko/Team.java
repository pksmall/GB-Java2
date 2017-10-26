package pavelkorzhenko;

/**
 * Write a description of class Main here.
 *
 * @author PavelKorzhenko
 * @version v.03 2017/10/26
 * @task HomeWork Java2 lesson 01
 */
import animals.*;

public class Team {
    private String name;
    private Animal[] animals;

    public Team(String name, Animal[] animals){
        this.name = name;
        this.animals = animals;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public void showResults() {
        System.out.println("Command Name: " + name + " got results:");
        Course c = new Course();
        c.doIt(this);
    }
}
