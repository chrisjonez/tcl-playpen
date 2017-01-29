package tcl.problems.change;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class ChangeTest {

	static int[] dens = {1, 2, 5, 10, 20, 50, 100, 200};
	
	static int[] negativeDens = {1, 2, 5, 10, 20, -50, 100, 200};
	
	static int AMOUNT = 100;
	
	@Test(expected=IllegalArgumentException.class)
	public void notEnough(){
		Change cashRegister = new Change(dens);
		cashRegister.pay(AMOUNT, Arrays.asList(1, 2));
	}
		
	@Test
	public void negativeAmountAssertMessage(){
		Change cashRegister = new Change(dens);
		try{
			cashRegister.pay(-1, Arrays.asList(1, 2));
			fail();
		}catch(IllegalArgumentException e){
			assertThat(e.getMessage(), is("Amount cannot be negative: -1"));
		}
	}
	
	@Test
	public void negativeDens(){
		try{
			new Change(negativeDens);
			fail();
		}catch(IllegalArgumentException e){
			assertThat(e.getMessage(), is("Denominations cannot be negative: -50"));
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongDeno(){
		Change cashRegister = new Change(dens);
		cashRegister.pay(AMOUNT+1, Arrays.asList(1, 2));
	}
	
	@Test
	public void wrongDenoAssertExceptionMessage(){
		Change cashRegister = new Change(dens);
		try{
			cashRegister.pay(AMOUNT+1, Arrays.asList(101, 2));
			fail();
		}catch (IllegalArgumentException ex){
			assertThat(ex.getMessage(), is("Coin: 101 not allowed!"));
		}
	}
	
	@Test
	public void justEnough(){
		Change cashRegister = new Change(dens);
		List<Integer> given = Arrays.asList(100);
		List<Integer> change = cashRegister.pay(AMOUNT, given);
		assertThat(change.toString(), is("[]"));
		assertBalance(AMOUNT, given, change);
	}
	
	@Test
	public void oneOver(){
		Change cashRegister = new Change(dens);
		List<Integer> given = Arrays.asList(100, 1);
		List<Integer> change = cashRegister.pay(AMOUNT, given);
		assertThat(change.toString(), is("[1]"));
		assertBalance(AMOUNT, given, change);
	}
	
	@Test
	public void twoOver(){
		Change cashRegister = new Change(dens);
		List<Integer> given = Arrays.asList(100, 1, 1);
		List<Integer> change = cashRegister.pay(AMOUNT, given);
		assertThat(change.toString(), is("[2]"));
		assertBalance(AMOUNT, given, change);
	}
	
	@Test
	public void threeOver(){
		Change cashRegister = new Change(dens);
		List<Integer> given = Arrays.asList(100, 1, 2);
		List<Integer> change = cashRegister.pay(AMOUNT, given);
		assertThat(change.toString(), is("[2, 1]"));
		assertBalance(AMOUNT, given, change);
	}
	
	@Test
	public void oneUnder(){
		Change cashRegister = new Change(dens);
		try{
			cashRegister.pay(AMOUNT, Arrays.asList(99));
			fail();
		}catch (IllegalArgumentException ex){
			assertThat(ex.getMessage(), is("Coin: 99 not allowed!"));
		}
	}
	
	@Test
	public void fiftyPenceChange(){
		Change cashRegister = new Change(dens);
		List<Integer> given = Arrays.asList(100, 50, 1);
		List<Integer> change = cashRegister.pay(AMOUNT+1, given);
		assertThat(change.toString(), is("[50]"));
		assertBalance(AMOUNT+1, given, change);
		
		given = Arrays.asList(100, 50);
		change = cashRegister.pay(AMOUNT+1, given);
		assertThat(change.toString(), is("[20, 20, 5, 2, 2]"));
		assertBalance(AMOUNT+1, given, change);
	}
	
	private void assertBalance(int amount, List<Integer> given, List<Integer> change){
		assertThat(Change.sum(change)+amount, is(Change.sum(given)));
	}
}
