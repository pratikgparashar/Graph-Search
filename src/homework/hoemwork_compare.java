//
//package homework;
//import java.io.*;
//import java.util.*;
///**
// *
// * @author prati
// */
//class NurseryGrid{
//    int lizard_count;
////    int[][] grid_map;
//    boolean solved;
//    Map<String,Integer> grid_map;
//    NurseryGrid(int lz_count,int n, HashMap<String,Integer> map){
//        lizard_count = 0;
//        grid_map = map;
//        solved =  false;
//;    }
//}
//
//public class hoemwork_compare {
//
//    /**
//     * @param args the command line arguments
//     * @throws java.io.IOException
//     */
//    static int total_lizard_count = 5; 
//    static int dimension = 3;
//    static double czz = 0;
//    public static void main(String[] args) throws IOException {
//        // TODO code application logic here
////        System.out.println("hello");
////        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader br =  new BufferedReader(new FileReader("./input.txt"));
//        String algorithm = br.readLine();
//        dimension = Integer.parseInt(br.readLine());
//        total_lizard_count = Integer.parseInt(br.readLine());
////        int [][] grid_map = new int[dimension][dimension];
//        HashMap<String,Integer> grid_map =new HashMap<String,Integer>();
//        for (int i = 0; i < dimension; i++) {
//            String line = br.readLine();
//            String[] row = line.split("");
//            int[] num_row = new int[dimension];
//            for(int k = 0; k< dimension; k++){
//                if(!"0".equals(row[k])){
//                    System.out.println(i+""+k+" = "+row[k]);
//                    grid_map.put(i+""+k, Integer.parseInt(row[k])) ;
//                }
//                
//            }
////            grid_map[i] = num_row;
//        }
////        System.out.println("hello2");
//        NurseryGrid grid =  new NurseryGrid(0,dimension, grid_map);
//        grid = solveGrid(grid,0,0);
//        printSolution(grid);
//    }
//    
//    static NurseryGrid solveGrid(NurseryGrid grid,int position_i , int position_j){
//        int i, j;
//        boolean can_placed;
//        int[]  next_cord;
////        System.out.println("Call : " + ++czz);
//        if (grid.lizard_count == total_lizard_count){
//            grid.solved =  true;
//            return grid;
//        }
//        i = position_i;
//        j = position_j;
//        while ( i < dimension && j< dimension) {
////            System.out.println("I =" + i + " J = " + j);
////            System.out.println(grid.lizard_count);
//            can_placed = canLizardBePlaced(grid,i,j);
////            System.out.println(can_placed);
//            next_cord = nextPosition(i,j);
////            if(can_placed) System.out.println("I =" + i + " J = " + j);
//            if (can_placed){
////                grid.grid_map[i][j] = 1;
//                grid.grid_map.put(i+""+j , 1);
//                grid.lizard_count += 1;
////                next_cord = nextPosition(i,j);
//                NurseryGrid new_g = solveGrid(grid,next_cord[0],next_cord[1]);
//                if(new_g.lizard_count == total_lizard_count){
//                    return new_g;
//                }
//                else{
////                    grid.grid_map[i][j] = 0;
//                    grid.grid_map.put(i+""+j , 0);
//                    grid.lizard_count -= 1;
//                }
//            }
//            else{
////                next_cord = nextPosition(i,j);
////                solveGrid(grid,next_cord[0],next_cord[1]);
//            }
//            i = next_cord[0];
//            j = next_cord[1];
//        }
//        return grid;
//    }
//    
//    static boolean canLizardBePlaced(NurseryGrid grid, int current_i, int current_j){
////        int i, j;
////        if(grid.grid_map[current_i][current_j] == 2) return false;
////        /*left side */
////        for (i = current_j; i >= 0; i--)
////            if(grid.grid_map[current_i][i] == 2)
////                break;
////            else if (grid.grid_map[current_i][i] == 1)
////                return false;
//// 
////        /*upper diagonal left */
////        for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
////            if (grid.grid_map[i][j] == 2)
////                break;
////            else if (grid.grid_map[i][j] == 1)
////                return false;
//// 
////        /* lower diagonal left*/
////        for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--)
////            if (grid.grid_map[i][j] == 2)
////                break;
////            else if (grid.grid_map[i][j] == 1)
////                return false;
////        
////        for (i = current_i; i >= 0 ; i--)
////            if (grid.grid_map[i][current_j] == 2)
////                break;
////            else if (grid.grid_map[i][current_j] == 1)
////                return false;
////
////        return true;
//
//
////        int i, j;
////        if( grid.grid_map.containsKey(current_i+""+current_j) && grid.grid_map.get(current_i+""+current_j) == 2) return false;
////        /*left side */
////        for (i = current_j; i >= 0; i--)
////            if(grid.grid_map.get(current_i+""+i) == 2)
////                break;
////            else if (grid.grid_map.get(current_i+""+i) == 1)
////                return false;
////
////        /*upper diagonal left */
////        for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
////            if (grid.grid_map.get(i+""+j) == 2)
////                break;
////            else if (grid.grid_map.get(i+""+j) == 1)
////                return false;
////
////        /* lower diagonal left*/
////        for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--)
////            if (grid.grid_map.get(i+""+j) == 2)
////                break;
////            else if (grid.grid_map.get(i+""+j) == 1)
////                return false;
////
////        for (i = current_i; i >= 0 ; i--)
////            if (grid.grid_map.get(i+""+current_j) == 2)
////                break;
////            else if (grid.grid_map.get(i+""+current_j) == 1)
////                return false;
////
////        return true;
////        int i, j;
////    if(grid.grid_map.containsKey(current_i+""+current_j) && grid.grid_map.get(current_i+""+current_j) == 2) return false;
////    /*left side */
////    for (i = current_j; i >= 0; i--)
////        if(grid.grid_map.containsKey(current_i+""+i) && grid.grid_map.get(current_i+""+i) == 2)
////            break;
////        else if (grid.grid_map.containsKey(current_i+""+i) && grid.grid_map.get(current_i+""+i) == 1)
////            return false;
////
////    /*upper diagonal left */
////    for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--)
////        if (grid.grid_map.containsKey(i+""+j) && grid.grid_map.get(i+""+j) == 2)
////            break;
////        else if (grid.grid_map.containsKey(i+""+j) && grid.grid_map.get(i+""+j) == 1)
////            return false;
////
////    /* lower diagonal left*/
////    for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--)
////        if (grid.grid_map.containsKey(i+""+j) && grid.grid_map.get(i+""+j) == 2)
////            break;
////        else if (grid.grid_map.containsKey(i+""+j) && grid.grid_map.get(i+""+j) == 1)
////            return false;
////    
////    for (i = current_i; i >= 0 ; i--)
////        if (grid.grid_map.containsKey(i+""+current_j) && grid.grid_map.get(i+""+current_j) == 2)
////            break;
////        else if (grid.grid_map.containsKey(i+""+current_j) && grid.grid_map.get(i+""+current_j) == 1)
////            return false;
////
////    return true;
//    int i, j, val = 3;
//    boolean con;
//    if(grid.grid_map.containsKey(current_i+""+current_j) && grid.grid_map.get(current_i+""+current_j) == 2) 
//        return false;
//    /*left side */
//    for (i = current_j; i >= 0; i--){
//        con = grid.grid_map.containsKey(current_i+""+i) ;
//        if(con)
//            val = grid.grid_map.get(current_i+""+i) ;
//        if(con && (val == 2))
//            break;
//        else if (con && val == 1)
//            return false;
//    }    
//    /*upper diagonal left */
//    for (i=current_i, j=current_j; i>=0 && j>=0; i--, j--){
//        con = grid.grid_map.containsKey(i+""+j) ;
//        if(con) 
//            val = grid.grid_map.get(i+""+j) ;
//        if(con && (val == 2))
//            break;
//        else if (con && val == 1)
//            return false;
//    }    
//    /* lower diagonal left*/
//    for (i=current_i, j=current_j; j>=0 && i<dimension; i++, j--){
//        con = grid.grid_map.containsKey(i+""+j) ;
//        if(con){ 
//            val = grid.grid_map.get(i+""+j) ;}
//        if(con && (val == 2))
//            break;
//        else if (con && val == 1)
//            return false;
//    }
//    for (i = current_i; i >= 0 ; i--){
//        con = grid.grid_map.containsKey(i+""+current_j) ;
//        if(con) 
//            val = grid.grid_map.get(i+""+current_j) ;
//        if(con && (val == 2))
//            break;
//        else if (con && val == 1)
//            return false;
//    }    
//    return true;
//    
//        }
//
//        static int [] nextPosition(int i,int j){
//           int[] cords = new int[2];
//           if (i < dimension - 1){
//               cords[0] = ++i;
//               cords[1] = j;
//           } 
//           else{
//               cords[0] = 0;
//               cords[1] = ++j;
//           }
//
//           return cords;
//    }
//    
//    static void printSolution(NurseryGrid grid){
//        if(grid.solved){
//            System.out.println("OK");
//            for(int n=0;n<dimension;n++){
//                for(int p=0;p<dimension;p++){
////                    System.out.print(grid.grid_map[n][p]);
//                    System.out.print(grid.grid_map.get(n+""+p));
//                }
//                System.out.println("\n");
//            }
//        }
//        else{
//            System.out.println("FAIL");
//        }
//    }
//    
//    static void printTree(NurseryGrid grid){
//        for(int n=0;n<dimension;n++){
//                for(int p=0;p<dimension;p++){
////                    System.out.print(grid.grid_map[n][p]);
//                    System.out.print(grid.grid_map.get(n+""+p));
//                }
//                System.out.println("\n");
//            }
//    }
//}
//
