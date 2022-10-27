package _27102022.lexeme;

//Метод рекурсивного спуска
//1. Лексикографический анализ
//1.1 Создать для каждого элемента(лексемы) идентификатор [+]
//1.2 Разобрать по зап частям

import java.util.ArrayList;
import java.util.List;

//2. Синтаксический анализ
//2.1 Определить "роль" каждого элемента
//2.2 Написать логику подсчета в зависимости от роли
public class Main {
    public static void main(String[] args) {
        String expressionText = "122 -1* (11 + 11)";
        //анализ строки
        List<Lexeme> lexemes = lexAnalyze(expressionText);
        //вычисление выражения
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        System.out.println(lexemeBuffer);

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
        //Создать этот самый массив
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        //Пока не дошли до конца текста
        //будем идти по строке и генерировать лексемы
        while (pos < expText.length()) {

            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DEV, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                default:
                    //тут проверяем цифры
                    //если это цифры
                    if (c <= '9' && c >= '0') {
                        //У нас должно быть представление
                        StringBuilder sb = new StringBuilder();
                        //берем символ из текста
                        //и смотрим что это за лексема и добавляем в наш массив
                        //пока не встретиться что то другое и склеиваем их в одно число
                        //и уже полученное число добавляем в массив лексем
                        do {//тк мы точно знаем что там цифра
                            sb.append(c);
                            pos++;
                            //если достигли конца строки, то брейк
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        //тип цифра относится к Намбер
                        lexemes.add(new Lexeme(LexemeType.NUMBER, c));
                    } else {
                        // а если не число? и символ не пробел?
                        if (c != ' ') {
                            //тогда у нас ошибка в выражении
                            throw new RuntimeException("Unsupported char: " + c);
                        }
                        //а если пробел то игнорим
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.END, ""));
        return lexemes;
    }

    //Вспомогательный класс
    //В данном классе мы сохраняем всю инфу нашего прохода по массиву
    public static class LexemeBuffer {
        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    //Лексикографический анализатор готов.
    //Пишем синтаксический анализатор

    /**
     * 25 + 6 * (11 - 3)
     * factor -> это число[Number] после которого (expression)
     * multdiv -> factor(* expression)
     * plusminus -> multdiv(expression)
     * expr -> consist of *
     */

    //25 + 6 * (11 +8)
    //из чего состоит наше выражение
    public static int expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        //сделать проверку на пустое выражение
        //""
        //если первая лексема это конец строки то вернем 0
        if (lexeme.type == LexemeType.END) {
            return 0;
        } else {
            //если не 0 то вернемся назад
            //и запустим +-
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    private static int factor(LexemeBuffer lexemes) {
        //читаем лексему
        Lexeme lexeme = lexemes.next();
        //проверяем ее тип
        switch (lexeme.type) {
            case NUMBER: //если это число
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET: //если левая скобка
                //верни значение из скобок
                int value = expr(lexemes);
                //25+9*(5-4)
                lexeme = lexemes.next();
                // а если нет правой скобки то выражение не верное 25+(4+6
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Some information");
                    //todo можно написать в каком именно месте ошибка
                }
                return value;
            default:
                throw new RuntimeException("Some information");

        }
    }


    public static int plusminus(LexemeBuffer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            //25+6*(8-9)
            switch (lexeme.type) {
                case OP_PLUS -> value = value + multdiv(lexemes);
                case OP_MINUS -> value = value - multdiv(lexemes);
                case END, RIGHT_BRACKET -> {
                    lexemes.back();
                    return value;
                }
                default -> throw new RuntimeException("Some information");
            }
        }
    }

    private static int multdiv(LexemeBuffer lexemes) {
        //значение первого числа
        int value = factor(lexemes);
        //25+6*9
        while (true) {
            //мы должны достать след лексему
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL -> value = value * factor(lexemes);
                case OP_DEV -> value = value / factor(lexemes);
                case END, RIGHT_BRACKET, OP_PLUS, OP_MINUS -> {
                    //25+6*8
                    //если не умножить и не разделить
                    //то возвращаем указатель назад
                    //и возвращаем позицию первого множителя
                    lexemes.back();
                    return value;
                }
                default -> throw new RuntimeException("Some information");
            }
        }
    }
}