package _27102022;

public class Test1 {
    public static void getMin(int[] array) {
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int j : array) {
            if (j < min) {
                min2 = min;
                min = j;
            } else if (j < min2 && j != min) {
                min2 = j;
            }
        }

        if(min2 != Integer.MAX_VALUE) {
            System.out.println("Min 2: " + min2);
        } else {
            System.out.println("Min 2 doesn't exist");
        }
        System.out.println("Min : " + min);
    }


    public static void main(String[] args) {
        int[] array = {11, 12, 55, 11, 55, 12};
        getMin(array);
    }
}
