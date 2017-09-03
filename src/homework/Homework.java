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
    NurseryGrid(int lz_count,int n, int [][] map){
        lizard_count = 0;
//        grid_map = new int[n][n];
//        for(int i=0;i<n;i++){
//            for(int j=0;j<n;j++){
//                grid_map[i][j] = 0;
//            }
//        }
//        grid_map = new int [][] {{2, 0, 2}, {0, 0, 0}, {2, 0, 2}};
        grid_map = map;
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
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        System.out.println("hello");
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        String algorithm = br.readLine();
        dimension = Integer.parseInt(br.readLine());
        total_lizard_count = Integer.parseInt(br.readLine());
        int [][] grid_map = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            String line = br.readLine();
            String[] row = line.split("");
            int[] num_row = new int[dimension];
            for(int k = 0; k< dimension; k++){
                num_row[k] = Integer.parseInt(row[k]);
                
            }
            grid_map[i] = num_row;
        }
        System.out.println("hello2");
        NurseryGrid grid =  new NurseryGrid(0,dimension, grid_map);
        solveGrid(grid,0,0);
    }
    
    static NurseryGrid solveGrid(NurseryGrid grid,int position_i , int position_j){
        int i, j;
        boolean can_placed;
        int[]  next_cord;
        System.out.println("Call : " + ++czz);
        if (grid.lizard_count == total_lizard_count){
            for(int n=0;n<dimension;n++){
                for(int p=0;p<dimension;p++){
                    System.out.print(grid.grid_map[n][p] + "  ");
                }
                System.out.println("\n");
            }
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
                next_cord = nextPosition(i,j);
                NurseryGrid new_g = solveGrid(grid,next_cord[0],next_cord[1]);
                if(new_g.lizard_count == total_lizard_count){
                    return new_g;
                }
                else{
                    grid.grid_map[i][j] = 0;
                    grid.lizard_count -= 1;
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
        if(grid.grid_map[current_i][current_j] == 2) return false;
        /* Check this row on left side */
        for (i = current_j; i >= 0; i--)
            if(grid.grid_map[current_i][i] == 2)
                break;
            else if (grid.grid_map[current_i][i] == 1)
                return false;
 
        /* Check upper diagonal on left side */
        for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
            if (grid.grid_map[i][j] == 2)
                break;
            else if (grid.grid_map[i][j] == 1)
                return false;
 
        /* Check lower diagonal on left side */
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
       if (i < dimension - 1){
           cords[0] = ++i;
           cords[1] = j;
       } 
       else{
           cords[0] = 0;
           cords[1] = ++j;
       }

       return cords;
    }
    
}
