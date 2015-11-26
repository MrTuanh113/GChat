package com.chatt.demo.model;

import java.util.Date;

import com.chatt.demo.TabFriend;



public class Conversation
{

	// Hằng này thể hiện tin nhắn đang gửi
	public static final int STATUS_SENDING = 0;

	// Hằng này thể hiện tin nhắn đã gửi
	public static final int STATUS_SENT = 1;

	// Hằng này thể hiện tin nhắn gửi thất bại
	public static final int STATUS_FAILED = 2;
	// Biến này chứa tin nhắn
	private String msg;

	// biến lưu trạng thái gửi
	private int status = STATUS_SENT;

	// ngày tháng gửi
	private Date date;

	// người gửi
	private String sender;

	/**
	 * Instantiates a new conversation.
	 * 
	 * @param msg
	 *            the msg
	 * @param date
	 *            the date
	 * @param sender
	 *            the sender
	 */
	public Conversation(String msg, Date date, String sender)
	{
		this.msg = msg;
		this.date = date;
		this.sender = sender;
	}

	/**
	 * Instantiates a new conversation.
	 */
	public Conversation()
	{
	}

	/**
	 * Gets the msg.
	 * 
	 * @return the msg
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 * Sets the msg.
	 * 
	 * @param msg
	 *            the new msg
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	/**
	 * Checks if is sent.
	 * 
	 * @return true, if is sent
	 */
	public boolean isSent()
	{
		return TabFriend.user.getUsername().equals(sender);
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Gets the sender.
	 * 
	 * @return the sender
	 */
	public String getSender()
	{
		return sender;
	}

	/**
	 * Sets the sender.
	 * 
	 * @param sender
	 *            the new sender
	 */
	public void setSender(String sender)
	{
		this.sender = sender;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

}
