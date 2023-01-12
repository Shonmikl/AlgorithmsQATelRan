package _12_01_2023;

import java.util.*;

public class Task4 {
    public static String reversWords(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        List<String> result = new ArrayList<>();

        while (st.hasMoreTokens()) {
            String curr = st.nextToken();
            result.add(curr);
        }

        Collections.reverse(result);
        StringBuilder ss = new StringBuilder();
        for (String curr : result) {
            ss.append(curr).append(" ");
        }

        return ss.toString().trim();
    }
    public static void main(String[] args) {
        String s = "Collapsed cryptocurrency exchange " +
                "FTX has located more than $5bn (£4.1bn) " +
                "of assets, an attorney for the firm says";

        System.out.println(reversWords(s));
    }
}