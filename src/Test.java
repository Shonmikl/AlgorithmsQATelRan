import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Test {
    private static boolean palindrome(String s) {
        for (int i = 0; i < s.length(); i++) {
            int right = s.length() - (1 + i);
            while (i != right) {
                if (s.charAt(i) != s.charAt(s.length() - (i + 1))) {
                    return false;
                }
                break;
            }
        }
        return true;
    }

    private static void getMin(int[] minArr) {
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int j : minArr) {
            if (min > j) {
                min2 = min;
                min = j;
            } else if (j < min2 && j != min) {
                min2 = j;
            }
        }
        System.out.println("Min = " + min);
        System.out.println("Min2 = " + min2);
    }

    public static String getLetterCount(String str) {
        Map<Character, Integer> lettersCount = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (lettersCount.containsKey(str.charAt(i))) {
                int value = lettersCount.get(str.charAt(i));
                value++;
                lettersCount.put(str.charAt(i), value);
            } else {
                lettersCount.put(str.charAt(i), 1);
            }
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> m : lettersCount.entrySet()) {
            result.append(m.getValue()).append(m.getKey());
        }
        return result.toString();
    }

    private static long getFibonacciStreams(int element) {
        var result =
                Stream.iterate(new long[]{0, 1}, arr -> new long[]{arr[1], arr[0] + arr[1]})
                        .limit(element)
                        .map(y -> y[0])
                        .max(Long::compareTo);
        return result.orElseThrow(IllegalArgumentException::new);

    }

    private static long getFibonacciLoop(int digit) {
        return 0;
    }

    private static long getFibonacciRecursive(int digit) {
        return 0;
    }

    public static void main(String[] args) {
        //System.out.println(getLetterCount("AASDDAFFGGHJYYY"));
        System.out.println(getFibonacciStreams(10));
    }
}