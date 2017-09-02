/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;
import java.io.*;
/**
 *
 * @author prati
 */
class NurseryGrid{
    int lizard_count;
    int[][] grid_map;
    NurseryGrid(int lz_count,int n){
        lizard_count = 0;
        grid_map = new int[n][n];
    }
}

public class Homework {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    static int total_lizard_count; 
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        String algorithm = br.readLine();
        int dimension = Integer.parseInt(br.readLine());
        total_lizard_count = Integer.parseInt(br.readLine());
        NurseryGrid grid =  new NurseryGrid(0,dimension);
        solveGrid(grid,0,0);
    }
    
    static NurseryGrid solveGrid(NurseryGrid grid,int i , int j){
        if (grid.lizard_count == total_lizard_count){
          return grid;
        } 
    }
    
    static boolean canLizardBePlaced(NurseryGrid grid, int i, int j){
    }
    
}
