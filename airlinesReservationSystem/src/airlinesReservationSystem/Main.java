package airlinesReservationSystem;

import java.util.*;
public class Main {
	public static void main(String args[]) throws Exception{
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to Meliora Airlines");
		System.out.println("1. Login");
		System.out.println("2. New User? Sign up");
		System.out.print("Enter a number : ");
		int n =sc.nextInt();
		User u=new User();
		switch(n){
		case 1:{
		    u.login();
		    break;
		}
		case 2:
		{
			u.createUser();
			break;
		}
		}
		
	}


    
}
