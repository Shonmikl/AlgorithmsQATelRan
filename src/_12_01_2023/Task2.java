package _12_01_2023;

public class Task2 {
    private static boolean isPalindromeString(String s) {
        if(s.length() == 1 || s.length() == 0) {
            return true;
        }

        if(s.charAt(0) == s.charAt(s.length()-1)) {
            return isPalindromeString(s.substring(1, s.length()-1));
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "MAMA";
        String sq = "ATAATA";
        String sw = "ATAWWWATA";

        System.out.println(isPalindromeString(s));
        System.out.println(isPalindromeString(sq));
        System.out.println(isPalindromeString(sw));
    }
}
