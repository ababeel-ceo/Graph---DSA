



/*
    List the problems

   1. Beadth First Search
   2. Depth First Search
   3. Detect cycle in an undirected graph (Bfs)
   4. Surrounding region (Dfs)
   5. Flood fill (Dfs - This is what we are using / Bfs)
   6. Detect Cycle in Directed graph
   7. Toppological Sort (DFS)
   8. Kahn's algorithm (BFS)
   9. Shortest path finding in Undirected Graph (Using BFS)
   10. Dijkstra Algorith  (SP - Weighted Undirected graph) Memiozation
   11. Bellford Ford Single Source Shortest Path Algorithm (DG & UG)
   12. Floyd Warshall Multisource Shortest Path Algorithm

 */



import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        ArrayList<ArrayList<Integer>> adj1 = new ArrayList<>();
        int v1 = 5;
        adj1.add(new ArrayList<>());
        adj1.add(new ArrayList<>());
        adj1.add(new ArrayList<>());
        adj1.add(new ArrayList<>());
        adj1.add(new ArrayList<>());

        adj1.get(0).add(1);
        adj1.get(0).add(2);
        adj1.get(0).add(3);

        adj1.get(2).add(4);

        boolean visited[] = new boolean[v1];

//----------------------------------------------------------------------------
//        1. Beadth First Search

        System.out.println("1. Bredth First Search Of This Adj List : " + adj1);
        System.out.println(bfs(visited, adj1)+"\n");

//----------------------------------------------------------------------------
//        2. Depth First Search
        boolean visited1[] = new boolean[v1];
        System.out.println("2. Depth First Search Of This Adj List : " + adj1);
        System.out.println(dfs(0, visited1, adj1, new ArrayList<>())+ "\n");


//----------------------------------------------------------------------------
//----------------3. Detect cycle in an undirected graph (Bfs)

//        Example - 1
        int [][] adlForDetectCycle = new int[][]{{1}, {0, 2, 4}, {1, 3}, {2, 4}, {1, 3}};
        int vertices = 5;
        detectCycle(adlForDetectCycle, vertices);
//        Example - 2
        adlForDetectCycle = new int[][]{{}, {2}, {1, 3}, {2}};
        vertices = 4;
        detectCycle(adlForDetectCycle, vertices);


//----------------------------------------------------------------------------
//        4. Surrounding region (Dfs)

        System.out.println("\n\n4. Surrounded Region ");
