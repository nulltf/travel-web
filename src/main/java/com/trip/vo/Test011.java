package com.trip.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Test011 {

	public static void main(String[] args) {

		try {
			//1.用正则表达式判断输入的合法性
			   System.out.println("请输入第一个数字:");
			   BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			   String num1 = reader.readLine();
			   
			   while(!Pattern.compile("[1-9][0-9]*").matcher(num1).matches())//正则验证
			   {
				   System.out.println("输入的不是非零自然数，请重新输入");
				   num1 = reader.readLine();
			   }
			   
			   System.out.println("请输入第二个数字:");
			   String num2 = reader.readLine();
			   
			   while(!Pattern.compile("[1-9][0-9]*").matcher(num2).matches())
			   {
				   System.out.println("输入的不是非零自然数，请重新输入");
				   num2 = reader.readLine();
			   }
				   int result = Integer.parseInt(num1) * Integer.parseInt(num2);
				 //  System.out.println("乘积是："+ result);
			   
			  //求最大公约数和最小公倍数
			  int m = Integer.parseInt(num1);
			  int n = Integer.parseInt(num2);
			  int m_cup;
			  int n_cup;
			  int res;
			  
			  if(m>0&&n>0){
				  m_cup = m;
				  n_cup = n;
				  res = m_cup%n_cup;
				  while(res!=0){
					  m_cup = n_cup;
					  n_cup = res;
					  res = m_cup%n_cup;
				  }
				  System.out.println("最大公约数:" + n_cup);
				  System.out.println("最小公倍数:" + m*n/n_cup);
			  }
			  

			   
			   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   }
	}
}