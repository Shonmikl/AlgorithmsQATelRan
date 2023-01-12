package _12_01_2023;

import java.util.ArrayDeque;
import java.util.Objects;

public class Vladimir {
    private static boolean weatherPalindrome(int n) {
        if (n < 0) {
            return false;
        }

        ArrayDeque<Integer> palindrome = new ArrayDeque<>();
        while (n != 0) {
            palindrome.add(n % 10);
            n = n / 10;
        }

        boolean check = true;
        while (!palindrome.isEmpty() && check) {
            check = Objects.equals(palindrome.peekFirst(), palindrome.peekLast());
            palindrome.removeFirst();
            if (!palindrome.isEmpty()) palindrome.removeLast();
        }
        return check;
    }

    public static void main(String[] args) {
        System.out.println(weatherPalindrome(-1));
    }
}