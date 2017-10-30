package pavelkorzhenko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/10/30
 * @task 02
 * @mark
 *
 * 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива
 *    другого размера необходимо бросить исключение ArrayIndexOutOfBoundsException.
 * 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в
 *    каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
 *    должно быть брошено исключение ArithmeticException, с детализацией в какой именно ячейке лежат неверные данные.
 * 3. В методе main() вызвать полученный метод, обработать возможные исключения ArithmeticException и
 *    ArrayIndexOutOfBoundsException, и вывести результат расчета.
 * 4. * Вариант повышенной сложности: данные берутся из текстового файла.
 */
public class GbJava2Task02 {
    public static final int SIZE = 4;
    public static final String REGEXOFARRAY = " ";
    public static Random random = new Random();

    public static void main(String[] args) {
        String[][] ArrayOfWrongElements = new String[4][6];
        String[][] ArrayOfElements = new String[SIZE][SIZE];

        for (int i = 0; i < ArrayOfWrongElements.length; i++) {
            for (int j = 0; j < ArrayOfWrongElements[i].length; j++) {
                ArrayOfWrongElements[i][j] = Integer.toString(random.nextInt() * 10);
            }

        }
        for (int i = 0; i < ArrayOfElements.length; i++) {
            for (int j = 0; j < ArrayOfElements[i].length; j++) {
                // bad string to int
                if (i == 3 && j == 2) {
                    ArrayOfElements[i][j] = "bad guy.";
                } else {
                    ArrayOfElements[i][j] = Integer.toString(random.nextInt() * 10);
                }
            }
        }

        try {
            System.out.println(sumArrayElements(ArrayOfWrongElements));
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            try {
                System.out.println(sumArrayElements(ArrayOfElements));
            } catch (ArithmeticException exs) {
                System.out.println(exs);
                try {
                    ArrayOfElements = arrayToFile("arrayfile.txt");
                    try {
                        System.out.println(sumArrayElements(ArrayOfElements));
                    } catch (ArithmeticException nbex) {
                        System.out.println(nbex);
                    }
                } catch (IOException ioex) {
                    System.out.println(ioex);
                }
            }
        } finally {
            System.out.println("The End.");
        }
    }

    // file to array
    public static String[][] arrayToFile(String filename) throws IOException {
        String[][] array = new String[SIZE][SIZE];

        File file = new File(filename);
        System.out.println("File: " + file.getAbsoluteFile());

        FileReader arrFile = new FileReader(file.getAbsoluteFile());
        BufferedReader in = new BufferedReader(arrFile);

        String s = in.readLine();
        //System.out.println("S: " + s);
        int i = 0;
        do {
            array[i++] = s.split(REGEXOFARRAY, SIZE);
            //System.out.println(java.util.Arrays.toString(s.split(REGEXOFARRAY, SIZE)));
            //System.out.println("S: " + s);
            s = in.readLine();
        } while (s != null);

        arrFile.close();

        return array;
    }

    // sum of array elements
    public static int sumArrayElements(String[][] array) throws NumberFormatException,ArrayIndexOutOfBoundsException,ArithmeticException {
        int sumOfElements = 0;

        // check 4x4
        if (array.length == SIZE) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].length != SIZE) {
                    throw new ArrayIndexOutOfBoundsException("Array index " + i + " has wrong length.");
                }
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Array index  has wrong length.");
        }

        // convert to int
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sumOfElements += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException ex) {
                    throw new ArithmeticException("Parse to int array index [" + i + "," + j + "] has failed.");
                }
            }
        }
        return sumOfElements;
    }
}
