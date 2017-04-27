package com.wipro.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {

	Connection conn;
	public int generateSequenceNumber() throws SQLException, ClassNotFoundException
	{
		int seqno = 0 ;
		conn = DBUtil.getDBConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select transactionId_seq.nextval from dual");
		if(rs.next() && rs!=null)
		{
			 seqno = rs.getInt(1);
		}
		
		return seqno;
	}
	
	public boolean validateAccount(String accountNumber) throws SQLException, ClassNotFoundException
	{
		boolean status = false;
		conn = DBUtil.getDBConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from account_tbl where Account_Number='"+accountNumber+"'");
		if(rs.next())
		{
			status = true;
		}
		else
		{
			status = false;
		}
		
		
		return status;
	}
	
	public float findBalance(String accountNumber) throws SQLException, ClassNotFoundException
	{
		
		
		conn = DBUtil.getDBConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select Balance from account_tbl where Account_Number='"+accountNumber+"'");
		if(rs.next())
		{
			float bal = rs.getFloat(1);
			return bal;
		}
		else
		{
			return -1;
		}
	
	}
	
	public boolean transferMoney(TransferBean transferBean) throws SQLException, ClassNotFoundException
	{
		
		BankDAO b= new BankDAO();
		int seq = b.generateSequenceNumber();
		transferBean.setTransactionID(seq);
		
		conn = DBUtil.getDBConnection();
		int transactionID = transferBean.getTransactionID();
		String fromAccountNumber = transferBean.getFromAccountNumber();
		String toAccountNumber = transferBean.getToAccountNumber();
		Date dateOfTransaction = transferBean.getDateOfTransaction();
		float amount =transferBean.getAmount();
		
		long t = dateOfTransaction.getTime();
		java.sql.Date dt = new java.sql.Date(t);
		
		PreparedStatement ps =conn.prepareStatement("insert into transfer_tbl values(?,?,?,?,?)");
		ps.setInt(1, transactionID);
		ps.setString(2,fromAccountNumber);
		ps.setString(3, toAccountNumber);
		ps.setDate(4, dt);
		ps.setFloat(5, amount);
		int res = ps.executeUpdate();
		if(res == 1)
		{
			conn.commit();
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	public boolean updateBalance(String accountNumber, float newBalance) throws SQLException, ClassNotFoundException
	{
		int res=0;
		conn = DBUtil.getDBConnection();
		Statement stmt = conn.createStatement();
		 res = stmt.executeUpdate("update account_tbl set Balance='"+newBalance+"' where Account_Number='"+accountNumber+"'");
		if(res == 1)
		{
			conn.commit();
			return true;
		}
		else
			return false;
		
	}
}
