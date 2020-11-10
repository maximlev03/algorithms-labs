package lab3;

import java.util.*;

public class Graph {
    //    private Map<Vertex, List<Vertex>[]> vertices;
//    private Map<Vertex, List<Vertex>[]> vertices2;
    private final Map<Vertex, List<Vertex>> verticesThatNeedThisToHaveAbilityToBeAdded;
    private final Map<Vertex, List<Vertex>> verticesNeededToAddThis;

    public Graph() {
        verticesThatNeedThisToHaveAbilityToBeAdded = new HashMap<>();
        verticesNeededToAddThis = new HashMap<>();
    }

    public void addVertex(String name) {
        verticesThatNeedThisToHaveAbilityToBeAdded.putIfAbsent(new Vertex(name), new ArrayList<>());
        verticesNeededToAddThis.putIfAbsent(new Vertex(name), new ArrayList<>());
    }

    public void removeVertex(String name) {
        Vertex v = new Vertex(name);
        verticesThatNeedThisToHaveAbilityToBeAdded.values().forEach(e -> e.remove(v));
        verticesThatNeedThisToHaveAbilityToBeAdded.remove(new Vertex(name));
    }

    public void removeEdge(String name1, String name2) {
        Vertex v1 = new Vertex(name1);
        Vertex v2 = new Vertex(name2);
        List<Vertex> eV1 = verticesThatNeedThisToHaveAbilityToBeAdded.get(v1);
        List<Vertex> eV2 = verticesThatNeedThisToHaveAbilityToBeAdded.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    public void addEdge(String name1, String name2) {
        Vertex v1 = new Vertex(name1);
        Vertex v2 = new Vertex(name2);
        verticesThatNeedThisToHaveAbilityToBeAdded.get(v1).add(v2);
        verticesThatNeedThisToHaveAbilityToBeAdded.get(v2).add(v1);
    }

    public List<Vertex> getChildVertices(String label) {
        return verticesThatNeedThisToHaveAbilityToBeAdded.get(new Vertex(label));
    }

    public List<Vertex> getParentVertices(String label) {
        return verticesNeededToAddThis.get(new Vertex(label));
    }

    public Set<String> breadthFirstTraversal(String root) {
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            for (Vertex v : getChildVertices(vertex)) {
                if (!visited.contains(v.getName()))
                    if (getParentVertices(vertex).stream().map(vertex1 -> {
                        if (visited.contains(vertex1.getName())) {
                            return true;
                        } else {
                            // try to add parent vertex to queue
                            return false;
                        }
                    }).reduce((aBoolean, aBoolean2) -> aBoolean && aBoolean2).orElse(false)) {

                        visited.add(v.getName());
                        queue.add(v.getName());
                    }
            }
        }
        return visited;
    }

}

