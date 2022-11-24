package _24_11_2022;

import java.util.ArrayList;
import java.util.List;

public class CalcSystem {
    public static void main(String[] args) {
        System.out.println(getInRadix(321654, 2));
    }
    //Таблица символов
    private static List<Character> getDigitTable() {
        ArrayList<Character> digits = new ArrayList<>();
        for (char i = '0'; i <= '9' ; i++) {
            digits.add(i);
        }
        for (char i = 'A'; i <= 'Z' ; i++) {
            digits.add(i);
        }
        return digits;
    }

    public static String getInRadix(int number, int radix) {
        List<Character> digits = getDigitTable();
        //Проверка radix на адекватность
        if(radix < 2 || radix >= digits.size() || number < 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder valueStr = new StringBuilder();
        while (number > 0) {
            valueStr.insert(0, digits.get(number % radix));
            //а сам number делим на то же основание
            number = number / radix;
        }
        //вернуть строку
        return valueStr.toString();
    }
}