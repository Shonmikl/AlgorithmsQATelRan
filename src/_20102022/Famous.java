package _20102022;

//Ищем знаменитость
//Все могут знать всех и не знать никого
//Знаменитость знают все, а САМА знаменитость не знает никого
public class Famous {
    Famous getFamous(Famous[] persons) {
        Famous f = null;
        int first = 0;
        int last = persons.length - 1;

        while (first != last) {
            if (persons[first].knows(persons[last])) {
                first++;
            } else last--;

            //first == last
            for (int i = 0; i < persons.length; i++) {
                if (i != first &&
                        (persons[i].knows(persons[first]) || persons[first].knows(persons[i]))) {
                    f = null;
                }
                f = persons[first];
            }
        }
        return f;
    }

    private boolean knows(Famous person) {
        //code......
        return true;
    }
}