//        Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
//        Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
//        Explanation: Notice that an 'O' should not be flipped if:
//        - It is on the border, or
//        - It is adjacent to an 'O' that should not be flipped.
//                The bottom 'O' is on the border, so it is not flipped.
//        The other three 'O' form a surrounded region, so they are flipped.

        char[][] board = new char[][]{{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        surroundedRegion(board);


//----------------------------------------------------------------------------
//        5. Flood fill algorithm using dfs
        floodFill();


//----------------------------------------------------------------------------
//        6. Detect Cycle in Directed graph
        detectCycleInDirectedGraph();


//----------------------------------------------------------------------------
//        7. Toppological Sort (DFS)
        topoSort();


//----------------------------------------------------------------------------
//        8. Kahn's algorithm (BFS)
        kahnsAlgorith();

//----------------------------------------------------------------------------
//        9. Shortest path finding in Undirected Graph (Using BFS - > Dijkstra Algarithm ) unit weight (always weight - 1)

        shortestPathInUndirectedGraph();
        
//        10 Dijkstra Algorith  (SP - Weighted Undirected graph) Memiozation
        dijkstraAlgorithm();
        
//        11 Bellman Ford  (SP) (directed and undirected graph) 
        bellmanFordAlgarithm();

//        12 Floyd Warshall   (MSP) (directed and undirected graph)
        floydWarshallMultiSourceSP();

    }

    private static void floydWarshallMultiSourceSP() {

        /*
            Input Matrix (graph):
             { {0, 2, -1, -1},
             {1, 0, 3, -1},{-1, -1, 0, -1},{3, 5, 4, 0} }

        **/

        System.out.println("\n\nInput Matrix (Graph) :   { {0, 2, -1, -1},\n" +
                "        {1, 0, 3, -1},{-1, -1, 0, -1},{3, 5, 4, 0} }");
        int matrix[][] = new int[][]  { {0, 2, -1, -1},
                {1, 0, 3, -1},{-1, -1, 0, -1},{3, 5, 4, 0} };
        int n = matrix.length;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j] == -1){
                    matrix[i][j] = (int) 1e9;
                }
                if(i == j){
                    matrix[i][j] =0;
                }
            }
        }
        for(int k =0; k<matrix.length; k++){
            for(int i=0; i<matrix.length; i++){
                for (int j=0; j<matrix.length; j++){
                    matrix[i][j] = Math.min(matrix[i][j] , matrix[i][k] + matrix[k][j]);
                }
            }

        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j] == (int) 1e9){
                    matrix[i][j] = -1;
                }
            }
        }
        System.out.println("Shortest path of the Graph is : ");
        for (int i=0; i<matrix.length; i++){
            for (int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j]+" ");
            }System.out.println();
        }
    }

    private static void bellmanFordAlgarithm() {
        System.out.println("\n\n11. Bellman Ford Shortuest path algorithm");
        /*
        1.Distance array
        I/p :  [[0, 1, -1], [1, 0, 3], [1, 2, 4], [2, 0, 3]]
        n  =3
        src =0
        */
        int n = 3;
        int src = 0;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        adj.add(new ArrayList<>(List.of(0, 1, -1)));
        adj.add(new ArrayList<>(List.of(1, 0, 3)));
        adj.add(new ArrayList<>(List.of(1, 2, 4)));
        adj.add(new ArrayList<>(List.of(2, 0, 3)));
        int dis[] = new int[n];
        for (int i=0; i<n; i++) dis[i] = (int) 1e9;
        dis[src] = 0;
        for(int i=0; i<n; i++){
            for(ArrayList<Integer> k : adj){
                int u = k.get(0);
                int v = k.get(1);
                int wt = k.get(2);
//                Do Relaxation
                if(dis[u] != (int)1e9 && dis[u] + wt < dis[v]) {
                    dis[v] = dis[u] + wt;
                }
            }
        }
//        Find the negative cycle
        for(ArrayList<Integer> k : adj){
            int u = k.get(0);
            int v = k.get(1);
            int wt = k.get(2);
//                Do Relaxation
            if(dis[u] != (int)1e9 && dis[u] + wt < dis[v]) {
               System.out.println("This Graph has Negative Cycle ");
               break;
            }
        }
        System.out.println("It shortest path is : ");
        for (int i=0; i<n; i++){
            System.out.print(dis[i] +" ");
        }
    }

    private static void dijkstraAlgorithm() {

        System.out.println("\n10. Dijkstra Algorithm");

        /*
            PRE-REQUISITE
            -------------
            1. Priority Queue
            2. Distance [] array
            3. Memoization array [] (named as Parent)
            4. adj with weighted
            input : [[[1, 9]], [[0, 9]]] v=2, src=0
            o/p : [0,9]
        */

        int v = 2;
        int src =0;
        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();

        ArrayList<ArrayList<Integer>> innerList1 = new ArrayList<>();
        innerList1.add(new ArrayList<>(Arrays.asList(1, 9)));
        adj.add(innerList1);

        ArrayList<ArrayList<Integer>> innerList2 = new ArrayList<>();
        innerList2.add(new ArrayList<>(Arrays.asList(0, 9)));
        adj.add(innerList2);

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((x,y)->x[0] - y[0]);
        int dis[] = new int[v];


        for (int i=0; i<v; i++){
            dis[i] = (int)1e9;
        }
        dis[src] = 0;
        pq.offer(new int[]{0, src});
        for(int i=0; i<adj.size(); i++){
            for(int j=0; j<adj.get(i).size(); j++){
                System.out.println(adj.get(i).get(j));
            }
        }
        while (!pq.isEmpty()){
            int elem[] = pq.poll();
            int node = elem[1];
            int distance = elem[0];

            for (ArrayList<Integer> k: adj.get(node)){
                int adjNode  = k.get(0);
                int weight = k.get(1);
                if(dis[adjNode] > weight+distance){
                    dis[adjNode] = weight + distance;
                    pq.offer(new int[]{weight+distance,adjNode});
                }
            }
        }

        System.out.println("Distance Array is : ");
        for (int i=0; i<dis.length; i++){
            System.out.print(dis[i] +" , ");
        }
        System.out.println("---------------------");




    }

    private static void shortestPathInUndirectedGraph() {
 /*       Pre-requisite
                --------------
        1. Queue DS
        2. Dis array[] of size n
        3. Adj List
        [0,1] [0,3] [3,4] [4,5] [5,6] [1,2] [2,6] [6,7] [7,8] [6,8]
        n = 9, m=10 start = 0
  */
        int n = 9;
        int start = 0;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for(int i=0; i<n; i++){
            adj.add(new ArrayList<>());
        }
        adj.get(0).add(1);adj.get(0).add(3);adj.get(3).add(4);adj.get(4).add(5);adj.get(5).add(6);
        adj.get(1).add(2);adj.get(2).add(6);adj.get(6).add(7);adj.get(7).add(8);adj.get(6).add(8);
        adj.get(1).add(0);adj.get(3).add(0);adj.get(4).add(3);adj.get(5).add(4);adj.get(6).add(5);
        adj.get(2).add(1);adj.get(6).add(2);adj.get(7).add(6);adj.get(8).add(7);adj.get(8).add(6);

        int dis[] = new int[n];
        for (int i=0; i<n; i++) dis[i] = (int)1e9;
        dis[start] = 0;

        Queue<Integer> q =new LinkedList<>();
        q.offer(start);
        while (!q.isEmpty()){
            int node = q.poll();
            for (int k : adj.get(node)){
                if (dis[node]+1  < dis[k] ){
                    dis[k] = 1+dis[node];
                    q.offer(k);
                }
            }
        }

        System.out.println("Find Shortest Path in Undirected Graph \nAdj List : "+ adj);
        System.out.println("The Edges :  [0,1] [0,3] [3,4] [4,5] [5,6] [1,2] [2,6] [6,7] [7,8] [6,8]");
        System.out.print("The shoertest paths : ");
        for (int i=0; i<n; i++){
            if (dis[i] == 1e9){
                System.out.print(-1 +" ");
            }else {
                System.out.print(dis[i]+" ");
            }
        }
    }

    private static void kahnsAlgorith() {
/*       Pre-requisite
         --------------
        1. Queue DS
        2. Indegree array[] of size n
        3. Adj List
            [2, 3]
            [3, 1]
            [4, 0]
            [4, 1]
            [5, 0]
            [5, 2]
            Vertices = 6

         Logic
         -----
         1. Find indegree of each node in adj list
         2. Traverse on indegree[] if it's 0 the add that **index to Q
         3. Start - BFS
         4. remove from q and add to ansList and check that node in adj List
                reduce indegree of the node index from adj List
                and check if it is 0 then add to Q
            End BFs
         5. Print ans list
        */
        int v = 6;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i=0; i<v; i++){
            adj.add(new ArrayList<>());
        }
        adj.get(2).add(3);adj.get(3).add(1);adj.get(4).add(0);
        adj.get(4).add(1);adj.get(5).add(0);adj.get(5).add(2);

        Queue<Integer> q =new LinkedList<>();
        int[] indegree = new int[v];

