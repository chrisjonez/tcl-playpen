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
	}
	
	private void checkDenoms(List<Integer> coins){
		for (int coin : coins){
			if (!dens.contains(coin))
				throw new IllegalArgumentException("Coin: " + coin + " not allowed!");
		}
	}
	
	public List<Integer> pay(int amount, List<Integer> coins){
		int sum = coins.stream().mapToInt(Integer::intValue).sum();
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
			}
			else if (changeRequired<0){
				changeRequired += currDen;
				denIndex++;
			}else{
				change.add(currDen);
			}
		}
			
		return change;
	}
	
}
