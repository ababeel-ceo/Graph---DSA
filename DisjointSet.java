import java.util.ArrayList;
import java.util.List;

class DisjointSet{
    List<Integer> parent = new ArrayList<>();
    List<Integer> size = new ArrayList<>();
    List<Integer> rank = new ArrayList<>();

    DisjointSet(int n){
        for (int i=0; i<=n; i++){
            parent.add(i);
            size.add(0);
            rank.add(0);
        }
    }

    public int findUltimateParent(int node){
        if( node == parent.get(node)){
            return node;
        }
        int ulp  = findUltimateParent(parent.get(node));
        parent.set(node,ulp);
        return parent.get(node);
    }

    public void unionByRank(int u, int v){
        int ulp_u = findUltimateParent(u);
        int ulp_v = findUltimateParent(v);
        if(ulp_v == ulp_u){
            return;
        }

        if (rank.get(ulp_u) < rank.get(ulp_v)){
            parent.set(ulp_u, ulp_v);
        }else if (rank.get(ulp_v) < rank.get(ulp_u)){
            parent.set(ulp_v, ulp_u);
        }else {
            parent.set(ulp_v, ulp_u);
            int rankU = rank.get(ulp_u);
            rank.set(ulp_u, rankU + 1);
        }
    }
    public void unionBySize(int u, int v){
        int ulp_u = findUltimateParent(u);
        int ulp_v = findUltimateParent(v);
        if(ulp_v == ulp_u){
            return;
        }
        if (size.get(ulp_u) < size.get(ulp_v)){
            parent.set(ulp_u, ulp_v);
            size.set(ulp_u, size.get(ulp_v) + size.get(ulp_u));
        }else {
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }
    }
}
