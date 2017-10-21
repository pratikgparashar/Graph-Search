/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package homework;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;
/**
 *
 * @author prati
 */
class NurseryGrid{
    int lizard_count;
    int[][] grid_map;
    boolean solved;
    
    int recent_i = 0, recent_j=0;
    NurseryGrid(int lz_count,int n, int [][] map){
        lizard_count = lz_count;
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
    //BFS SOLUTION
    static Deque<NurseryGrid> queue = new ArrayDeque<>();
    static int[] next_cords_bfs;
    static int[] temp;
    static int[] cords = new int[2];
    static Integer[] col_trees;
    static String algorithm;
    static ArrayList<int[]> queen_positions = new ArrayList<>();
    static int[] queen_attacks;
    public static void main(String[] args) throws IOException {
        BufferedReader br =  new BufferedReader(new FileReader("./input.txt"));
        algorithm = br.readLine();
        dimension = Integer.parseInt(br.readLine());
        total_lizard_count = Integer.parseInt(br.readLine());
        int [][] grid_map = new int[dimension][dimension];
        tree_count =  new int[dimension];
        col_lizard_count =  new int[dimension];
        queen_attacks = new int[total_lizard_count];

        
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
        int total_trees  = IntStream.of(tree_count).sum();
        switch(algorithm) {
            case "DFS" :
                        final NurseryGrid grid1 =  new NurseryGrid(0,dimension, grid_map); 
                        NurseryGrid grid =  new NurseryGrid(0,dimension, grid_map);
                        if(dimension  + total_trees < total_lizard_count ){
                            printSolution(grid);
                            System.exit(0);
                        }
                        ExecutorService excs_dfs = Executors.newSingleThreadExecutor();
                        Future<NurseryGrid> task = excs_dfs.submit(() -> solveGrid(grid1, 0, 0));
                        try {
                            grid = task.get(285, TimeUnit.SECONDS);
                        } catch (InterruptedException | TimeoutException | ExecutionException  newerror) {
                            excs_dfs.shutdown();
                            printSolution(grid);
                            System.exit(0);

                        }
                        excs_dfs.shutdown();
                        printSolution(grid);
                        break;
            case "BFS" :final NurseryGrid grid_bfs =  new NurseryGrid(0,dimension, grid_map);
                        NurseryGrid grid_bfs_sol =  new NurseryGrid(0,dimension, grid_map); 
                        if(dimension  + total_trees < total_lizard_count ){
                            printSolution(grid_bfs_sol);
                            System.exit(0);
                        }
                        ExecutorService exec_bfs = Executors.newSingleThreadExecutor();
                        Future<NurseryGrid> task_bfs = exec_bfs.submit(() -> solveGridBFS(grid_bfs));
                        try {
                            grid_bfs_sol = task_bfs.get(285, TimeUnit.SECONDS);
                        } catch (TimeoutException | ExecutionException  |InterruptedException e) {
                            exec_bfs.shutdown();
                            printSolution(grid_bfs_sol);
                            System.exit(0);

                        }
                        exec_bfs.shutdown();
                        printSolution(grid_bfs_sol);
                        break;
            case "SA" : 
                        NurseryGrid grid_sa =  new NurseryGrid(0,dimension, grid_map);
                        NurseryGrid grid_sa_sol =  new NurseryGrid(0,dimension, grid_map);
                        if(dimension * dimension - total_trees < total_lizard_count ){
                            printSolution(grid_sa_sol);
                            System.exit(0);
                        }
                        if(dimension  + total_trees < total_lizard_count ){
                            printSolution(grid_sa_sol);
                            System.exit(0);
                        }
			grid_sa = sovleSA(grid_sa);
                        printSolution(grid_sa);
                        break;
        }
    }
    
    static NurseryGrid solveGrid(NurseryGrid grid,int position_i , int position_j){
        int i, j;
        boolean can_placed;
        int[]  next_cord;
        if (grid.lizard_count == total_lizard_count){
            grid.solved =  true;
            return grid;
        }
        i = position_i;
        j = position_j;
        while ( i < dimension && j< dimension) {
            can_placed = canLizardBePlaced(grid,i,j);
            if (can_placed){
                grid.grid_map[i][j] = 1;
                grid.lizard_count += 1;
                col_lizard_count[j] += 1;
                next_cord = nextPosition(i,j);
                if(!shouldMoveAhead(i,j,grid.lizard_count)){
                    grid.grid_map[i][j] = 0;
                    grid.lizard_count -= 1;
                    col_lizard_count[j] -= 1;
                    i = next_cord[0];
                    j = next_cord[1];
                    break;
                }
                NurseryGrid new_g = solveGrid(grid,next_cord[0],next_cord[1]);
                if(new_g.lizard_count == total_lizard_count){
                    return new_g;
                }
                else{
                    grid.grid_map[i][j] = 0;
                    grid.lizard_count -= 1;
                    col_lizard_count[j] -= 1;
                }
            }
            else{
                next_cord = nextPosition(i,j);
            }
            i = next_cord[0];
            j = next_cord[1];
        }
        return grid;
    }
    
    static boolean canLizardBePlaced(NurseryGrid grid, int current_i, int current_j){
        int i, j;
        if(grid.grid_map[current_i][current_j] == 2) return false;
        // left check
        for (i = current_j; i >= 0; i--)
            if(grid.grid_map[current_i][i] == 2)
                break;
            else if (grid.grid_map[current_i][i] == 1)
                return false;
 
        //upper left check
        for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
            if (grid.grid_map[i][j] == 2)
                break;
            else if (grid.grid_map[i][j] == 1)
                return false;
 
        //lower left
        for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--)
            if (grid.grid_map[i][j] == 2)
                break;
            else if (grid.grid_map[i][j] == 1)
                return false;
        //upper check
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
    
    static boolean shouldMoveAhead(int row, int col, int current_lizard_count){
        int next_trees = 0;
        int remaining_columns = dimension - col - 1 ;
        if(tree_count[col] > 0){       
            Integer[] col_trees = tree_map.get(col).toArray(new Integer[tree_count[col]]);
            for(int row_index : col_trees){
                if(row_index > row)
                    next_trees += 1;
            }
            col_trees = null;
        }
        if(col_lizard_count[col] == 0){
            remaining_columns += 1;
        }
        for (int i = col + 1; i < dimension; i++) {
           next_trees += tree_count[i]; 
        }
        return (total_lizard_count - current_lizard_count) <= (next_trees + remaining_columns);
    }
    
    static void printSolution(NurseryGrid grid) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter file_write = new PrintWriter("output.txt", "UTF-8");
        if(grid.solved){
            file_write.println("OK");
            
            for(int n=0;n<dimension;n++){
                for(int p=0;p<dimension;p++){
                    file_write.print(grid.grid_map[n][p]);
                }
                file_write.println("");
            }
        }
        else{
            file_write.println("FAIL");
        }
        file_write.close();
    }
    
