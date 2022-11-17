package _17112022.eratosfen;

public class PrimeTest {
    public static void main(String[] args) {
//        for (int i = 1; i < 1000; i++) {
//            if (isPrime(i)) {
//                System.out.println(i);
//            }
//        }

        for (int i = 1; i < 1000; i++) {
            if (isPrimeNew(i)) {
                System.out.println(i);
            }
        }
    }

    private static boolean isPrime(int number) {
        //Если число меньше двух, то считаем что простое
        if (number < 2) {
            return false;
        }

        for (int i = 2; i * i <= number; i++) {
            //Если наше число поделилось на какое-то без остатка,
            //то это значит что число не простое
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    private static boolean isPrimeNew(int number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            //Если наше число делить без остатка на 2,
            // то оно не простое за исключением если это и есть 2
            return number == 2;
        }
        if (number % 3 == 0) {
            return number == 3;
        }
        //Теперь можно сделать цикл, который идет только по парам
        //кандидатов на простые числа и проверять делимость только на них
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                //тогда это не просто число
                return false;
            }
        }
        return true;
    }
}