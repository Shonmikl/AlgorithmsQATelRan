package _27102022.stackAndQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueue {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            String[] words = new String[] {"Alexey", "Mikhail", "Alexander", "Irina", "Yuri", "Alexey Kh", "Alexey Ba"};

            for (int i = 0; i < words.length && !Thread.interrupted();) {
                try {
                    Thread.sleep(3500);
                    queue.put(words[i]);
                    System.out.println("Producer wrote to queue "  + words[i] + ": elements number " + queue.size());
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(()-> {
            StringBuilder sb = new StringBuilder();
            while (!Thread.interrupted()) {

                try {
                    sb.setLength(0);
                    Thread.sleep(7000);
                    sb.append(queue.take());
                    System.out.println("Consumer read: " + sb.reverse() + ": element number " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        producer.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.start();
    }
}
