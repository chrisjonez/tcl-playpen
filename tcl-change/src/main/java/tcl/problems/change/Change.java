package tcl.problems.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Change {

	private int[] dens;
	
	public Change(int[] dens){		
		Arrays.sort(dens);
		this.dens = dens;
		
		int minDens = this.dens[0];
		if (minDens < 0)
			throw new IllegalArgumentException("Denominations cannot be negative: " + minDens);	
	}
	
	private void checkDenoms(List<Integer> coins){
		for (int coin : coins){
			if (Arrays.binarySearch(dens, coin)<0)
				throw new IllegalArgumentException("Coin: " + coin + " not allowed!");							
		}
	}
	
	public static int sum(List<Integer> coins){
		return coins.stream().mapToInt(Integer::intValue).sum();		
	}
	
	public List<Integer> pay(int amount, List<Integer> coins){
		if (amount<0)
			throw new IllegalArgumentException("Amount cannot be negative: " + amount);
		
		int sum = sum(coins);
		checkDenoms(coins);
		if (sum<amount)
			throw new IllegalArgumentException("Not enough!");
		
		int changeRequired = sum - amount;
		int denIndex = dens.length-1;
		ArrayList<Integer> change = new ArrayList<Integer>();
		
		while (changeRequired>0){
			int currDen = dens[denIndex];
			changeRequired = changeRequired - currDen;
			if (changeRequired==0){
				change.add(currDen);
				break;
			}else if (changeRequired<0){
				changeRequired += currDen;
				--denIndex;
			}else{
				change.add(currDen);
			}
		}
			
		return change;
	}
	
}
