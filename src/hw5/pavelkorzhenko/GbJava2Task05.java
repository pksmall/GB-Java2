package pavelkorzhenko;

/**
 * @author Pavel Korzhenko
 * @version 0.1 2017/11/07
 * @task 05
 * @mark
 *
 * 1. Необходимо написать два метода, которые делают следующее:
 *  1) Создают одномерный длинный массив, например:
 *      static final int size = 10000000;
 *      static final int h = size / 2;
 *      float[] arr = new float[size];
 *  2) Заполняют этот массив единицами;
 *  3) Засекают время выполнения: long a = System.currentTimeMillis();
 *  4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 *      arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 *  5) Проверяется время окончания метода System.currentTimeMillis();
 *  6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
 *
 *  Отличие первого метода от второго:
 *      Первый просто бежит по массиву и вычисляет значения.
 *      Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
 *      Пример деления одного массива на два:
 *          System.arraycopy(arr, 0, a1, 0, h);
 *          System.arraycopy(arr, h, a2, 0, h);
 *      Пример обратной склейки:
 *          System.arraycopy(a1, 0, arr, 0, h);
 *          System.arraycopy(a2, 0, arr, h, h);
 *      Примечание:
 *          System.arraycopy() – копирует данные из одного массива в другой:
 *          System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение,
 *                          откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
 *  По замерам времени:
 *      Для первого метода надо считать время только на цикл расчета:
 *          for (int i = 0; i < size; i++) {
 *              arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 *         }
 *  Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 *
 * synchronized имеет два важных момента: это гарантия того, что только один поток выполняет секцию кода в один момент
 *              времени (взаимоисключение или mutex), и также гарантия того, что данные, изменённые одним потоком, будут
 *              видны всем другим потокам (видимость изменений).
 * volatile проще, нежели синхронизация и подходит только для контроля доступа к одиночному экземпляру или переменной
 *              примитивного типа: int, boolean... Когда переменная объявлена как volatile, любая запись её будет
 *              осуществляться прямо в память, минуя кеш. Также как и считываться будет прямо из памяти, а не из
 *              всевозможного кеша. Это значит, что все потоки будут "видеть" одно и то же значение переменной одновременно.
 *
 *  My results:
 *
 *      First run  end time: 5330 ms
 *      Second run end time: 3387 ms
 */

public class GbJava2Task05 {
    public static void main(String[] args) throws InterruptedException {
        // first run
        new FirstFillArray();

        // second run
        long starTime, endTime;
        BigArray arr = new BigArray();
        float[] a1 = new float[arr.getH()];
        float[] a2 = new float[arr.getH()];

        starTime = System.currentTimeMillis();
        // first thread
        System.arraycopy(arr.arr, 0, a1, 0, arr.getH());
        SecondFillArray fArr1 = new SecondFillArray("First", a1, 0);
        // second thread
        System.arraycopy(arr.arr, arr.getH(), a2, 0, arr.getH());
        SecondFillArray fArr2 = new SecondFillArray("Second", a2, arr.getH());

        // wait it's done && main catch exception
        fArr1.thrd.join(); fArr2.thrd.join();

        System.arraycopy(fArr1.getArrLoc(), 0, arr.arr, 0, arr.getH());
        System.arraycopy(fArr2.getArrLoc(), 0, arr.arr, arr.getH(), arr.getH());
        endTime = System.currentTimeMillis() - starTime;
        System.out.println("Second run end time: " + endTime + " ms");
        System.out.println("A65: " + arr.arr[arr.getH() + 65]);
        //arr.printArr();
    }
}
