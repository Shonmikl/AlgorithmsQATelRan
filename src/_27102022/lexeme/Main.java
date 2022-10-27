package _27102022.lexeme;

//Метод рекурсивного спуска
//1. Лексикографический анализ
//1.1 Создать для каждого элемента(лексемы) идентификатор [+]
//1.2 Разобрать по зап частям

import java.util.List;

//2. Синтаксический анализ
//2.1 Определить "роль" каждого элемента
//2.2 Написать логику подсчета в зависимости от роли
public class Main {
    public static void main(String[] args) {
        String expressionText = "122 -1* (11 + 11 * 11)";

    }

    //Тип лексем
    public enum LexemeType {
        LEFT_BRACKET,
        RIGHT_BRACKET,
        OP_PLUS,
        OP_MINUS,
        OP_MUL,
        OP_DEV,
        NUMBER,
        END
    }

    //Класс для представления лексем
    // + для мэтчинга Стринги и LexemeType
    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    //Функция лексического анализа
    //Принимает строку с выражением и возвращает массив лексем
    public static List<Lexeme> lexAnalyze(String expText) {

    }
}


