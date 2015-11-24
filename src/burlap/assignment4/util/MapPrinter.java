package burlap.assignment4.util;

import java.util.List;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.policy.Policy;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;

public class MapPrinter {

	public static void printMap(int[][] map){
		map = matrixToMap(map);
		System.out.println("This is your grid world:");
		for (int j = 0; j<map[0].length; j++){
	    	 System.out.print("[");
		     for (int i = 0; i<map.length; i++){
		    	 System.out.print(String.valueOf(map[j][i]));
		    	 if(i != map.length-1){
		    		 System.out.print(",");
		    	 }
		     }
	    	 System.out.print("]");

	    	 System.out.println();
		}
   	 System.out.println("\n\n");

	}
	
	public static void printPolicyMap(List<State> states, Policy p,int[][] map) {

		     
		System.out.println();
		System.out.println("This is your optimal policy:");
		
		String[][] policy = new String[map[0].length][map.length];
		
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
		policy = matrixToMap(policy);
		
		for (int j = 0; j<policy[0].length; j++){
	    	 System.out.print("[");
		     for (int i = 0; i<policy.length; i++){
		    	 String action = String.valueOf(policy[j][i]);
		    	 if(action.equals("null")) action ="*";
		    	 System.out.print(action);
		    	 if(i != policy.length-1){
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
}
