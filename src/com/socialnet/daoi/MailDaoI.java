package com.socialnet.daoi;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Vector;

import com.socialnet.bean.MailDTO;
import com.socialnet.exception.ConnectionException;

public interface MailDaoI {
	public boolean sendMail(MailDTO mail) throws ConnectionException,
			SQLException;

	public Vector<MailDTO> mailContacts() throws ConnectionException,
			SQLException;

	public Vector<MailDTO> viewMails(MailDTO mail) throws ConnectionException,
			SQLException;

	public boolean deleteMails(int msgid, String mailbox)
			throws ConnectionException, SQLException;

	public Vector<MailDTO> viewMail(int messageid, String mailbox)
			throws ConnectionException, SQLException;

	public boolean deleteTips(int messageidx) throws ConnectionException,
			SQLException;

	public boolean insestAttachment(MailDTO mail) throws ConnectionException,
			FileNotFoundException;

	public Vector<MailDTO> viewMailAttachment(int messageid, String mailbox,
			String realpath) throws ConnectionException;
}