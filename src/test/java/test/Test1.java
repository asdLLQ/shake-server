package test;

import java.util.Random;

import org.junit.Test;

public class Test1 {
	
	@Test
	public void test1(){
		while(true){
			int random=new Random().nextInt(2)+1;   //[0,2)
			System.out.println(random);
		}
	}
}
