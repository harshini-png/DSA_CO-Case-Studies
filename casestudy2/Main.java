package casestudy2;

public class Main {

    static class FenwickTree {
        int[] bit;
        int n;

        FenwickTree(int n) {
            this.n = n;
            bit = new int[n + 1];
        }

        void update(int index, int value) {
            while (index <= n) {
                bit[index] += value;
                index += index & (-index);
            }
        }

        int prefixSum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += bit[index];
                index -= index & (-index);
            }
            return sum;
        }

        int rangeSum(int left, int right) {
            return prefixSum(right) - prefixSum(left - 1);
        }
    }

    public static void main(String[] args) {

        int[] spend = { 1200, 800, 0, 2400, 1500, 600, 0, 0, 3500, 0, 1100, 950, 700, 0, 0 };

        int n = spend.length;

        FenwickTree ft = new FenwickTree(n);

        for (int i = 0; i < n; i++) {
            ft.update(i + 1, spend[i]);
        }

        System.out.println("==== HDFC NETBANKING DAILY SPEND ANALYSIS ====\n");

        System.out.println("Input Spend Array:");
        for (int x : spend) {
            System.out.print(x + " ");
        }

        System.out.println("\n");

        System.out.println("BIT Array:");
        for (int i = 1; i <= n; i++) {
            System.out.print(ft.bit[i] + " ");
        }

        System.out.println("\n");

        int left = 5;
        int right = 12;

        int prefixRight = ft.prefixSum(right);
        int prefixLeft = ft.prefixSum(left - 1);
        int answer = ft.rangeSum(left, right);

        System.out.println("Range Query:");
        System.out.println("Spend from Day " + left + " to Day " + right + "\n");

        System.out.println("Prefix(" + right + ") = " + prefixRight);
        System.out.println("Prefix(" + (left - 1) + ") = " + prefixLeft);

        System.out.println("\nTotal Spend = " + prefixRight + " - " + prefixLeft);
        System.out.println("Answer = " + answer);

        int manual = 0;
        for (int i = left - 1; i < right; i++) {
            manual += spend[i];
        }

        System.out.println("\nVerification:");
        System.out.println("Manual Sum = " + manual);

        System.out.println("\nTime Complexity:");
        System.out.println("Point Update : O(log n)");
        System.out.println("Range Query  : O(log n)");
    }
}