package tcl.problems.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Change {

	List<Integer> dens;
	
	public Change(int[] dens){		
		this.dens = Arrays.stream(dens).boxed().sorted(Collections.reverseOrder()).collect(Collectors.toList());
		if (this.dens.get(dens.length-1) < 0)
			throw new IllegalArgumentException("Denominations cannot be negative: " + this.dens.get(dens.length-1));	
	}
	
	private void checkDenoms(List<Integer> coins){		
		for (int coin : coins){
			if (!dens.contains(coin))
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
		int denIndex = 0;
		ArrayList<Integer> change = new ArrayList<Integer>();
		
		while (changeRequired>0){
			int currDen = dens.get(denIndex);
			changeRequired = changeRequired - currDen;
			if (changeRequired==0){
				change.add(currDen);
				break;
			}else if (changeRequired<0){
				changeRequired += currDen;
				denIndex++;
			}else{
				change.add(currDen);
			}
		}
			
		return change;
	}
	
}
