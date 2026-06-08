package casestudy3;

import java.util.Scanner;
import java.util.Stack;

public class AirlineConnectivity {

    static int[] parent;
    static Stack<int[]> history = new Stack<>();

    // Find the parent of an airport
    static int find(int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }

    // Add a flight route
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            history.push(new int[] { rootB, parent[rootB] });
            parent[rootB] = rootA;
        }
    }

    // Remove the last added route
    static void rollback() {
        if (!history.isEmpty()) {
            int[] last = history.pop();
            parent[last[0]] = last[1];
        }
    }

    // Check whether two airports are connected
    static boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("========== Airline Connectivity Analysis ==========");

        System.out.print("Enter number of airports: ");
        int n = sc.nextInt();

        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        System.out.print("Enter number of flight routes: ");
        int routes = sc.nextInt();

        for (int i = 1; i <= routes; i++) {
            System.out.print("Enter Flight Route " + i + " (Source Destination): ");
            int source = sc.nextInt();
            int destination = sc.nextInt();

            union(source, destination);
        }

        System.out.print("\nEnter airports to check connectivity: ");
        int airport1 = sc.nextInt();
        int airport2 = sc.nextInt();

        System.out.println("\nFlight routes added successfully.");
        System.out.println("\nChecking connectivity...");
        System.out.println("Connected (" + airport1 + "," + airport2 + "): "
                + connected(airport1, airport2));

        System.out.println("\nRemoving the last flight route...");
        rollback();

        System.out.println("After Rollback Connected (" + airport1 + "," + airport2 + "): "
                + connected(airport1, airport2));

        System.out.println("\nTime Complexity");
        System.out.println("-----------------------");
        System.out.println("Find      : O(log n)");
        System.out.println("Union     : O(log n)");
        System.out.println("Rollback  : O(1)");

        sc.close();
    }
}