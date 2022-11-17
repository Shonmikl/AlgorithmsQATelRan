package _17112022.bitMoving;

import java.util.BitSet;

public class Main {
    public static void main(String[] args) {


    //как записывается в двоичной системе число
    int i = 0b000_00100;
        System.out.println(i);
    //если хотим распечатать в двоичной
        System.out.println(Integer.toBinaryString(i));

        System.out.println("---побитовый сдвиг вправо---"); // аналогичен делению на два
        System.out.println("в десятичном виде: " + (i >> 1));
        System.out.println("в двоичном виде: " + binaryStr(i >> 1));

        System.out.println("---побитовый сдвиг влево---");// аналогичен умножению на 2
        System.out.println("в десятичном виде: " + (i << 1));
        System.out.println("в двоичном виде: " + binaryStr(i << 1));

    /**
     * Такт процессора
     * Быстродействие компьютера определяется тактовой частотой его процессора.
     * Тактовая частота — количество тактов (соответственно и исполняемых команд) за секунду.
     *
     * Частота нынешних процессоров измеряется в ГГц (Гигагерцы).
     * 1 ГГц = 10⁹ Гц — миллиард операций в секунду.
     * Побитовая операция - это 1 такт
     */

        System.out.println("Быстро умножить число на 4: " + (i << 2));
        System.out.println("Быстро поделить число на 4: " + (i >> 2));

    /**
     * Беззнаковый сдвиг -
     */

    //0 трактуется как false 1 true
    int b1 = 0b00001001;
    int b2 = 0b00001010;

        System.out.println("Побитовое И (AND)");
    printBinary(b1);
    printBinary(b2);
    //& - один амперсанд
    printBinary(b1 & b2);

        System.out.println("Побитовое ИЛИ (OR)");
    printBinary(b1);
    printBinary(b2);
    printBinary(b1 | b2); // хотя бы у одного есть 1

        System.out.println("Исключающее ИЛИ (XOR)");
    printBinary(b1);
    printBinary(b2);
    printBinary(b1 ^ b2);

        System.out.println("Инверсия (NOT)");
    printBinary(b1);
    printBinary(~b1);
        System.out.println("*************************");

    /**
     * Можно применить где можно управлять значениями отдельных битов
     *
     */

    //установка бита на второе место
    i = 0b11010000;
    printBinary(i);
    printBinary(i | 0b00000010);

    //сброс бита в 0 в четвертом разряде
    printBinary(i);
    printBinary(i & 0b11110111);
        System.out.println("*************************");

    //проверка бита, какое значение у 4-го бита
        System.out.println((i & 0b00001000) > 0 ? "1" : "0");
        System.out.println("*************************");

    /**
     * Часто boolean хранят в целых числах 0 или 1
     */

    // упаковка 4 байт в int
    // Например хранение цвета + прозрачность
    int r = 64;
    int g = 128;
    int b = 32;
    int alpha = 255;
    int color = alpha << 24 | r << 16 | g << 8 | b;
        System.out.println(Integer.toBinaryString(color));

    // извлечение байтов из int
    b1 = color & 0xFF;//16 ричная
    b2 = (color & 0xFF << 8) >> 8;
    int b3 = (color & 0xFF << 16) >> 16;
    int b4 = (color & 0xFF << 24) >> 24;
    printBinary(b1);
    printBinary(b2);
    printBinary(b3);
    printBinary(b4);

    // Теперь проверим работоспособность
    BitArray bitArray = new BitArray(100);
        bitArray.set(0, 1);
        bitArray.set(9, 1);

    //теперь установим 5ый ьит в единичку а потом его же установим в 0
        bitArray.set(5, 1);
        bitArray.set(5, 0);

        System.out.println(bitArray.toString());
}

//класс реализующий битовый массив как BitSet
public static class BitArray {
    int size;
    byte[] bytes;

    //массив для работы с отдельными разрядами
    private byte[] masks = new byte[] {0b00000001, 0b00000010, 0b00000100, 0b00001000,
            0b00010000, 0b00100000, 0b01000000, (byte) 0b10000000};

    //Должен принимать размер далее изменение размера не предусмотрено
    public BitArray(int size) {
        this.size = size;
        //сколько байтов надо что бы поместить это все у себя
        int sizeInBytes = size / 8; //в одном байте 8 бит поэтому делим на 8
        //если у нас размер больше 8 то берем остаток что бы хвостик сохранить
        if (size % 8 > 0) {
            sizeInBytes = sizeInBytes + 1;
        }
        //создаем массив байт где мы будем хранить байты
        bytes = new byte[sizeInBytes];
    }

    //теперь нам нужны методы что бы
    // получить значения определенного бита по идексам
    //и что бы задать значения определенного бита по индексам

    //тут будем получать значение битового массива
    public int get(int index) {
        //нужно узнать в каком байте какой бит сообтветствует этому индекск
        int byteIndex = index / 8;//номер байта
        int bitIndex = index % 8;//вычисляем номер бита
        //вернем байт с соответствующим индексом
        //логическая поразрядная с маской в которой расположен бит индекс
        //если больше 0 то устанавливаем 1 - она означает что соответствующий
        // бит установлен в 1 иначе 0
        return (bytes[byteIndex] & masks[bitIndex]) > 0 ? 1 : 0;
    }

    //теперь сделаем функцию что бы задавать значение бита

    //передаем номер бита и значение которое надо установить
    public void set (int index, int value) {
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        //если значение больше 0 то надо установить бит 1
        if (value > 0) {
            //меняем значение соответствующего байта
            //и устанавливаем в этом байте соответствующий бит
            bytes[byteIndex] = (byte) (bytes[byteIndex] | masks[bitIndex]);
        } else {
            //если меньше 0 то надо сбросить
            //что бы сбросить то инвертируем маску
            //те делаем маску что бы все разряды были 1 кроме
            //того который надо сбросить
            bytes[byteIndex] = (byte) (bytes[byteIndex] & ~masks[bitIndex]);
        }
    }

    //что бы было видно что твориться переопределяем ту стринг
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //пройдемся по всем битам и допишем в стрингбилдер
        for (int i = 0; i < size; i++) {
            sb.append(get(i) > 0 ? '1' : '0');
        }
        return sb.toString();
    }
}

    // функции для вывода разрядов байта в форматированном виде
    public static void printBinary(int b) {
        System.out.println("0b" + Integer.toBinaryString(0b100000000 | (b & 0xff)).substring(1));
    }

    public static String binaryStr(int b) {
        return "0b" + Integer.toBinaryString(0b100000000 | (b & 0xff)).substring(1);
    }
}