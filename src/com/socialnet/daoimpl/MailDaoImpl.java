package com.socialnet.daoimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import com.socialnet.bean.NotesDTO;
import com.socialnet.bean.MailDTO;
import com.socialnet.bean.UserBean;
import com.socialnet.conections.AbstractDataAccessObject;
import com.socialnet.dao.SqlConstants;
import com.socialnet.daoi.MailDaoI;
import com.socialnet.exception.ConnectionException;
import com.socialnet.util.DateWrapper;

public class MailDaoImpl implements MailDaoI {
	Connection con;
	PreparedStatement pstmt, pstmt1;
	Statement stmt;
	ResultSet rs, rs1;
	boolean flag = false;

	
	public void closeConnection() throws ConnectionException {
		try {
			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt.close();
			if (stmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException ex) {
			throw new ConnectionException(
					"Server Busy please Try after Sometine");
		}
	}

	
	public boolean deleteMails(int msgid, String mailbox)
			throws ConnectionException {
		boolean flag = false;
      System.out.println(msgid+"=msgid");
      System.out.println(mailbox+"=mailbox");
		try {
			con = AbstractDataAccessObject.getConnection();
			if (mailbox.equals("inbox"))
			{   pstmt1 = con.prepareStatement(SqlConstants._DELETE_MAIL12);
				pstmt = con.prepareStatement(SqlConstants._DELETE_MAIL1);
			    
			}
			else if (mailbox.equals("outbox"))
			{ 
				pstmt1 = con.prepareStatement(SqlConstants._DELETE_MAIL2);
				pstmt = con.prepareStatement(SqlConstants._DELETE_MAIL);
			   
			}
			
			pstmt1.setInt(1, msgid);
			pstmt.setInt(1, msgid);
			int i = pstmt1.executeUpdate();
			int j = pstmt.executeUpdate();
			if (i > 0 && j > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		}

		finally {
			closeConnection();
		}
		return flag;
	}

	public boolean deletefilterMails(int msgid)
	throws ConnectionException {
    boolean flag = false;

   try {
	con = AbstractDataAccessObject.getConnection();
	
		pstmt = con.prepareStatement(SqlConstants._DELETE_FILTER_MAIL);
	
	pstmt.setInt(1, msgid);
	int i = pstmt.executeUpdate();
	if (i > 0) {
		flag = true;
	} else
		flag = false;
} catch (SQLException e) {
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
}

finally {
	closeConnection();
}
return flag;
}

	
	
	
	
	public boolean deleteTips(int messageidx) throws ConnectionException,
			SQLException {
		boolean flag = false;

		try {
			con = AbstractDataAccessObject.getConnection();
			pstmt = con.prepareStatement(SqlConstants._TIPS_SUGGESTION);
			pstmt.setInt(1, messageidx);
			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		} finally {
			closeConnection();
		}
		return flag;
	}

	/**
	 * The mailContacts method of the MailDaoImpl Class. <br>
	 * 
	 * This method is called when to view mailContacts.
	 * 
	 * @throws SQLException
	 *             if an error occurred
	 * @throws NullPointerException
	 *             if an error occurred
	 * @return vector with mail details depends upon operations.
	 */
	public Vector<MailDTO> mailContacts() throws ConnectionException,
			SQLException {
		Vector<MailDTO> vmail = new Vector<MailDTO>();
		MailDTO mail = null;
		try {
			con = AbstractDataAccessObject.getConnection();
			pstmt = con.prepareStatement(SqlConstants._MAIL_CONTACTS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mail = new MailDTO();
				mail.setMailid(rs.getString(1));

				vmail.add(mail);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		} finally {
			closeConnection();
		}
		return vmail;
	}

	/**
	 * The viewMails method of the MailDaoImpl Class. <br>
	 * 
	 * This method is called when to view mails.
	 * 
	 * @throws SQLException
	 *             if an error occurred
	 * @throws NullPointerException
	 *             if an error occurred
	 * @return vector with mail details depends upon operations.
	 */
	public Vector<MailDTO> viewMails(MailDTO MailDTO)
			throws ConnectionException {
		Vector<MailDTO> vmail = new Vector<MailDTO>();
		String empid = MailDTO.getEmpid();
		System.out.println("emmmmmmmp"+empid);
		String mailbox = MailDTO.getRequesttype();
		System.out.println("aaaaaaaaaaaaaaaama main box"+mailbox);
		MailDTO mail = null;
		try {
			con = AbstractDataAccessObject.getConnection();
			
			System.out.println(con);
			if (mailbox.equals("outbox"))
				pstmt = con.prepareStatement(SqlConstants._VIEW_OUT_MAILS);
			else
				pstmt = con.prepareStatement(SqlConstants._VIEW_IN_MAILS);
			pstmt.setString(1, empid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mail = new MailDTO();
				mail.setMessageid(rs.getInt(1));
				mail.setFrommailid(rs.getString(2));
				mail.setTomailid(rs.getString(3));
				// pstmt = con.prepareStatement(SqlConstants._VIEW_MAILID);
				// pstmt.setInt(1, rs.getInt(3));
				// System.out.println(rs.getInt(3));
				// rs1 = pstmt.executeQuery();
				// if (rs1.next()) {
				// mail.setEmploginid(rs1.getString(1));
				// }
				if (mailbox.equals("outbox"))
					pstmt1 = con
							.prepareStatement("select count(*) from OUTBOX_MAIL_ATTACHMENT where ATTACHMENTMAILID="
									+ rs.getInt(1));
				else
					pstmt1 = con
							.prepareStatement("select count(*) from INBOX_MAIL_ATTACHMENT where ATTACHMENTMAILID="
									+ rs.getInt(1));

				ResultSet resultSet = pstmt1.executeQuery();

				mail.setMailsub(rs.getString(4));
				mail.setMailbody(rs.getString(5));
				mail.setMaildate(DateWrapper.parseDate(rs.getDate(6)));
				mail.setMailbox(mailbox);
				if (resultSet.next()) {
					System.out.println(resultSet.getInt(1));
					mail.setAttachcount(resultSet.getInt(1));
				}
				vmail.add(mail);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				System.out.println(se);
			}
		} finally {
			closeConnection();
		}

		return vmail;
	}

	
	public boolean sendMail(MailDTO mail) throws ConnectionException,
			SQLException {
		boolean flag = false;
		try {
			con = AbstractDataAccessObject.getConnection();
			String frommailid = mail.getFrommailid();
			String empid = mail.getEmpid();
			System.out.println("totttttttmain"+empid);
			System.out.println("frommmmmmmm"+frommailid);
			String mailsub = mail.getMailsub();
			String mailbody = mail.getMailbody();
			pstmt = con.prepareStatement(SqlConstants._SEND_MAIL);
			pstmt1 = con.prepareStatement(SqlConstants._SEND_MAIL1);
			pstmt.setString(1, frommailid);
			pstmt1.setString(1, frommailid);
			pstmt.setString(2, empid);
			pstmt1.setString(2, empid);
			pstmt.setString(3, mailsub);
			pstmt1.setString(3, mailsub);
			pstmt.setString(4, mailbody);
			pstmt1.setString(4, mailbody);

			int i = pstmt.executeUpdate();
			int j = pstmt1.executeUpdate();
			if (i > 0 && j > 0) {
				flag = true;
				con.commit();
			} else
				flag = false;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		}

		finally {
			closeConnection();
		}
		return flag;
	}

	
	
	public boolean sendFilterMail(MailDTO mail) throws ConnectionException,
	SQLException {
boolean flag = false;
try {
	con = AbstractDataAccessObject.getConnection();
	String senderid = mail.getSenderid();
	String reciverid = mail.getReceiver();
	System.out.println("totttttttmain"+senderid);
	System.out.println("frommmmmmmm"+reciverid);
	String mailsub = mail.getSubject();
	String mailbody = mail.getMessage();
	pstmt = con.prepareStatement(SqlConstants._SEND_FILTER_MAIL);
	
	pstmt.setString(1, senderid);
	pstmt.setString(2, reciverid);
	pstmt.setString(3, mailsub);
	pstmt.setString(4, mailbody);
	

	int i = pstmt.executeUpdate();
	
	if (i > 0 ) {
		flag = true;
		con.commit();
	} else
		flag = false;
} catch (SQLException e) {
	try {
		con.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
}

finally {
	closeConnection();
}
return flag;
}

	
	
	
	
	
	
	public boolean filterMail(MailDTO mail) throws ConnectionException,
	SQLException {
    boolean flag = false;
    try {
	con = AbstractDataAccessObject.getConnection();
	 String sid=mail.getSenderid();
	 String rid=mail.getReceiver();
	 String sub=mail.getSubject();
	 String msg=mail.getMessage();
	 String bw=mail.getCword();
	 String cw=mail.getBword();
	 String cpw=mail.getCpword();
	  
	 int tc=mail.getTc(); 
	 int pcn=mail.getPc();  
	 int em=mail.getEm(); 
	 int qm=mail.getQm(); 
			 
	pstmt = con.prepareStatement(SqlConstants._FILTER_MAIL);
	pstmt.setString(1, sid);
	pstmt.setString(2, rid);
	pstmt.setString(3, sub);
	pstmt.setString(4, msg);
	pstmt.setString(5, cw);
    pstmt.setString(6, bw);
	pstmt.setString(7, cpw);
    pstmt.setInt(8, tc);
	pstmt.setInt(9, pcn);
	pstmt.setInt(10, em);
	pstmt.setInt(11, qm);
	
	
	int i = pstmt.executeUpdate();
	
	if (i > 0) {
		flag = true;
		con.commit();
	} else
		flag = false;
} catch (SQLException e) {
	try {
		con.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
}

finally {
	closeConnection();
}
return flag;
}
	
	
	
	
	public boolean blacklistMail(MailDTO mail) throws ConnectionException,
	SQLException {
    boolean flag = false;
    try {
	con = AbstractDataAccessObject.getConnection();
	 String sid=mail.getSenderid();
	 String rid=mail.getReceiver();
	 String sub=mail.getSubject();
	 String msg=mail.getMessage();
	
			 
	pstmt = con.prepareStatement(SqlConstants._BLACKLIST_MAIL);
	pstmt.setString(1, sid);
	pstmt.setString(2, rid);
	pstmt.setString(3, sub);
	pstmt.setString(4, msg);
	
	
	
	int i = pstmt.executeUpdate();
	
	if (i > 0) {
		flag = true;
		con.commit();
	} else
		flag = false;
} catch (SQLException e) {
	try {
		con.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
}

finally {
	closeConnection();
}
return flag;
}
	
	
	public Vector<MailDTO> viewMail(int msgid, String mailbox)
			throws ConnectionException {
		Vector<MailDTO> vmail = new Vector<MailDTO>();
		System.out.println("msgidmsgidmsgid...."+msgid);
		System.out.println(mailbox);
		MailDTO mail = null;
		try {
			con = AbstractDataAccessObject.getConnection();
			if (mailbox.equals("outbox"))
				pstmt = con.prepareStatement(SqlConstants._VIEW_OUT_MAIL);
			else
				pstmt = con.prepareStatement(SqlConstants._VIEW_IN_MAIL);
			pstmt.setInt(1, msgid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mail = new MailDTO();
				mail.setMessageid(rs.getInt(1));
				mail.setFrommailid(rs.getString(2));
				mail.setTomailid(rs.getString(3));
				pstmt = con.prepareStatement(SqlConstants._VIEW_MAILID);
				pstmt.setString(1, rs.getString(3));
				rs1 = pstmt.executeQuery();
				if (rs1.next()) {
					mail.setEmploginid(rs1.getString(1));
				}
				mail.setMailsub(rs.getString(4));
				mail.setMailbody(rs.getString(5));
				System.out.println(rs.getString(5));
				mail.setMaildate(DateWrapper.parseDate(rs.getDate(6)));
				vmail.add(mail);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		} finally {
			closeConnection();
		}
		return vmail;
	}
	
	
	
	
	public Vector<MailDTO> viewMailFilter(int msgid)
	throws ConnectionException {
Vector<MailDTO> vmail = new Vector<MailDTO>();
System.out.println("msgidmsgidmsgid...."+msgid);

MailDTO mail = null;
try {
	con = AbstractDataAccessObject.getConnection();
	
		pstmt = con.prepareStatement(SqlConstants._VIEW_IN_MAIL_FILTER);
	pstmt.setInt(1, msgid);
	rs = pstmt.executeQuery();
	while (rs.next()) {
		mail = new MailDTO();
		mail.setMessageid(rs.getInt(1));
		System.out.println(rs.getInt(1));
		mail.setSenderid(rs.getString(2));
		System.out.println(rs.getString(2));
		mail.setReciverid(rs.getString(3));
		System.out.println(rs.getString(3));
		mail.setSubject(rs.getString(4));
		System.out.println(rs.getString(4));
		
		String msg=rs.getString(5);
		String filtermsg=msg.replaceAll("\\p{Punct}|\\d", "");
		System.out.println(filtermsg);
		mail.setMessage(filtermsg);
		System.out.println(rs.getString(5));
		mail.setMaildate(DateWrapper.parseDate(rs.getDate(6)));
		System.out.println(rs.getDate(6));
		
		
		vmail.add(mail);
	}
} catch (SQLException e) {
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
} finally {
	closeConnection();
}
return vmail;
}



	
	public Vector<MailDTO> viewFilterMail()
	throws ConnectionException {
    Vector<MailDTO> vmail = new Vector<MailDTO>();

    MailDTO mail = null;
   try {
	con = AbstractDataAccessObject.getConnection();
	
		pstmt = con.prepareStatement(SqlConstants._VIEW_FILTER_MAIL);
	
	
	rs = pstmt.executeQuery();
	while (rs.next()) {
		mail = new MailDTO();
		
		
		mail.setMessageid(rs.getInt(1));
		System.out.println(rs.getInt(1));
		mail.setSenderid(rs.getString(2));
		System.out.println(rs.getString(2));
		mail.setReciverid(rs.getString(3));
		System.out.println(rs.getString(3));
		mail.setSubject(rs.getString(4));
		System.out.println(rs.getString(4));
		mail.setMessage(rs.getString(5));
		System.out.println(rs.getString(5));
		
		
		
		mail.setCword(rs.getString(6));
		System.out.println(rs.getString(6));
		mail.setBword(rs.getString(7));
		System.out.println(rs.getString(7));
		mail.setCpword(rs.getString(8));
		System.out.println(rs.getString(8));
		
		mail.setTc(rs.getInt(9));
		System.out.println(rs.getInt(9));
		mail.setPc(rs.getInt(10));
		System.out.println(rs.getInt(10));
		mail.setEm(rs.getInt(11));
		System.out.println(rs.getInt(11));
		mail.setQm(rs.getInt(12));
		System.out.println(rs.getInt(12));
		
		mail.setMaildate(DateWrapper.parseDate(rs.getDate(13)));
		
		mail.setSendinfo(rs.getString(14));
		System.out.println(rs.getString(14));
		
		
			
		vmail.add(mail);
	}
} catch (SQLException e) {
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
} finally {
	closeConnection();
}
return vmail;
}
	
	
	
	public Vector<MailDTO> viewBlackListMail()
	throws ConnectionException {
    Vector<MailDTO> vmail = new Vector<MailDTO>();

    MailDTO mail = null;
   try {
	con = AbstractDataAccessObject.getConnection();
	
		pstmt = con.prepareStatement(SqlConstants._VIEW_BLACKLIST_MAIL);
	
	
	rs = pstmt.executeQuery();
	while (rs.next()) {
		mail = new MailDTO();
		
		
		mail.setMessageid(rs.getInt(1));
		System.out.println(rs.getInt(1));
		mail.setSenderid(rs.getString(2));
		System.out.println(rs.getString(2));
		mail.setReciverid(rs.getString(3));
		System.out.println(rs.getString(3));
		mail.setSubject(rs.getString(4));
		System.out.println(rs.getString(4));
		mail.setMessage(rs.getString(5));
		System.out.println(rs.getString(5));
		mail.setMaildate(DateWrapper.parseDate(rs.getDate(6)));
		mail.setSendinfo(rs.getString(7));
		System.out.println(rs.getString(7));
		
			
		vmail.add(mail);
	}
} catch (SQLException e) {
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
} finally {
	closeConnection();
}
return vmail;
}
	
	
	
	
	
	public Vector<MailDTO> filterViewMail(String empid,MailDTO mail)
	throws ConnectionException {
    Vector<MailDTO> fmail = new Vector<MailDTO>();
   
	System.out.println("emmmmmmmp"+empid);
   
   try {
	con = AbstractDataAccessObject.getConnection();
	
		pstmt = con.prepareStatement(SqlConstants._VIEW_IN_FILTER_MAILS);
		
		pstmt.setString(1, empid);
	rs = pstmt.executeQuery();
	while (rs.next()) {
		mail = new MailDTO();
		
		
		mail.setMessageid(rs.getInt(1));
		System.out.println(rs.getInt(1));
		mail.setSenderid(rs.getString(2));
		System.out.println(rs.getString(2));
		mail.setReciverid(rs.getString(3));
		System.out.println(rs.getString(3));
		mail.setSubject(rs.getString(4));
		System.out.println(rs.getString(4));
		mail.setMessage(rs.getString(5));
		System.out.println(rs.getString(5));
		mail.setMaildate(DateWrapper.parseDate(rs.getDate(6)));
		System.out.println(rs.getDate(6));
		
		
			
		fmail.add(mail);
	}
} catch (SQLException e) {
	System.out.println(e);
} catch (Exception e) {
	e.printStackTrace();
	System.out.println(e);
	flag = false;
	try {
		con.rollback();
	} catch (SQLException se) {
		se.printStackTrace();
		System.out.println(se);
	}
} finally {
	closeConnection();
}
return fmail;
}
	
	
	public boolean addWords(NotesDTO notesDTO) {

		try {
			con = AbstractDataAccessObject.getConnection();
			System.out.println(notesDTO.getWordName()+"=====bbbbbbbbbbbbb");
			PreparedStatement preparedStatement = con
					.prepareStatement("insert into words values(?)");
			preparedStatement.setString(1, notesDTO.getWordName());
			
			int inserti = preparedStatement.executeUpdate();
			if (inserti > 0)
				flag = true;
			else
				flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception raised" + e);
		}
		return flag;
	}
	
	
	
	
	
	
	
	
	public ArrayList<MailDTO> getAllMessageList(String user, String hint,
			String realpath) throws NullPointerException {
		ArrayList<MailDTO> flist = new ArrayList<MailDTO>();
		try {
			con = AbstractDataAccessObject.getConnection();
			
			pstmt = con.prepareStatement("select u.FROMMAILID,u.mailsub,u.MAILBODY,u.MAILDATE,p.potos from INBOX_MAILS u,home_potos p where u.FROMMAILID=p.user_id and u.TOMAILID like '"+ hint + "%' ORDER BY u.MAILDATE");
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String path = "";
				MailDTO mail = new MailDTO();
				mail.setFrommailid(rs.getString(1));
				mail.setSubject(rs.getString(2));
				mail.setMessage(rs.getString(3));
				mail.setMaildate(DateWrapper.parseDate(rs.getDate(4)));
				System.out.println("userid : " + rs.getString(1));
				System.out.println("username : " + rs.getString(2));
				
				try {
					Blob b = rs.getBlob(5);
					byte b1[] = b.getBytes(1, (int) b.length());
					path = realpath + "/" + rs.getString(1) + ".jpg";
					System.out.println("path  :" + path);
					OutputStream fout = new FileOutputStream(path);
					fout.write(b1);
					mail.setPhoto(path);
					flist.add(mail);
				} catch (Exception e) {
					mail.setPhoto(realpath);
					flist.add(mail);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();

		} finally {
			try {
				con.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flist;

	}

	public boolean deleteMailIDs(int contactid) throws ConnectionException {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("delete user_contact where contactid=?");
			preparedStatement.setInt(1, contactid);
			int inserti = preparedStatement.executeUpdate();
			if (inserti > 0)
				flag = true;
			else
				flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception raised" + e);
		}
		return flag;
	}
	
	
	
	public boolean deleteMail(int mailid) throws ConnectionException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete BLACKLISTMESSAGE where BLACKLISTMAILID=?");
			preparedStatement.setInt(1, mailid);
			int inserti = preparedStatement.executeUpdate();
			if (inserti > 0)
				flag = true;
			else
				flag = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Exception raised" + e);
		}
		return flag;
	}

	public boolean insestAttachment(MailDTO mail) throws ConnectionException,
			FileNotFoundException {
		boolean flag = false;
		String filepath = mail.getFilepath();
		String fileType = "";
		String fullpath = filepath;
		String[] extension = fullpath.split("\\.");
		int i;
		for (i = 0; i < extension.length; i++) {
			System.out.println(extension[i]);
			fileType = extension[i];
		}

		File file = new File(mail.getFilepath());
		File file1 = new File(mail.getFilepath());
		FileInputStream fisfile = new FileInputStream(file);
		FileInputStream fisfile1 = new FileInputStream(file1);
		try {
			con = AbstractDataAccessObject.getConnection();
			pstmt = con.prepareStatement(SqlConstants._INBOX_ATTACHMENT_FILE);
			pstmt1 = con.prepareStatement(SqlConstants._OUTBOX_ATTACHMENT_FILE);

			pstmt.setBinaryStream(1, fisfile, (int) file.length());
			pstmt.setString(2, fileType);
			pstmt1.setBinaryStream(1, fisfile1, (int) file1.length());
			pstmt1.setString(2, fileType);

			int j = pstmt.executeUpdate();
			int l = pstmt1.executeUpdate();
			if (j > 0 && l > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			throw new ConnectionException(
					"Some Technical prablum Occering please try later");
		} catch (Exception e) {
			throw new ConnectionException(
					"Some Technical prablum Occering please try later");
		} finally {
			closeConnection();
		}
		return flag;
	}

	public Vector<MailDTO> viewMailAttachment(int msgid, String mailbox,
			String realpath) throws ConnectionException {
		Vector<MailDTO> vmail = new Vector<MailDTO>();

		MailDTO mail = null;
		String path = "";
		try {
			con = AbstractDataAccessObject.getConnection();
			if (mailbox.equals("outbox"))
				pstmt = con
						.prepareStatement("select * from OUTBOX_MAIL_ATTACHMENT where ATTACHMENTMAILID=?");
			else
				pstmt = con
						.prepareStatement("select * from INBOX_MAIL_ATTACHMENT where ATTACHMENTMAILID=?");
			pstmt.setInt(1, msgid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				path = "";
				path = realpath;
				mail = new MailDTO();
				mail.setMessageid(rs.getInt(1));
				mail.setAttachmentmailid(rs.getInt(2));
				Blob b = rs.getBlob(3);
				byte b1[] = b.getBytes(1, (int) b.length());
				path = path + "/" + rs.getInt(1) + "." + rs.getString(4);
				OutputStream fout = new FileOutputStream(path);
				fout.write(b1);
				System.out.println(path);
				mail.setAttachmentfile(path);
				vmail.add(mail);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			flag = false;
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println(se);
			}
		} finally {
			closeConnection();
		}
		return vmail;
	}
}
