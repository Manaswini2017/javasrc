package com.socialnet.deleget;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Vector;

import com.socialnet.bean.MailDTO;

import com.socialnet.exception.ConnectionException;
import com.socialnet.servicei.MailServiceI;
import com.socialnet.serviceimpl.MailServiceImpl;

public class MailDelegate {
	MailServiceI usi = new MailServiceImpl();
	boolean flag = false;
	

	public Vector<MailDTO> mailContacts() throws ConnectionException,
			SQLException {
		return usi.mailContacts();
	}

	public boolean sendMail(MailDTO mail) throws ConnectionException,
			SQLException {
		flag = usi.sendMail(mail);
		return flag;
		
	}	
  

	public Vector<MailDTO> viewMails(MailDTO mail) throws ConnectionException,
			SQLException {
		return usi.viewMails(mail);
	}

	public boolean deleteMails(int msgid, String mailbox)
			throws ConnectionException, SQLException {
		flag = usi.deleteMails(msgid, mailbox);
		return flag;
	}

	public Vector<MailDTO> viewMail(int messageid, String mailbox)
			throws ConnectionException, SQLException {
		return usi.viewMail(messageid, mailbox);
	}

	public boolean deleteTips(int messageid) throws ConnectionException,
			SQLException {
		return usi.deleteTips(messageid);
	}

	public boolean insestAttachment(MailDTO mail) throws FileNotFoundException,
			ConnectionException {
		// TODO Auto-generated method stub
		return usi.insestAttachment(mail);
	}

	public Vector<MailDTO> viewMailAttachment(int messageid, String mailbox, String realpath) throws ConnectionException {
		// TODO Auto-generated method stub
		return usi.viewMailAttachment(messageid, mailbox,realpath);
	}

}
