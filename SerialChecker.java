import java.util.*;

public class SerialChecker {

    static class Operation 
    {
        int time;
        int tx;
        String type;
        String item;

        Operation(int time, int tx, String type, String item) 
        {
            this.time = time;
            this.tx = tx;
            this.type = type;
            this.item = item;
        }
    }

    static boolean hasCycle(Map<Integer, Set<Integer>> graph) 
    {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> stack = new HashSet<>();

        for (int node : graph.keySet()) 
        {
            if (dfs(node, graph, visited, stack)) 
            {
                return true;
            }
        }
        return false;
    }

    static boolean dfs(int node, Map<Integer, Set<Integer>> graph, Set<Integer> visited, Set<Integer> stack) 
    {

        if (stack.contains(node)) return true;
        if (visited.contains(node)) return false;

        visited.add(node);
        stack.add(node);

        for (int next : graph.getOrDefault(node, new HashSet<>())) 
        {
            if (dfs(next, graph, visited, stack)) return true;
        }

        stack.remove(node);
        return false;
    }

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        List<List<Operation>> transactions = new ArrayList<>();
        List<Operation> current = null;
        int txId = -1;

        while (sc.hasNextLine()) 
        {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.equals("T")) 
            {
                current = new ArrayList<>();
                transactions.add(current);
                txId++;
            } else {
                String[] parts = line.split(",", 2);
                int time = Integer.parseInt(parts[0]);
                String op = parts[1];

                String type = op.contains("READ") ? "READ" : "WRITE";
                String item = op.substring(op.indexOf("(") + 1, op.indexOf(")"));

                current.add(new Operation(time, txId, type, item));
            }
        }

        List<Operation> ops = new ArrayList<>();
        for (List<Operation> t : transactions) ops.addAll(t);

        ops.sort(Comparator.comparingInt(o -> o.time));

        Map<Integer, Set<Integer>> graph = new HashMap<>();

        for (int i = 0; i < ops.size(); i++) {
            for (int j = i + 1; j < ops.size(); j++) 
            {
                Operation a = ops.get(i);
                Operation b = ops.get(j);

                if (a.tx != b.tx && a.item.equals(b.item)) {
                    if (a.type.equals("WRITE") || b.type.equals("WRITE")) 
                    {
                        graph.computeIfAbsent(a.tx, k -> new HashSet<>()).add(b.tx);
                    }
                }
            }
        }

        System.out.print(hasCycle(graph) ? 0 : 1);
    }
}
