package pavelkorzhenko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/01
 * @task 03
 * @mark
 *
 * 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список
 *    уникальных слов, из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.
 *    HardWork: массив брать из файла.
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров. В этот
 *    телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона
 *    по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 *    тогда при запросе такой фамилии должны выводиться все телефоны.
 *    Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в телефонную запись добавлять еще
 *    дополнительные поля (имя, отчество, адрес), делать взаимодействие с пользователем через консоль и т.д.). Консоль
 *    желательно не использовать (в том числе Scanner), тестировать просто из метода main() прописывая add() и get().
 */

public class GbJava2Task03 {

    public static void main(String[] args) throws CloneNotSupportedException {

        List<String> words = new ArrayList<>();
        HashMap<String, Integer> wordsCount = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt")))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                // разделаяем полученую строку из файла
                // \W+ слово разделенная пробелом или знаками применания
                // и все слова больше 2-х символов засовываем в массив и хешмап.
                for(String endWord: sCurrentLine.split("\\W+", sCurrentLine.length())) {
                    if (endWord.length() > 2) {
                        words.add(endWord);
                        if (!wordsCount.containsKey(endWord)) wordsCount.put(endWord, 0);
                        wordsCount.put(endWord, wordsCount.get(endWord) + 1);
                    }
                }
            }
            // общий вид массива
            // оно вообще не нужно (сам массив лист), но красиво печатется.
            // для наглядности
            System.out.println(words);

            // выводим уникальные слова и кол-во их в массиве.
            // не учитываем регист , можно добавить перевод всех в нижний.
            // но в условии задачи ничего про это не сказано.
            for (String endWord : wordsCount.keySet()) {
                System.out.println(endWord + " " + wordsCount.get(endWord));
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        System.out.println();

        // address book
        AddressBook addressBook = new AddressBook();
        addressBook.addRecord("594859844", "Путин Владимир");
        addressBook.addRecord("459059623", "Медведем Дмитрий");
        addressBook.addRecord("234950912", "Песков Александар");
        addressBook.addRecord("594859682", "Медведем Дмитрий");
        System.out.println(addressBook.getRecord("Медведем Дмитрий"));
        //System.out.println(addressBook.allRecords());
    }
}
