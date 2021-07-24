
public class MinCostClimbing {
//	You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.
//	You can either start from the step with index 0, or the step with index 1.
	
	public static int minCostClimbingStairs (int[] cost) {
        if (cost.length == 2) {
        	if (cost[0] < cost[1]) return cost[0];
        	else return cost[1];
        }
        int[] minCost = new int[cost.length+1];
        for (int i = 2; i < minCost.length; i++) {
        	// compare climbing 1 step to climbing 2 step
        	int oneStep = minCost[i-1] + cost[i-1];
        	int twoStep = minCost[i-2] + cost[i-2];
        	minCost[i] = Math.min(oneStep, twoStep);
        }
        return minCost[minCost.length-1];
    }
	
	public static void main (String[] args) {
		int[] cost = {10, 15,20,1,100,11};
		System.out.println(minCostClimbingStairs(cost));
	}
}
