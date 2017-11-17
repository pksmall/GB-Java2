package pavelkorzhenko;

public class FirstFillArray {
    BigArray arr = new BigArray();
    private long starTime, endTime;

    FirstFillArray() {
        starTime = System.currentTimeMillis();
        for (int i = 0; i < arr.getSize(); i++) {
            arr.arr[i] = (float)(arr.arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        endTime = System.currentTimeMillis() - starTime;
        System.out.println("First run  end time: " + endTime + " ms");
        System.out.println("A65: " + arr.arr[arr.getSize()/2 + 65]);
        //arr.printArr();
    }
}
