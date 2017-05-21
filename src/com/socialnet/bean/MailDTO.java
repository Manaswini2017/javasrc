package com.socialnet.bean;

import javax.mail.Address;

public class MailDTO {

	private String subject;
	private Address from;
	private Object content;
	private String receiver;
	
	private String attachmentfile;
	private String mailid;
	private String empid;
	private String mailsub;
	private String mailbody;
	private String frommailid;
	private int messageid;
	private String tomailid;
	private int attachmentmailid;
	private String maildate;
	private String loginid;
	private String emploginid;
	private String requesttype;
	private String mailbox;
	private String filepath;
	private int attachcount;
	
	private String senderid;
	private String reciverid;
	private String message;
	private String sendinfo;
	private String photo;
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSendinfo() {
		return sendinfo;
	}

	public void setSendinfo(String sendinfo) {
		this.sendinfo = sendinfo;
	}

	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getReciverid() {
		return reciverid;
	}

	public void setReciverid(String reciverid) {
		this.reciverid = reciverid;
	}

	public String getCword() {
		return cword;
	}

	public void setCword(String cword) {
		this.cword = cword;
	}

	public String getBword() {
		return bword;
	}

	public void setBword(String bword) {
		this.bword = bword;
	}

	public String getCpword() {
		return cpword;
	}

	public void setCpword(String cpword) {
		this.cpword = cpword;
	}

	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getEm() {
		return em;
	}

	public void setEm(int em) {
		this.em = em;
	}

	public int getQm() {
		return qm;
	}

	public void setQm(int qm) {
		this.qm = qm;
	}

	private String cword;
	private String bword;
	private String cpword;
	private int tc;
	private int pc;
	private int em;
	private int qm;

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getEmpid() {
		return empid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Address getFrom() {
		return from;
	}

	public void setFrom(Address from) {
		this.from = from;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getAttachmentfile() {
		return attachmentfile;
	}

	public void setAttachmentfile(String attachmentfile) {
		this.attachmentfile = attachmentfile;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getMailsub() {
		return mailsub;
	}

	public void setMailsub(String mailsub) {
		this.mailsub = mailsub;
	}

	public String getMailbody() {
		return mailbody;
	}

	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}

	

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	

	public String getFrommailid() {
		return frommailid;
	}

	public void setFrommailid(String frommailid) {
		this.frommailid = frommailid;
	}

	public String getTomailid() {
		return tomailid;
	}

	public void setTomailid(String tomailid) {
		this.tomailid = tomailid;
	}

	public String getMaildate() {
		return maildate;
	}

	public void setMaildate(String maildate) {
		this.maildate = maildate;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getEmploginid() {
		return emploginid;
	}

	public void setEmploginid(String emploginid) {
		this.emploginid = emploginid;
	}

	public String getRequesttype() {
		return requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getAttachcount() {
		return attachcount;
	}

	public void setAttachcount(int attachcount) {
		this.attachcount = attachcount;
	}

	public int getAttachmentmailid() {
		return attachmentmailid;
	}

	public void setAttachmentmailid(int attachmentmailid) {
		this.attachmentmailid = attachmentmailid;
	}

}
