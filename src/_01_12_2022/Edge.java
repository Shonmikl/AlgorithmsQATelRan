package _01_12_2022;

import java.util.*;

//Класс для вершин
public class Edge {
    int source;
    int dest;
    int weight;

    public Edge(int source, int dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

//Класс для хранения узлов
class Node {
    int vertex;
    int weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}

//Класс который все объединяет в граф
class Graph {

    //List для предоставления списков смежности
    List<List<Edge>> adjList;

    Graph(List<Edge> edges, int n) {
        //тут будут храниться наши узлы/вершины
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        //добавить ребра в наш граф
        for (Edge edge : edges) {
            adjList.get(edge.source).add(edge);
        }
    }
}

class Main {
    private static void getRoute(int[] prev, int i, List<Integer> route) {
        if(i >= 0) {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    public static void findShortestPath(Graph graph, int source, int nodesNumbers) {
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        minHeap.add(new Node(source, 0));

        //устанавливаем для нашей матрицы - бесконечности
        List<Integer> dist;
        dist = new ArrayList<>(Collections.nCopies(nodesNumbers, Integer.MAX_VALUE));

        //расстояние от источника до самого себя == 0
        dist.set(source, 0);

        //Нужно помечать вершины которые мы уже прошли
        boolean[] done = new boolean[nodesNumbers];
        done[source] = true;

        //Массив для сохранения предыдущих вершин
        int[] prev = new int[nodesNumbers];
        prev[source] = -1;

        //Нам надо обойти все узлы пока minHeap не станет пустым
        while (!minHeap.isEmpty()) {
            //удалить и вернуть лучшую вершину
            Node node = minHeap.poll();

            //далее получаем номер вершины
            int vertex = node.vertex;

            //Для каждого соседа
            for (Edge edge : graph.adjList.get(vertex)) {
                int v = edge.dest;
                int weight = edge.weight;

                //Релаксация(замена на итоговое лучшее расстояние)
                if(!done[v] && (dist.get(vertex) + weight) < dist.get(v)) {
                    dist.set(v, dist.get(vertex) + weight);
                    prev[v] = vertex;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            //если мы нашли меньшее расстояние, то помечаем вершину как пройденную
            done[vertex] = true;
        }

        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < nodesNumbers; i++) {
            if(i != source && dist.get(i) != Integer.MAX_VALUE) {
                getRoute(prev, i, route);
                System.out.printf("Path (%d -> %d): Minimum cost = %d, Route = %s\n",
                        source, i, dist.get(i), route);
                route.clear();
            }
        }
    }

    public static void main(String[] args) {
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 10),
                new Edge(0, 4, 3),
                new Edge(1, 2, 2),
                new Edge(1, 4, 4),
                new Edge(2,3,9),
                new Edge(3,2, 7),
                new Edge(4,1, 1),
                new Edge(4,2,8),
                new Edge(4,3,2)
        );

        int nodesNumber = 5;
        Graph graph = new Graph(edges, nodesNumber);

        for (int source = 0; source < nodesNumber; source++) {
            findShortestPath(graph, source, nodesNumber);
        }
    }
}
