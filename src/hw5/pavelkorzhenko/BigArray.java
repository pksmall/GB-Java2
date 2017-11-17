package pavelkorzhenko;

public class BigArray {
    private static final int size = 10000000;
    private static final int h = size / 2;
    public float[] arr = new float[size];

    BigArray() {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    public void printArr() {
        for (int i = 0; i < size; i++) {
            System.out.println("I: " + i + " " + arr[i]);
        }
    }

    public static int getH() {
        return h;
    }

    public static int getSize() {
        return size;
    }
}
