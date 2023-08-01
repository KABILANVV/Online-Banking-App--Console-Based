package main;

import java.util.Scanner;

import account.Account;
import transfer.Transfer;

public class Main {

    public static void main(String[] args) {
        
        int option=0;
		Scanner in=new Scanner(System.in);
		System.out.println("Welcome to Online Banking System...>>");
		while(true)
		{
			if(option==0) {
			System.out.println("Enter 1---> Create new Account\nEnter 2---> Login into Account for Deposit/Transaction\nEnter 3---> Exit The Application");
			System.out.print("Choice : ");
			option=in.nextInt();
			in.nextLine();
			}
			Account acc = new Account();
			if(option==1)
			{
				System.out.println("---------Enter your Details--------");
				System.out.print("username : ");
				String username=in.nextLine();
				System.out.print("password : ");
				String password=in.nextLine();
				acc=new Account(username);
				boolean done=acc.addAccount(username,password);
				if(done){
					System.out.println("Your Account is Successfully Created.");
				}
				else {
					System.out.println("Account is not Created.Try Again");
				}
				System.out.println("-------------------------------------");
				System.out.println("-------------------------------------");
				System.out.println("Do You want to Login?");
				String temp=in.nextLine().toLowerCase();
				if(temp.equals("y") ||temp.equals("yes")) {
					option=2;
				}
				else {
					option=0;
				}
				continue;
			}
			else if(option==2)
			{
				int t2=1;
				while(true) {
				
				if(t2==0) {
				System.out.println("Do you Want to Login Again??");
				String str=in.nextLine().toLowerCase();
				if(!str.equals("y") || !str.equals("yes")) {
					option=0;
					break;
					}
				}
				int accountnumber = 0;
				String pass = null;
				try {
				System.out.println("Enter your Account Number : ");
				String temp=in.nextLine();
				accountnumber=Integer.parseInt(temp);
				System.out.println("Enter your Password : ");
				pass=in.nextLine();
				t2=1;
				}
				catch(Exception e) {
					System.out.println("Wrong Input!!!");
//					continue;
				}
				if(acc.check(accountnumber,pass)) {
				String username=acc.getUsername();
				while(true) {
				System.out.println("--------Logged User : "+username+"--------");
				System.out.println("Enter 1---> View Account Details\nEnter 2---> Deposit Money\nEnter 3---> Transfer Money to another Account\nEnter 4---> Check Balance\nEnter 5---> Logout");
				System.out.print("Choice : ");
				int op=in.nextInt();
				in.nextLine();
				if(op==1) {
				System.out.println("--------Your Account Details--------");
				acc.showDetails();
				System.out.println("-------------------------------------");
				System.out.println();
				}
				else if(op==2) {
					System.out.println("Enter Amount to be Deposited : ");
					float amount=in.nextFloat();
					in.nextLine();
					acc.depositMoney(amount);
				}
				else if(op==3) {
					System.out.println("Enter Amount to be Transfered : ");
					float money=in.nextFloat();
					in.nextLine();
					System.out.println("Enter Account Number for Money to be Transfered : ");
					String accno=in.nextLine();
					Transfer mt=new Transfer();
					if(mt.check(acc.getAccno(),money)) {
						mt.transfer(acc.getAccno(),Integer.parseInt(accno),money);
					}
					else {
						System.out.println("Available balance is low !! Can't Transfer Money");
					}
				}
				else if(op==4) {

					Transfer mt=new Transfer();
					
					float bal=mt.check(acc.getAccno());
					if(bal!=-1) {
						System.out.println("Your Balance : "+bal);
						System.out.println("-------------------------------------");
					}
				}
				else {
					option=0;
					t2=0;
					acc=new Account();
					break;
				}
				}
				}
				else {
					t2=0;
					System.out.println("----------------------------------------------");
					System.out.println("Wrong Credentials!!!			  ");
					System.out.println("Try Again!!!				  ");
					System.out.println("----------------------------------------------");
				}
				}
			}
			else if(option==4)
			{

				System.out.print("Thank You For Visting");
				in.close();
				System.exit(0);
			}
			else 
			{
				System.out.println("Invalid input");
			}
			
		}
    }
}
