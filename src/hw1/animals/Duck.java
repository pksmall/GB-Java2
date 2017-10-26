/**
 * Duck class extends from Animal
 *
 * @author PavelKorzhenko
 * @version  v.01 2017/10/26
 * @task HomeWork Java2 lesson 01
 */
package animals;

public class Duck extends Animal implements Swimable,Flyable {
    private int swim_limit;
    private int fly_limit;

    public Duck(String name) {
        this.name = name;
        this.run_limit = 10;
        swim_limit = 500;
        fly_limit = 800;
    }

    @Override
    public String voice() {
        return "krya-krya";
    }

    @Override
    public boolean swim(int length) {
        return swim_limit >= length;
    }

    @Override
    public boolean fly(float height) { return fly_limit >= height; }
}
