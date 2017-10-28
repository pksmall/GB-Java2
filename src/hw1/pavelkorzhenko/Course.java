package pavelkorzhenko;

/**
 * Write a description of class Main here.
 *
 * @author PavelKorzhenko
 * @version  v.03 2017/10/26
 * @task HomeWork Java2 lesson 01
 */

import animals.Animal;
import obstances.*;

public class Course {
    private Obstacle[] obStacles;

    public Course() {
        this.obStacles = new Obstacle[]{new Rock(750), new Wall(50), new Track(100), new Water(150)};
    }

    public void doIt(Team tM) {
        for(Obstacle obStackle: obStacles) {
            tM.doIt(obStackle);
        }
    }
}
