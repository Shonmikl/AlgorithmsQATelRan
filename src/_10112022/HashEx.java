package _10112022;

import java.util.ArrayList;
import java.util.Objects;

public class HashEx {
    String name;
    int age;
    double salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashEx hashEx)) return false;
        return age == hashEx.age && Double.compare(hashEx.salary, salary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, salary);
    }

    public static void main(String[] args) {
        HashEx e1 = new HashEx();
        HashEx e2 = new HashEx();
        int h1 = e1.hashCode();
        int h2 = e2.hashCode();
        System.out.println(h1==h2);
        ArrayList<Integer> list = new ArrayList<>(200_000);
    }
}
