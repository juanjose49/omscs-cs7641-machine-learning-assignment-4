package assignment4.util;

import java.util.List;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.policy.Policy;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;

public class MapPrinter {

	public static void printMap(int[][] map){
		int numCols = map[0].length;
		int numRows = map.length;
		System.out.println("This is your grid world:");
		for (int row = 0; row<numRows; row++){
	    	 System.out.print("[");
		     for (int col = 0; col<numCols; col++){
		    	 System.out.print(String.valueOf(map[row][col]));
		    	 if(col != numCols-1){
		    		 System.out.print(",");
		    	 }
		     }
	    	 System.out.print("]");

	    	 System.out.println();
		}
   	 System.out.println("\n\n");

	}
	
	public static void printPolicyMap(List<State> states, Policy p,int[][] matrix) {

		System.out.println();
		System.out.println("This is your optimal policy:");
		String[][] policy = new String[matrix.length][matrix[0].length];
		System.out.println("num of rows in policy is " + policy.length);     

		
		for(State state : states){
			ObjectInstance agent = state.getObject("agent0");
			List<Value> values = agent.getValues();
			Value xValue = values.get(0);
			int x = xValue.getDiscVal();
			Value yValue = values.get(1);
			int y = yValue.getDiscVal();
			
			String action;
			if(p instanceof GreedyQPolicy){
				action = p.getAction(state).toString();
			}else{
			action = p.getAction(state).toString();			
			}
			switch(action){
			case "east": 
				action = ">";
				break;
			case "north":
				action = "^";
				break;
			case "west":
				action = "<";
				break;
			case "south":
				action = "v";
				break;
			default:
				action = "*";
			}
			policy[x][y] = action;

		}
		
		String[][] printPolicy = matrixToMap(policy);
		
		int numCols = printPolicy[0].length;
		int numRows = printPolicy.length;
		for (int row = 0; row<numRows; row++){
	    	 System.out.print("[");
		     for (int col = 0; col<numCols; col++){
		    	 String action = String.valueOf(printPolicy[row][col]);
		    	 if(action.equals("null")) action ="*";
		    	 System.out.print(action);
		    	 if(col != numCols-1){
		    		 System.out.print(",");
		    	 }
		     }
	    	 System.out.print("]");

	    	 System.out.println();
		}
//		for(State state : vi.getAllStates()){
//			System.out.println(state.toString()+":"+p.getAction(state).toString());
//		}
	}
	public static String[][] mapToMatrix(String[][] map) {
		// its rotated and inverted
		int numMapRows = map.length;
		int numMapCols = map[0].length;
		String[][] matrix = new String[numMapCols][numMapRows];
		for (int matrixRow = 0; matrixRow<numMapCols; matrixRow++){
			for (int matrixCol = 0; matrixCol<numMapRows; matrixCol++){
				matrix[matrixRow][matrixCol] = map[numMapRows-1-matrixCol][matrixRow];
			}
		}
		return matrix;
	}

	public static String[][] matrixToMap(String[][] matrix) {
		// its rotated and inverted
		int numMatrixRows = matrix.length;
		int numMatrixCols = matrix[0].length;
		String[][] map = new String[numMatrixCols][numMatrixRows];
		for (int mapRow = 0; mapRow<numMatrixCols; mapRow++){
			for (int mapCol = 0; mapCol<numMatrixRows; mapCol++){
				map[mapRow][mapCol] = matrix[mapCol][numMatrixCols-1-mapRow];
			}
		}
		return map;
	}

	public static int[][] matrixToMap(int[][] matrix) {
		// its rotated and inverted
		int numMatrixRows = matrix.length;
		int numMatrixCols = matrix[0].length;
		int[][] map = new int[numMatrixCols][numMatrixRows];
		for (int mapRow = 0; mapRow<numMatrixCols; mapRow++){
			for (int mapCol = 0; mapCol<numMatrixRows; mapCol++){
				map[mapRow][mapCol] = matrix[mapCol][numMatrixCols-1-mapRow];
			}
		}
		return map;
	}

	public static int[][] mapToMatrix(int[][] map) {
		// its rotated and inverted
		int numMapRows = map.length;
		int numMapCols = map[0].length;
		int[][] matrix = new int[numMapCols][numMapRows];
		for (int matrixRow = 0; matrixRow<numMapCols; matrixRow++){
			for (int matrixCol = 0; matrixCol<numMapRows; matrixCol++){
				matrix[matrixRow][matrixCol] = map[numMapRows-1-matrixCol][matrixRow];
			}
		}
		return matrix;
	}
}
