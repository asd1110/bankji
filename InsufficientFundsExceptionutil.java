package com.wipro.bank.util;

@SuppressWarnings("serial")
public class InsufficientFundsException extends Exception {

	public String toString()
	{
		return "INSUFFICIENT FUNDS";
	}
}
