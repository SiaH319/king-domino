package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.model.Property;
public class DisjointSet {
    private int[] par;
    private int[] rank;
    public DisjointSet (int n){
        if (n > 0) {
            par = new int[n];
            rank = new int[n];
            for (int i = 0; i < this.par.length; i++) {
                par[i] = i;
            }
        }
    }

    /**
     * Find resentative of element i
     * */
    public int find(int i) {
        if (par[i] == i) {
            return i;
        }else {
            par[i] = find(par[i]);
            return par[i];
        }

    }

    /**
     * Merge sets containing elements i and j
     *  @return resentative of merged set
     * */
    public int union(int i, int j) {
        int mergedRep = -1;
        int root_i = find(i);
        int root_j = find(j);

        //If elements i and j are in the same tree
        if (root_i == root_j) {
            return root_i;
        }

        //If elements i and j are not in the same tree
        if (rank[root_i] < rank[root_j]) {
            mergedRep = root_j;
            par[root_i] = mergedRep;	//Update smaller rank tree's representative to larger rank tree's root
        }else if(rank[root_i] > rank[root_j]) {
            mergedRep = root_i;
            par[root_j] = mergedRep;	//Update smaller rank tree's representative to larger rank tree's root
        }else {
            mergedRep = root_j;
            rank[root_j] += 1;			//Increase the rank of new root by 1
            par[root_i] = mergedRep;
        }
        return mergedRep;
    }

    public String toString(Square[] grid){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if(grid[i].getTerrain()!=null){
                if (setstrings[pari]==null) {
                    setstrings[pari] = String.valueOf(i);
                    countsets+=1;
                } else {
                    setstrings[pari] += "," + i;

                }
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }

    /**
     * Getter for par array
     * @return
     */
    public int[] getParentArray () {
        return par;
    }

}
