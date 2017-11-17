package pavelkorzhenko;

public class SecondFillArray implements Runnable {
    Thread thrd;

    private int flag;
    private float[] arrLoc;

    SecondFillArray(String name, float[] arrLoc, int flag) {
        this.flag = flag;
        this.arrLoc = arrLoc;
        thrd = new Thread(this,name);
        thrd.start();
    }

    public float[] getArrLoc() {
        return arrLoc;
    }
    public void printArr() {
        System.out.print("[ ");
        for (int i = 0; i < arrLoc.length; i++) {
            System.out.print(arrLoc[i] + ", ");
        }
        System.out.println("]");
    }

    @Override
    public void run() {
        if (this.flag == 0) {
            for (int i = 0; i < arrLoc.length; i++) {
                arrLoc[i] = (float)(arrLoc[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        } else {
            for (int i = 0; i < arrLoc.length; i++) {
                arrLoc[i] = (float) (arrLoc[i] * Math.sin(0.2f + (i + flag) / 5) * Math.cos(0.2f + (i + flag) / 5) * Math.cos(0.4f + (i + flag) / 2));
            }
        }
        //printArr();
    }
}
