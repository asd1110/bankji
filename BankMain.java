package com.wipro.bank.service;

import java.sql.SQLException;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;
import com.wipro.bank.util.InsufficientFundsException;

public class BankMain {
	
	float bal;
	public String checkBalance(String accountNumber) throws SQLException, ClassNotFoundException
	{
		BankDAO b=new BankDAO();
		
		boolean check = b.validateAccount(accountNumber);
		if(check == true)
		{
		 bal =b.findBalance(accountNumber);
		 String status = "BALANCE IS:"+bal;
		 return status;
		}
		else
		{
			return "ACCOUNT NUMBER INVALID";
		}
		
		
	}

	public String transfer(TransferBean transferBean) throws SQLException, ClassNotFoundException
	{
		BankDAO b=new BankDAO();
		if(transferBean == null)
		{
			return "INVALID";
		}
		else
		{	
			boolean stfrom = false ;
			boolean stto = false;
			if(transferBean.getFromAccountNumber() == null || transferBean.getToAccountNumber() == null)
			{
				return "INVALID ACCOUNT";
			}
			else
			{
				float fbal = b.findBalance(transferBean.getFromAccountNumber());
				float tbal = b.findBalance(transferBean.getToAccountNumber());
				
				if(transferBean.getAmount()<=fbal)
				{
					stfrom = b.updateBalance(transferBean.getFromAccountNumber(), fbal-transferBean.getAmount());
					stto = b.updateBalance(transferBean.getToAccountNumber(), tbal+transferBean.getAmount());
					boolean st=false;
					if(stfrom ==true  && stto==true)
					{
						st =b.transferMoney(transferBean);
						if(st==true)
						{
						return "SUCCESS";
						}
						else
						{
							return "INVALID ACCOUNT";
						}
					}
					else
				0		return "INVALID ACCOUNT";
				}
				else
				{
					try
					{
						throw new InsufficientFundsException();
					}
					catch(InsufficientFundsException e)
					{
						return "INSUFFICIENT FUNDS";
					}
				}
				
				
			}
			
			
			
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		BankMain bankMain = new BankMain();
		// View Balance
		System.out.println(bankMain.checkBalance("1234567890"));
		 
		// TransferMoney
		TransferBean transferBean = new TransferBean();
		transferBean.setFromAccountNumber("1234567890");
		transferBean.setAmount(500);
		transferBean.setToAccountNumber("1234567891");
		transferBean.setDateOfTransaction(new java.util.Date());
		 
		System.out.println(bankMain.transfer(transferBean));
		}
	
}
