package _12_01_2023;

public class Task1 {
    public static boolean isPalindrome(int x) {
      if(x < 0) {
          return false;
      }

      String s = x + "";
      StringBuilder stringBuilder = new StringBuilder(s);
      System.out.println(stringBuilder);
      return stringBuilder.reverse().toString().equals(s);
    }

    public static void main(String[] args) {
        //121 -> t
        //-121 -> f
        // 10 -> f
        int a = -121;
        int b = 121;
        int c = 225;
        System.out.println(isPalindrome(b));
//        System.out.println(isPalindrome(b));
//        System.out.println(isPalindrome(c));

        String ma = "ma"; //"ma"
        String ma1 = ma + "ma"; //"ma" "mama"
        String ma2 = ma1 + "!"; // "ma" "mama" "!" "mama!"

        StringBuilder sb = new StringBuilder("ma");
        sb.append("ma").append("!");
    }
}