//        Find how many indegree are in the adj List
        for(int i=0; i<v; i++){
            for (int k : adj.get(i)){
                indegree[k]++;
            }
        }
//        If indegree is  0 the add to Q
        for (int i = 0; i < v; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()){
            int node = q.poll();
            ans.add(node);
            for (int k: adj.get(node)){
                indegree[k]--;
                if (indegree[k] == 0){
                    q.offer(k);
                    }
            }
        }
        System.out.println("\n\nKahn's Alogorithm (DAG) : ");
        System.out.println("QNS : "+ adj);
        System.out.println("ANS : "+ans +"\n");

    }


    //  1. Breadth First Search
    public static List<Integer> bfs (boolean visited[], ArrayList<ArrayList<Integer>> adj){

        Queue<Integer>  q = new LinkedList<>();
        List<Integer> bfsRes = new ArrayList<>();
        q.offer(0);
        visited[0] = true;
        while (! q.isEmpty())
        {
            int qElem = q.poll();
            bfsRes.add(qElem);
            visited[qElem] = true;
            for (int adjElem : adj.get(qElem)){
                    if(!visited[adjElem]){
                        q.offer(adjElem);
                    }
            }
        }
        return bfsRes;
    }

//  2.  Depth First Search
    public static List<Integer> dfs (int start, boolean visited[], ArrayList<ArrayList<Integer>> adj, List<Integer> res){
        visited[start] = true;
        res.add(start);
        for(int end : adj.get(start)){
            if(!visited[end]){
                dfs(end, visited, adj, res);
            }
        }
        return res;
    }


