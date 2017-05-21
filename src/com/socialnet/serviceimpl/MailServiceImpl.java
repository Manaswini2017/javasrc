package com.socialnet.serviceimpl;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Vector;

import com.socialnet.bean.MailDTO;

import com.socialnet.daoi.MailDaoI;
import com.socialnet.daoimpl.MailDaoImpl;
import com.socialnet.exception.ConnectionException;
import com.socialnet.servicei.MailServiceI;

public class MailServiceImpl implements MailServiceI {

	boolean flag = false;
	
	MailDaoI mdaoi = new MailDaoImpl();

	public Vector<MailDTO> mailContacts() throws ConnectionException,
			SQLException {
		return mdaoi.mailContacts();
	}

	public boolean sendMail(MailDTO mail) throws ConnectionException,
			SQLException {
		flag = mdaoi.sendMail(mail);
		return flag;
	}

	
	public Vector<MailDTO> viewMails(MailDTO mail) throws ConnectionException,
			SQLException {
		return mdaoi.viewMails(mail);
	}

	public boolean deleteMails(int msgid, String mailbox)
			throws ConnectionException, SQLException {
		flag = mdaoi.deleteMails(msgid, mailbox);
		return flag;
	}

	public Vector<MailDTO> viewMail(int messageid, String mailbox)
			throws ConnectionException, SQLException {
		return mdaoi.viewMail(messageid, mailbox);
	}

	public boolean deleteTips(int msgid) throws ConnectionException,
			SQLException {
		flag = mdaoi.deleteTips(msgid);
		return flag;
	}

	public boolean insestAttachment(MailDTO mail) throws FileNotFoundException,
			ConnectionException {
		// TODO Auto-generated method stub
		return mdaoi.insestAttachment(mail);
	}

	public Vector<MailDTO> viewMailAttachment(int messageid, String mailbox,
			String realpath) throws ConnectionException {
		// TODO Auto-generated method stub
		return mdaoi.viewMailAttachment(messageid, mailbox, realpath);
	}
}
