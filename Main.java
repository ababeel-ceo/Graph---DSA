



/*
    List the problems

   1. Beadth First Search
   2. Depth First Search
   3. Detect cycle in an undirected graph (Bfs)
   4. Surrounding region (Dfs)
   5. Flood fill (Dfs - what we are using / Bfs)
   6. Detect Cycle in Directed graph
   7. Toppological Sort

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

        System.out.println("1. Bredth First Search Of This Adj List : " + adj1);
        System.out.println(bfs(visited, adj1)+"\n");

        boolean visited1[] = new boolean[v1];
        System.out.println("2. Depth First Search Of This Adj List : " + adj1);
        System.out.println(dfs(0, visited1, adj1, new ArrayList<>())+ "\n");



//        Example - 1
        int [][] adlForDetectCycle = new int[][]{{1}, {0, 2, 4}, {1, 3}, {2, 4}, {1, 3}};
        int vertices = 5;
        detectCycle(adlForDetectCycle, vertices);
//        Example - 2
        adlForDetectCycle = new int[][]{{}, {2}, {1, 3}, {2}};
        vertices = 4;
        detectCycle(adlForDetectCycle, vertices);

//       ------------------------
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

//        5. Flood fill algorithm using dfs
        floodFill();
//        6. Detect cycle in Directed graph
        detectCycleInDirectedGraph();
//        7. Topological Sort
        topoSort();


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