//  3. Detect cycle in an undirected graph

    public static void detectCycle(int [][] adlForDetectCycle, int vertices){
        System.out.println("3. Detect Cycle in an Undirected graph");
        System.out.println( "Input Adjacency list : ");
        for(int[] i : adlForDetectCycle){
            for (int j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }
        System.out.println("Vertices : "+vertices);

        boolean[] vis = new boolean[vertices];
        boolean flag = false;
        for (int i=0; i<vertices; i++){
            if(!vis[i]){
                if( findCycleinUndirectedGraph(i, adlForDetectCycle, vis)){
                   flag = true;
                }
            }
        }
        if (flag){
            System.out.println("Yes, it has cycle");
        }else {
            System.out.println("No, it doesn't has cycle");
        }
    }

    private static boolean findCycleinUndirectedGraph(int start, int [][] adj, boolean[] vis){

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{start, -1});
        vis[start] = true;
        while (!q.isEmpty()){
            int ele[] = q.poll();
            int node = ele[0];
            int parent = ele[1];

            for (int k : adj[node]){
                if (!vis[k]){
                    vis[k] = true;
                    q.offer(new int[]{k, node});
                } else if ( k != parent) {
                        return true;
                }
            }

        }
        return false;
    }


//    4. Surrounded region

    public static void surroundedRegion(char[][] board){
        System.out.println("Given input is : ");
        for(char[] i : board){
            for (char j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }

        int n = board.length;
        int m = board[0].length;
//        1. we neen visited array of m * n size
        boolean vis[][] = new boolean[n][m];

//        Traverse the all boundaries
        int r = 0;
        for(int c = 0; c < m; c++){
            if( board[r][c] == 'O' && !vis[r][c] ){
                dfsForSurrounding(r, c, vis, board);
            }
        }
        r = n-1;
        for(int c = 0; c < m; c++){
            if( board[r][c] == 'O' && !vis[r][c] ){
                dfsForSurrounding(r, c, vis, board);
            }
        }

        int col = 0;
        for (int row = 0; row < n; row++){
            if( board[row][col] == 'O' && !vis[row][col] ){
                dfsForSurrounding(row, col, vis, board);
            }
        }
        col = m-1;
        for (int row = 0; row < n; row++){
            if( board[row][col] == 'O' && !vis[row][col] ){
                dfsForSurrounding(row, col, vis, board);
            }
        }

        for (int i=0; i<n; i++){
            for (int j=0; j<m; j++){
                if ( !vis[i][j] && board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
        System.out.println("Output is : ");
        for(char[] i : board){
            for (char j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }

    }

    static void dfsForSurrounding(int row, int col, boolean[][] vis, char[][] board){
        vis[row][col] = true;
        int n = board.length;
        int m = board[0].length;
        int[] delr = {0 ,1, 0, -1};
        int[] delC = {1, 0, -1, 0};
        for (int i= 0; i<4; i++){
            int newRow = row + delr[i];
            int newCol = col + delC[i];
            if( newRow < n && newRow >= 0 && newCol < m && newCol >= 0 && !vis[newRow][newCol] && board[newRow][newCol] == 'O' ){
                dfsForSurrounding(newRow, newCol, vis, board);
            }
        }
    }


//    5. Flood Fill
    static void floodFill(){

        int delRow[] = {-1, 0, 1, 0};
        int delCol[] = {0, +1, 0, -1};
        int[][] image = new int[][]{{1,1,1},{1,1,0},{1,0,1}};
//        int[][] image = new int[][]{{0,0,0},{0,0,0}};
        int ans[][] = image;
        int startRow = 1;
        int startCol = 1;
        int initialColor = image[startRow][startCol];
        int newColor = 2;

        System.out.println("\n\n 4. Flood fill Algorithm");
        System.out.println("Given Input is : ");
        for(int[] i : image){
            for (int j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }

        dfsForFloodFill(image, ans, startRow, startCol, initialColor, newColor, delRow, delCol);
        System.out.println("Output is : ");
        for(int[] i : ans){
            for (int j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }


    }
    static void dfsForFloodFill(int[][] image, int[][] ans, int r, int c, int initialColor, int newColor, int[] delRow, int[] delCol){
        ans[r][c] = newColor;
        int n = image.length;
        int m = image[0].length;
        for (int i = 0; i < 4; i++){
            int newRow = r + delRow[i];
            int newCol = c + delCol[i];
            if( newRow < n && newRow >=0 && newCol < m && newCol >= 0 && image[newRow][newCol] == initialColor && ans[newRow][newCol] != newColor){
                dfsForFloodFill(image, ans, newRow, newCol, initialColor, newColor, delRow, delCol);
            }
        }
    }

//    6. Detect Cycle in Directed Graph

    static void detectCycleInDirectedGraph(){

        System.out.println("\n\n5. Detect Cycle in Directed Graph");
//        This Cycle example
        int[][] edges = {{4, 4},
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 3}};

        List<List<Integer>> adj = new ArrayList<>();
//        Example for not a cycle
//        int[][] edges = {{6, 5}, {5, 3}, {3, 1}, {1, 2}, {2, 4}, {4, 0}};
        int v = edges.length + 1;
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
        }

        for (List<Integer> list : adj) {
            for (int j : list) {
                System.out.print(j + "  ");
            }
            System.out.println();
        }
        boolean[] vis = new boolean[v];
        boolean[] pathVis = new boolean[v];

        boolean flag = false;
        for(int i=0; i<v; i++){
            if(!vis[i]){
                if(cycleDetection(i, adj, vis, pathVis)){
                    flag =  true;
                }
            }
        }
        if (flag){
            System.out.println("Directed graph has cycle");
        }else {
            System.out.println("Directed doesn't have cycle");
        }
        
    }

    static boolean cycleDetection(int start, List<List<Integer>> adj, boolean vis[], boolean pathVis[]){

        vis[start] = true;
        pathVis[start] = true;

        for (int k : adj.get(start)){
            if(!vis[k]){
                if(cycleDetection(k, adj, vis, pathVis)){
                    return true;
                }
            }else if (pathVis[k]){
                return true;
            }
        }
        pathVis[start] = false;
        return false;
    }


    static void addEdge(int v, int w, List<List<Integer>> adj ) { adj.get(v).add(w); }
    static void topoSort(){

        System.out.println("\n\n Topological Sorting ");
        System.out.println("Adj List");
        int[][] adj1 = new int[][]{
                {3, 4},
                {3, 0},
                {1, 0},
                {2, 0}
        };
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0; i<6; i++){
            adj.add(new ArrayList<>());
        }
        addEdge(5, 2, adj);
        addEdge(5, 0, adj);
        addEdge(4, 0, adj);
        addEdge(4, 1, adj);
        addEdge(2, 3, adj);
        addEdge(3, 1, adj);


        int v = 6   ;
        for(List<Integer> i : adj){
            for (int j : i){
                System.out.print(j + "  ");
            }
            System.out.println();
        }
        boolean[] vis = new boolean[v];
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[v];
        for (int i=0; i< v; i++){
            if(!vis[i]){
                dfsForTopo(i, adj, vis, st);
            }
        }

        System.out.println("\n\nAfter Topo sort : ");
        while (!st.isEmpty()){
            System.out.print(st.pop() +"  ");
        }
    }

    static void dfsForTopo(int start, List<List<Integer>> adj, boolean[] vis, Stack<Integer> st){
        vis[start] = true;
        for(int k: adj.get(start)){
            if(!vis[k]){
                dfsForTopo(k, adj, vis, st);
            }
        }
        st.push(start);
    }
}