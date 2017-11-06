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
    private String results;

    public Team(String name, Animal[] animals){
        this.name = name;
        this.animals = animals;
        this.results = "";
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public String getName() {
        return name;
    }

    public void doIt(Obstacle obStackle) {
        results +=  "Obstackle: " + obStackle.getName() + "\n";
        for(Animal animal: animals) {
            results += " " + animal + " " + obStackle.doIt(animal) + "\n";
        }
    }

    public String showResults() {
        return results;
    }
}
