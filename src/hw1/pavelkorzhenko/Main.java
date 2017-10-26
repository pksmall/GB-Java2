package pavelkorzhenko;

/**
 * Write a description of class Main here.
 *
 * @author PavelKorzhenko
 * @version  v.01 2017/10/26
 * @task HomeWork Java2 lesson 01
 */

import animals.*;

/*
 * 1. Разобраться с имеющимся кодом;
 * 2. Добавить класс Team, который будет содержать: название команды, массив из 4-х участников (т.е. в конструкторе
 *      можно сразу всех участников указывать), метод для вывода информации о членах команды прошедших дистанцию,
 *      метод вывода информации обо всех членах команды.
 * 3. Добавить класс Course (полоса препятствий), в котором будут находиться: массив препятствий, метод который будет
 *      просить команду пройти всю полосу;
 *  То есть в итоге должно быть:
 *
 *  public static void main(String[] args) {
 *      Course c = new Course(...); // Создаем полосу препятствий
 *      Team team = new Team(...); // Создаем команду
 *      c.doIt(team); // Просим команду пройти полосу
 *      team.showResults(); // Показываем результаты
 *  }
 *
*/

public class Main {

    public static void main(String[] args) {
        // our team
        Animal[] zooTeam = {new Cat("Murzik"), new Hen("Izzy"), new Hippo("Hippopo"), new Duck("Donald")};

        Course c = new Course(); // Создаем полосу препятствий
        Team team = new Team("Super Team", zooTeam); // Создаем команду
        c.doIt(team); // Просим команду пройти полосу
        System.out.println();
        team.showResults(); // Показываем результаты
    }
}
