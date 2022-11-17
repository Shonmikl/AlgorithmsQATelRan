package _10112022.backpack;

import java.util.ArrayList;
import java.util.Arrays;

//2. Жадные алгоритмы
//Сначала берем самый дорогой, потом самый дорогой предмет из оставшихся итд
public class Greedy {
    public static void main(String[] args) {
        int[] weights = {3, 4, 5, 8, 9};
        int[] prices = {1, 6, 4, 7, 6};
        int maxWeight = 13;

        //я просто ищу самый дорогой предмет и пытаюсь его положить в рюкзак
        //это можно сделать если вес набора не превышает вместимость рюкзака
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        int resultWeight = 0;

        for (int i = 0; i < weights.length; i++) {
            indexes.add(i);
        }

        //Необходимо найти максимум, и далее если можно положить
        while (!indexes.isEmpty()) {
            int maxValue = prices[indexes.get(0)];
            int maxIndex = indexes.get(0);

            for (int i = 1; i < indexes.size(); i++) {
                if(maxValue < prices[indexes.get(i)]) {
                    maxValue = prices[indexes.get(i)];
                    maxIndex = indexes.get(i);
                }
            }
            resultWeight = resultWeight + weights[maxIndex];
            if(resultWeight > maxWeight) {
                break;
            }
            //тут кладем предметы в наш рюкзак до тех пор пока не опустошиться наш набор
            //или не будет превыше вес рюкзака
            result.add(maxIndex);
            indexes.remove(maxIndex);
        }

        System.out.println("Оптимальное содержание: ");
        for (Integer integer : result) {
            System.out.println(integer + 1);
        }
    }
}