package _03112022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Haffman {
    /**
     * 1. Посчитать сколько каждый символ встречается в строке
     * 2. Класс для предоставления кодового дерева
     * 3. Написать алгоритм кодирования
     * 4. Написать алгоритм декодирования
     * 5. Посмотреть как все это работает
     */

    //1. Посчитать сколько каждый символ встречается в строке
    private static TreeMap<Character, Integer> countFrequency(String text) {
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = freqMap.get(c);
            freqMap.put(c, count != null ? count + 1 : 1);
        }
        return freqMap;
    }

    //2. Класс для предоставления кодового дерева
    private static class CodeTreeNode implements Comparable<CodeTreeNode> {
        //Хранит символ
        Character content;

        //Частота(вес)
        int weight;

        //Левый потомок
        CodeTreeNode left;

        //Правый потомок
        CodeTreeNode right;

        public CodeTreeNode(Character content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public CodeTreeNode(Character content, int weight, CodeTreeNode left, CodeTreeNode right) {
            this.content = content;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(CodeTreeNode o) {
            return o.weight - weight;
        }

        /**
         * Функция которая делает проход по нашему кодовому дереву
         * от корня до конкретного символа и при повороте направо или налево
         * вычисляет последовательность 0 и 1.
         * В качестве параметра будет принимать символ который мы ищем и путь в виде 0 и 1
         * к которым мы будем дописывать 0 при сворачивании налево и 1 направо.
         * Если кратко то это просто обход дерева в глубину
         */

        public String getCodeForCharacter(Character ch, String parentPath) {
            //если наш символ который мы ищем, то мы его нашли
            if (content == ch) {
                //возвращаем путь
                return parentPath;
            } else {
                //если есть левый потомок
                if (left != null) {
                    //вызываем рекурсивно ту же функцию
                    //передаем символ и дописываем к пути 0 или 1
                    String path = left.getCodeForCharacter(ch, parentPath + 0);
                    if (path != null) {
                        //это значит что в ветке нашелся нужный символ
                        //а это в свою очередь значит что необходимо вернуть путь
                        //который представляет из себя код
                        return path;
                    }
                }
                if (right != null) {
                    return right.getCodeForCharacter(ch, parentPath + 1);
                }
            }
            return null;
        }
    }

    //3. Написать алгоритм кодирования
    private static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes) {
        //пока список узлов больше чем 1
        while (codeTreeNodes.size() > 1) {

            //упорядочиваем узлы по весам
            Collections.sort(codeTreeNodes);

            //теперь необходимо взять два узла с самыми маленькими весами
            //получить из списка узел и сразу их из arrayList удалить
            //в результате у нас получиться два узла для дерева
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size() - 1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size() - 1);

            //теперь необходимо создать промежуточный узел
            //вес которого равен сумме "маленьких узлов"
            CodeTreeNode parent = new CodeTreeNode
                    (null, right.weight + left.weight, left, right);

            //теперь кладем его обратно в массив
            //прокручиваем ту же операцию снова

            codeTreeNodes.add(parent);
        }
        return codeTreeNodes.get(0);
    }

    // 4. Написать алгоритм декодирования
    private static String huffmanDecode(String encoded, CodeTreeNode tree) {
        //тут будем хранить расшифрованный текст
        StringBuilder decoded = new StringBuilder();

        //тут будет переменная которая будет хранить узел когда мы будем спускаться по дереву
        //изначально этот узел - это корень
        CodeTreeNode node = tree;

        //теперь идем по битам нашей строки ( по 0 и 1 )
        for (int i = 0; i < encoded.length(); i++) {
            //если текущий бит 0 тогда идем налево, а если 1 направо
            node = encoded.charAt(i) == '0' ? node.left : node.right;

            //если мы дошли до какого-то листа и у него есть символ
            if (node.content != null) {
                //добавляем его в нашу последовательность
                decoded.append(node.content);
                // и далее возвращаем узел на корень дерева
                node = tree;
            }
        }
        return decoded.toString();
    }

    public static void main(String[] args) {
        String text = "Kenya has won six of the past nine Olympic gold medals " +
                "on offer in the men's 800m, starting when Paul Ereng triumphed at the 1988 Games in Seoul.";

        //Вычисляем частоту символов в тексте
        TreeMap<Character, Integer> frequencies = countFrequency(text);

        //Генерируем список листов деревьев
        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();
        for (Character c : frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }

        //Строим кодовое дерево с помощью господина Хаффмана
        CodeTreeNode tree = huffman(codeTreeNodes);

        //Генерируем таблицу префиксных кодов для кодируемых символов с помощью кодового дерева
        //клич - символ, значение - строка содержащая  0 и 1 которые являются кодом для этого символа
        TreeMap<Character, String> codes = new TreeMap<>();
        for (Character  c : frequencies.keySet()) {
            //Добавляем код для нашего символа из нашего дерева, в качестве начального пути.
            //С самого начала в качестве начального пути передаем пустую строку.
            //В нашу пустую строку будем передавать 0 и 1
            codes.put(c, tree.getCodeForCharacter(c, ""));
        }

        System.out.println("Таблица префиксных кодов: " + codes);

        //Теперь кодируем текст, заменяя символы соответствующим кодом
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            //Просто идем по строке и для каждого символа записываем код
            //этого символа, который мы сгенерировали при помощи нашего кодового дерева
            encoded.append(codes.get(text.charAt(i)));
        }

        System.out.println("Размер исходной строки: " + text.getBytes().length * 8 + " бит");
        System.out.println("Размер сжатой строки: " + encoded.length() + " бит");
        System.out.println("Биты сжатой строки: " + encoded);

        //Теперь раскодируем обратно
        String decoded = huffmanDecode(encoded.toString(), tree);

        System.out.println("Раскодированный вариант: " + decoded);
    }
}