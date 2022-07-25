import java.util.Scanner;

public class Main {
    private static final long mainThreadId = Thread.currentThread().getId();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        int start1 = scanner.nextInt();
        int end1 = scanner.nextInt();

        int start2 = scanner.nextInt();
        int end2 = scanner.nextInt();

        RangeAdder adder1 = new RangeAdder(start1, end1);
        RangeAdder adder2 = new RangeAdder(start2, end2);
        // The run method is executed when the thread start() method is called.
        // we can call these methods on the RangeAdder class cause the class extends Thread
        // we need the join() method so that the main thread waits on the current thread to finish before continue
        // so the sum can be set before we get it.
        adder1.start();
        adder2.start();
        adder1.join();
        adder2.join();

        long partialSum1 = adder1.getSum();
        long partialSum2 = adder2.getSum();

        long sum = partialSum1 + partialSum2; 

        System.out.println(sum);

        scanner.close();
    }

    // DO NOT modify the RangeAdder class
    static class RangeAdder extends Thread {

        int start;
        int end;

        private volatile long sum = 0;

        public RangeAdder(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            final long currentId = Thread.currentThread().getId();

            if (currentId == mainThreadId) {
                throw new RuntimeException("You must start a new thread!");
            }

            long total = 0;
            for (int i = start; i <= end; i++) {
                total += i;
            }

            this.sum = total;
        }

        public long getSum() {
            return sum;
        }
    }
}