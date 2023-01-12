package _12_01_2023;

public class Task3 {
    public static int reverse(int x) {
        int sign = 1;
        int result = 0;
        int max = Integer.MAX_VALUE;

        if (x < 0) {
            x = x * -1;
            sign = -1;
        }

        while (x > 0) {
            int mod = x % 10;

            if (max / 10 > result || (max / 10 == result && max % 10 >= mod)) {
                result = result * 10 + mod;
            } else {
                return 0;
            }
            x = x / 10;
        }
        return result * sign;
    }

    public static void main(String[] args) {
        // 123 -> 321
        // -255 -> -552
        // 120 -> 21
        // -150 -> -51
        //-21 -> -12
        int a = 123;
        int b = -255;
        int c = 120;
        int d = -150;

        System.out.println(reverse(10000));
//        System.out.println(reverse(b));
//        System.out.println(reverse(c));
//        System.out.println(reverse(d));
    }
}