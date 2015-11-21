package burlap.assignment4.util;

import java.util.List;

import burlap.assignment4.easy.EasyGridWorld;
import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.policy.Policy;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;

public class MapPrinter {

	public static void printMap(int[][] map){
		System.out.println("/////Grid World Analysis/////\n");
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
}
