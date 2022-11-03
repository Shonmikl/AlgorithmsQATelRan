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
        String expressionText = "122 - 1 * (11* 2)";
        List<Lexeme> lexemes = lexAnalyze(expressionText);
        //после всего кода запустим
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        System.out.println(expr(lexemeBuffer));
    }

    //1. Тип Лексем
    public enum LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
        NUMBER,
        END;
    }

    //2. Класс для представления отдельной лексемы
    public static class Lexeme {
        LexemeType type; //тип лексемы
        String value; // чем является в тексте

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
    //5. Вспомогательный класс
    //тут мы сохраним всю инфу прохода по массиву
    public static class LexemeBuffer {
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        //получаем лексемы
        public Lexeme next() {
            return lexemes.get(pos++);
        }

        //если надо вернуться назад в массиве
        public void back() {
            pos--;
        }

        //какая текущая позиция
        public int getPos() {
            return pos;
        }
    }

    //3. Функция лексического анализа
    //принимает строку с выражением и выдает массив лексем

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        //пока не дошли до конца текста
        //будем идти по строке и генерировать лексемы
        while (pos< expText.length()) {
            //берем символ из текста и смотрим что это
            // и добавляем в массив
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    //увеличиваем позицию в строке на единицу
                    pos++;
                    //прерываем цикл и начинаем новый
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    //далее проверяем на цифры
                    if (c <= '9' && c >= '0') {
                        //создаем стринг билдер куда мы будем добавлять символы
                        StringBuilder sb = new StringBuilder();
                        //смысл цикла что мы выписываем все цифры и добавляем в массив
                        //лексем пока не встретиться что то другое и склеиваем их в одно число
                        //после этого число добавляем в массив лексем
                        do {//используем do тк знаем что тут цифра
                            sb.append(c);
                            pos++;
                            //если достигли конца строки то брейк
                            if (pos >= expText.length()) {
                                break;
                            }
                            //достаем след символ из строки
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        //тип лексем это намбер и текст кторый ему соответствует
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        //а если не число
                        //если символ не пробел
                        if (c != ' ') {
                            //то в нашем выражении ошибка и надо прерывать анализ
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        //а если пробел то после его игнорим
                        pos++;
                    }
            }
        }
        //в самом конце добавим лексему конца
        //и вернем массив лексем
        lexemes.add(new Lexeme(LexemeType.END, ""));
        return lexemes;
    }

    //4. Лексикографический анализатор написали теперь можно написать синтаксический
    //и вычислять выражение

    //6. напишем методы

    public static int expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        //сделаем проверку на пустое выражение
        //(без него можно и обойтись)
        //если первая лексема конец строки то вернем 0
        if (lexeme.type == LexemeType.END) {
            return 0;
        } else {
            //иначе вернемся назад
            //и запустим вычисления +-
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    //6.3
    public static int plusminus(LexemeBuffer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS -> value += multdiv(lexemes);
                case OP_MINUS -> value -= multdiv(lexemes);
                case END, RIGHT_BRACKET -> {
                    lexemes.back();
                    return value;
                }
                default -> throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
            }
        }
    }

    //6.2
    public static int multdiv(LexemeBuffer lexemes) {
        //значение первого фактора
        int value = factor(lexemes);
        while (true) {
            //достаем след лексему
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL -> value *= factor(lexemes);
                case OP_DIV -> value /= factor(lexemes);
                case END, RIGHT_BRACKET, OP_PLUS, OP_MINUS -> {

                    //если не умножить и не разделить то возвращаем указатель назад
                    // и возвращаем позицию первого множителя
                    lexemes.back();
                    return value;
                }
                default -> throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
            }
        }
    }
    //6.1 напишем методы

    public static int factor(LexemeBuffer lexemes) {
        //читаем лексему
        Lexeme lexeme = lexemes.next();
        //проверяем ее тип
        switch (lexeme.type) {
            case NUMBER: //если номер то возвращаем само число
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET:
                //если левая скобка то это выражение
                int value = expr(lexemes);
                lexeme = lexemes.next();
                //если нет правой скобки то выражение не верно написано
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            //и пишем в какой позиции у нас проблемы
                            + " at position: " + lexemes.getPos());

                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
        }
    }
}