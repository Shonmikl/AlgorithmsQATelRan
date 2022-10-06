import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = {20, 19, 18, 1, 16};

        System.out.println(Arrays.toString(array));
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] array) {

        int arrayLength = array.length;
        if (arrayLength == 1) return;

        int mid = arrayLength / 2;
        int[] leftArray = new int[mid];
        int[] rightArray = new int[arrayLength - mid];

        for (int i = 0; i < mid; i++) {
            leftArray[i] = array[i];
        }
        for (int i = mid; i < arrayLength; i++) {
            rightArray[i - mid] = array[i];
        }

        sort(leftArray);
        sort(rightArray);
        merge(array, leftArray, rightArray);


    }

    private static void merge(int[] array, int[] leftArray, int[] rightArray) {
        int leftLengthArray = leftArray.length;
        int rightLengthArray = rightArray.length;

        int l = 0;
        int r = 0;

        int idx = 0;

        while (l < leftLengthArray && r < rightLengthArray) {
            if (leftArray[l] < rightArray[r]) {
                array[idx] = leftArray[l];
                l++;
                idx++;
            } else {
                array[idx] = rightArray[r];
                r++;
                idx++;
            }
        }
        for (int i = l; i < leftLengthArray; i++) {
            array[idx++] = leftArray[i];
        }

        for (int i = r; i < rightLengthArray; i++) {
            array[idx++] = rightArray[r];
        }
    }
}