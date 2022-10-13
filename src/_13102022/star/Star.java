package _13102022.star;

/**
 * way(n, m) = way(n-1, m) + way(n, m-1);
 */
public class Star {
    public static int way(int n, int m) {
        if (n < 1 || m < 1) return 0;
        if (n == 1 && m == 1) return 1;
        return way(n - 1, m) + way(n, m - 1);
    }

    public static void main(String[] args) {
        System.out.println(way(3, 3));
    }
}