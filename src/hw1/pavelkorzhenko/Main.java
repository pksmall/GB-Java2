package pavelkorzhenko;

import animals.*;
import obstances.*;

public class Main {
    public static void main(String[] args) {
        Cat cat1 = new Cat("wasya");
        Track track = new Track(100);

        track.doIt(cat1);
    }
}