    static void printTree(NurseryGrid grid){
        for(int n=0;n<dimension;n++){
                for(int p=0;p<dimension;p++){
                    System.out.print(grid.grid_map[n][p] + "  ");
                }
                System.out.println("");
            }
    }
    
    
    static NurseryGrid solveGridBFS(NurseryGrid grid){
        queue.add(grid);
        //printTree(grid);
        temp = new int[dimension];
        NurseryGrid return_grid = null;
        while (!queue.isEmpty()) {
            return_grid = create_children(queue.remove());
            if(return_grid.solved){
                return return_grid;
            }
            
        }
        return grid;
    }

    static NurseryGrid create_children(NurseryGrid grid){
        int i = grid.recent_i,j = grid.recent_j;
        while (i < dimension && j < dimension) {
            if(canLizardBePlaced(grid, i, j) && (shouldMoveAhead(i,j,grid.lizard_count))){
                int[][] dst = new int[dimension][dimension];
                for (int k = 0; k < dimension; k++) 
                {
                    dst[k] = grid.grid_map[k].clone();
                }
                NurseryGrid new_grid = new NurseryGrid(grid.lizard_count ,dimension, dst);
                dst[i][j] = 1;
                new_grid.lizard_count += 1 ;
                new_grid.recent_i  = i;
                new_grid.recent_j = j;
                queue.add(new_grid);
                if(new_grid.lizard_count == total_lizard_count){
                    new_grid.solved = true;
                    return new_grid;
                }
            }
            
            next_cords_bfs = nextPosition(i, j);
            i = next_cords_bfs[0];
            j = next_cords_bfs[1];
        }
        return grid;
    }
    
