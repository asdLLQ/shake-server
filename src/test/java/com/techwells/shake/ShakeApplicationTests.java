package com.techwells.shake;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techwells.shake.dao.NumMapper;
import com.techwells.shake.domain.Num;



@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class ShakeApplicationTests {
	
	@Resource
	private NumMapper numMapper;
	
	@Test
	public void contextLoads() {
		for(int i=9;i<=12;i++){
			for(int j=3;j<=17;j++){
//				if (j==6||j==7||j==8) {
//					continue;
//				}
				Num num=new Num();
				num.setCreatedDate(new Date());
				num.setLevel(2);   //C柜
				String a="D"+i+"-"+j;  //编号
				num.setNumber(a);
				
				int count=numMapper.insertSelective(num);
				if (count==0) {
					System.out.println(a);
				}
			}
		}
		
		
		
	}

}
