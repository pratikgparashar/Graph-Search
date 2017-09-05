/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;
import java.io.*;
import java.util.*;
/**
 *
 * @author prati
 */
class NurseryGrid{
    int lizard_count;
    int[][] grid_map;
    boolean solved;
    NurseryGrid(int lz_count,int n, int [][] map){
        lizard_count = 0;
        grid_map = map;
        solved =  false;
;    }
}

public class Homework {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    static int total_lizard_count = 5; 
    static int dimension = 3;
    static double czz = 0;
    static int[] tree_count;
    static int [] col_lizard_count;
    static HashMap<Integer,ArrayList<Integer>> tree_map =new HashMap<Integer,ArrayList<Integer>>();
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        System.out.println("hello");
//        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br =  new BufferedReader(new FileReader("./input.txt"));
        String algorithm = br.readLine();
        dimension = Integer.parseInt(br.readLine());
        total_lizard_count = Integer.parseInt(br.readLine());
        int [][] grid_map = new int[dimension][dimension];
        tree_count =  new int[dimension];
        col_lizard_count =  new int[dimension];
        Arrays.fill(col_lizard_count,0);
        Arrays.fill(tree_count,0);
        ArrayList<Integer> temp =  new ArrayList<Integer>();
        for (int i = 0; i < dimension; i++) {
            String line = br.readLine();
            String[] row = line.split("");
            int[] num_row = new int[dimension];
            int type = 0;
            for(int k = 0; k< dimension; k++){
                type = Integer.parseInt(row[k]);
                num_row[k] = type;
                if (type == 2){
                    tree_count[k] += 1;
                    temp = tree_map.get(k);
                    if(temp != null){
                        temp.add(i);
                        tree_map.put(k,temp);
                    }
                    else{
                        ArrayList<Integer> new_list = (new ArrayList<Integer>());
                        new_list.add(i);
                        tree_map.put(k,new_list);
                    }
                }
                
            }
            grid_map[i] = num_row;
        }
//        System.out.println("hello2");
        for(int count : tree_count){
            System.out.print(count + " ");
        }
        System.out.println("");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i + " : ");
            if(tree_map.containsKey(i)) tree_map.get(i).forEach(System.out::print);
            System.out.println();
        }
        NurseryGrid grid =  new NurseryGrid(0,dimension, grid_map);
        grid = solveGrid(grid,0,0);
        printSolution(grid);
        System.out.println("Validity : " + validSolution(grid, total_lizard_count, dimension));
    }
    
    static NurseryGrid solveGrid(NurseryGrid grid,int position_i , int position_j){
        int i, j;
        boolean can_placed;
        int[]  next_cord;
//        System.out.println("Call : " + ++czz);
        if (grid.lizard_count == total_lizard_count){
            grid.solved =  true;
            return grid;
        }
        i = position_i;
        j = position_j;
        while ( i < dimension && j< dimension) {
//            System.out.println("I =" + i + " J = " + j);
//            System.out.println(grid.lizard_count);
            can_placed = canLizardBePlaced(grid,i,j);
//            System.out.println(can_placed);
            
//            if(can_placed) System.out.println("I =" + i + " J = " + j);
            if (can_placed){
                grid.grid_map[i][j] = 1;
                grid.lizard_count += 1;
                col_lizard_count[j] += 1;
                next_cord = nextPosition(i,j);
                if(!shouldMoveAhead(i,j,grid.lizard_count)){
                    grid.grid_map[i][j] = 0;
                    grid.lizard_count -= 1;
                    col_lizard_count[j] -= 1;
//                    System.out.println("Nikal diya BC I =" + i + " J = " + j);
                    i = next_cord[0];
                    j = next_cord[1];
                    break;
                }
//                printTree(grid);
                NurseryGrid new_g = solveGrid(grid,next_cord[0],next_cord[1]);
                if(new_g.lizard_count == total_lizard_count){
                    return new_g;
                }
                else{
                    grid.grid_map[i][j] = 0;
                    grid.lizard_count -= 1;
                    col_lizard_count[j] -= 1;
//                    System.out.println("Nikal diya BC I =" + i + " J = " + j);
                }
            }
            else{
                next_cord = nextPosition(i,j);
//                solveGrid(grid,next_cord[0],next_cord[1]);
            }
            i = next_cord[0];
            j = next_cord[1];
        }
        return grid;
    }
    
    static boolean canLizardBePlaced(NurseryGrid grid, int current_i, int current_j){
        int i, j;
//        System.out.println("Trying to Place I =" + current_i + " J = " + current_j);
        if(grid.grid_map[current_i][current_j] == 2) return false;
        /*left side */
        for (i = current_j; i >= 0; i--)
            if(grid.grid_map[current_i][i] == 2)
                break;
            else if (grid.grid_map[current_i][i] == 1)
                return false;
 
        /*upper diagonal left */
        for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
            if (grid.grid_map[i][j] == 2)
                break;
            else if (grid.grid_map[i][j] == 1)
                return false;
 
        /* lower diagonal left*/
        for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--)
            if (grid.grid_map[i][j] == 2)
                break;
            else if (grid.grid_map[i][j] == 1)
                return false;
        
        for (i = current_i; i >= 0 ; i--)
            if (grid.grid_map[i][current_j] == 2)
                break;
            else if (grid.grid_map[i][current_j] == 1)
                return false;

        return true;
    }
    
    static int [] nextPosition(int i,int j){
       int[] cords = new int[2];
//       if (col_lizard_count[j] >= tree_count[j] + 1){
//           cords[0] = 0;
//           cords[1] = ++j;    
//       }
//       else 
           if (i < dimension - 1){
           cords[0] = ++i;
           cords[1] = j;
       } 
       else{
           cords[0] = 0;
           cords[1] = ++j;
       }
//        System.out.println("Next Cord : "+ cords[0]+" - "+ cords[1]);
       return cords;
    }
    
    static boolean shouldMoveAhead(int row, int col, int current_lizard_count){
        int next_trees = 0;
        int remaining_columns = dimension - col - 1 ;
        if(tree_count[col] > 0){       
            Integer[] col_trees = tree_map.get(col).toArray(new Integer[tree_count[col]]);
            for(int row_index : col_trees){
                if(row_index > row)
                    next_trees += 1;
            }
        }
        for (int i = col + 1; i < dimension; i++) {
           next_trees += tree_count[i]; 
        }
//        System.out.println("Rem Lizards : " + (total_lizard_count - current_lizard_count));
//        System.out.println("NT + NC : " + (next_trees + remaining_columns));
        return (total_lizard_count - current_lizard_count) <= (next_trees + remaining_columns);
    }
    
    static void printSolution(NurseryGrid grid){
        if(grid.solved){
            System.out.println("OK");
            for(int n=0;n<dimension;n++){
                for(int p=0;p<dimension;p++){
                    System.out.print(grid.grid_map[n][p]);
                }
                System.out.println("\n");
            }
        }
        else{
            System.out.println("FAIL");
        }
    }
    
    static void printTree(NurseryGrid grid){
        for(int n=0;n<dimension;n++){
                for(int p=0;p<dimension;p++){
                    System.out.print(grid.grid_map[n][p] + "  ");
                }
                System.out.println("\n");
            }
    }
    
    static boolean validSolution(NurseryGrid grid, int total_lizards, int dimension){
        ArrayList<Integer> r = new ArrayList<Integer>() ;
        ArrayList<Integer> c = new ArrayList<Integer>() ;
//        System.out.println("Dimension : "+ dimension);
        for(int n=0;n<dimension;n++){
            for(int p=0;p<dimension;p++){
                if(grid.grid_map[n][p] == 1)
//                    System.out.println(" NP : " + n + " " + p);
                    r.add(n);
                    c.add(p);
            }
        }
        for(int n=0; n<total_lizards; n++){
            System.out.println(r.get(n) +" , "+ c.get(n));
            if(canLizardBePlaced(grid, r.get(n), c.get(n)))
                return false;
        }
        return true;
    }
}