    static NurseryGrid sovleSA(NurseryGrid grid){
     	int[][] map = new int[dimension][dimension];
        for (int k = 0; k < dimension; k++) 
        {
            map[k] = grid.grid_map[k].clone();
        }
        NurseryGrid next_temp = new NurseryGrid(0, dimension, grid.grid_map);
     	NurseryGrid new_grid = new NurseryGrid(0, dimension, grid.grid_map);
        double T = 100;
        //NurseryGrid curr_state = initialRandomMove(grid);
        NurseryGrid curr_state = newRandomMove(grid);
        int iteration_count = 1;
        double curr_cost = 0;
        double temp_cost = 0;
        double probability = 0.0;
        curr_cost = checkNumberOfConflictingPairs(curr_state);
        boolean ch_cal = true;
        boolean result_d = false;

     	double starttime=System.currentTimeMillis();
        while(curr_cost != 0 && System.currentTimeMillis() - starttime < 260000){
            if(ch_cal && System.currentTimeMillis() - starttime > 190000){
               ch_cal = false;
               new_grid.grid_map = map;
               new_grid = solveGrid(new_grid,0,0);
               if(!new_grid.solved) 
               {result_d = true;}
               else
               {return(new_grid);}
                   
            }
            next_temp =  createNextRandomState(curr_state);
            temp_cost = checkNumberOfConflictingPairs(next_temp);
            
            if(temp_cost < curr_cost){
                curr_state = next_temp;
                curr_cost = temp_cost;
            }
            else{
                probability =  Math.exp((-1) * (temp_cost - curr_cost)/T);
                double probability1 =  ThreadLocalRandom.current().nextDouble();
                if(probability >= probability1){
                    curr_state = next_temp;
                    curr_cost = temp_cost;
                }
            }
            T =   Math.log(1+1/T);
            iteration_count++;
        }
    	grid.solved = (result_d == true) ? false : true;
        return grid;
    }
    static NurseryGrid newRandomMove(NurseryGrid grid){
        int lizard_placed = 0,col = 0, row = 0;
        
        while(lizard_placed < total_lizard_count){
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                   if(lizard_placed < total_lizard_count && grid.grid_map[i][j] != 2 && grid.grid_map[i][j] != 1){
                        int[] coord = new int[2];
                        coord[0] = i;
                        coord[1] = j;
                        queen_positions.add(coord);
                        grid.grid_map[i][j] = 1;
                        lizard_placed += 1;
                        break;
                    } 
                }    
            }
        }
        return grid;
    }
    static NurseryGrid initialRandomMove(NurseryGrid grid){
        int lizard_placed = 0,col = 0, row = 0;
        Random rand = new Random();
        while(lizard_placed < total_lizard_count){
            int[] coord = new int[2];
            col = ThreadLocalRandom.current().nextInt(0,dimension-1);
            row = ThreadLocalRandom.current().nextInt(0,dimension-1);
            if(grid.grid_map[row][col] != 2 && grid.grid_map[row][col] != 1){
                coord[0] = row;
                coord[1] = col;
                queen_positions.add(coord);
                grid.grid_map[row][col] = 1;
                lizard_placed += 1;
            }
        }
        return grid;
    }
    
    static int selectRandomQueen(){
        int queen_number = ThreadLocalRandom.current().nextInt(0,total_lizard_count);
        return queen_number;
    }
    
    static int [] selectRandomCoordinate(NurseryGrid grid){
        int[] coord = new int[2];
        Random rand = new Random();
        double counter = 0;
        double max_count = Math.pow(dimension, 3);
        boolean placed_from_for = false;
        boolean reached_last_cell =false;
        int p =0, q =0;
//        boolean coord_set = false;
//        int queen_number = 0;
//        int[] curr_pos = new int[2];

//        int p =0 ,q= 0;
//        ArrayList<int []> possible_positions = new ArrayList<>();
//        int[][] possible_values = new int[8][8];
//        int i = rand.nextInt(dimension);
//        int j = rand.nextInt(dimension);
        int i = ThreadLocalRandom.current().nextInt(0,dimension);
        int j = ThreadLocalRandom.current().nextInt(0,dimension);
        
        
        while((counter < max_count) && (grid.grid_map[i][j] == 1 || grid.grid_map[i][j] == 2)){
            counter ++;
            i = ThreadLocalRandom.current().nextInt(0,dimension);
            j = ThreadLocalRandom.current().nextInt(0,dimension);
        }
        if(counter == max_count){
            outer_row:
            for ( p = 0; p < dimension; p++) {
                for (q = 0; q < dimension; q++) {
                   if(grid.grid_map[p][q] != 2 && grid.grid_map[p][q] != 1){
                        i = p;
                        j = q;
                        placed_from_for = true;
                        break outer_row;
                    }
                }    
            }
            if( p == dimension && q == dimension){
                try{
                    grid.solved =  false;
                    printSolution(grid);
                    System.exit(0);
                }
                catch(Exception e){}
            }
        }
        coord[0] = i;
        coord[1] = j;
//        System.out.println("NEW CORDS " + i + "  " + j);
        return coord;
    }
    
    static NurseryGrid createNextRandomState(NurseryGrid grid){
        int queen_number =  selectRandomQueen();
        int[] new_cords = selectRandomCoordinate(grid);
//        queen_number = new_cords[2];
        int[] old_cords = queen_positions.get(queen_number);
//        System.out.println("QUEEN TO BE REPLACED :" + queen_number);
//        System.out.println("QUEEN TO BE REPLACED CORDS :" + old_cords[0]+"  " + old_cords[1]);
//        System.out.println("QUEEN LIST :");;
//        for(int[] a : queen_positions){
//            System.out.print(" [" + a[0] + "," + a[1] + "]");
//        }
        queen_positions.set(queen_number, new_cords);
        grid.grid_map[old_cords[0]][old_cords[1]] = 0;
        grid.grid_map[new_cords[0]][new_cords[1]] = 1;
        return grid;
    }
    static int checkNumberOfConflictingPairs(NurseryGrid grid){
        int conflict_count = 0;
        int i =0,j=0;
        int r,c;
        int current_i =0,current_j=0;
        int queen_pos = 0;
        for (int[] lizs : queen_positions ) {
            r = lizs[0];
            c = lizs[1];
            if(grid.grid_map[r][c] == 1){
                current_i = r;
                current_j = c;
                for (i = current_j ; i >= 0; i--){
                    if (grid.grid_map[current_i][i] == 2){
                        break;
                    }
                    if (grid.grid_map[current_i][i] == 1){
                        conflict_count++;}
                }    
                conflict_count--;
                for (i = current_j ; i < dimension; i++){
                    if (grid.grid_map[current_i][i] == 2){
                        break;
                    }
                    if (grid.grid_map[current_i][i] == 1){
                        conflict_count++;}
                }
                conflict_count--;
                for (i=current_i , j=current_j ; i>=0 && j>=0; i--, j--){
                    if (grid.grid_map[i][j] ==2){
                        break;
                    }
                    if (grid.grid_map[i][j] == 1)
                    { 
                        conflict_count++;}
                }
                conflict_count--;
                for (i=current_i , j=current_j ; i< dimension  && j< dimension; i++, j++){
                    if (grid.grid_map[i][j] == 2){
                        break;
                    }
                    if (grid.grid_map[i][j] == 1)
                    { 
                        conflict_count++;}
                }
                conflict_count--;
                
                for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--){
                    if (grid.grid_map[i][j] == 2){
                        break;
                    }
                    if (grid.grid_map[i][j] == 1)
                    {
                        conflict_count++;}
                }
                conflict_count--;
                for (i=current_i, j=current_j; j<dimension && i<dimension; i++, j++){
                    if (grid.grid_map[i][j] == 2){
                        break;
                    }
                    if (grid.grid_map[i][j] == 1)
                    {
                        conflict_count++;}
                }
                conflict_count--;
                for (i = current_i; i >= 0 ; i--){                        
                    if (grid.grid_map[i][current_j] == 2){
                        break;
                    }
                    if (grid.grid_map[i][current_j] == 1)
                    { 
                        conflict_count++;}
                }
                conflict_count--;
                for (i = current_i; i <dimension ; i++){
                    if (grid.grid_map[i][current_j] == 2){
                        break;
                    }
                    if (grid.grid_map[i][current_j] == 1)
                    { 
                        conflict_count++;}
                }
                conflict_count--;
                queen_attacks[queen_pos] = conflict_count;
                queen_pos++;
            }
        }
            
        
        return conflict_count;
    }
    
}
