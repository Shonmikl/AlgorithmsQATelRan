package _17112022.eratosfen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eratosfen {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(eratosfenPrimes(1000).toArray()));
    }

    //Будем проверять числа до 1000
    //Что бы проверить числа до 1000 достаточно
    //проверить все числа до квадратного корня от 1000
    public static List<Integer> eratosfenPrimes(int max) {
        //создать массив boolean
        boolean[] isPrime = new boolean[max];
        //заполним true
        Arrays.fill(isPrime, true);

        //Начинаем проверку с 2
        for (int i = 2; i * i < max; i++) {
            //если число простое
            if (isPrime[i]) {
                //Тогда пробегаем по все числам и делаем шаг....
                for (int j = 2 * i; j < max; j = j + i) {
                    //устанавливаем false тк оно точно не простое
                    isPrime[j] = false;
                }
            }
        }

        //После этот нам остается просто вписать в список все простые числа
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i < max; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}