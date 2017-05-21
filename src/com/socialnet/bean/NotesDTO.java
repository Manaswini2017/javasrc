package com.socialnet.bean;

public class NotesDTO {
	private int folderid;
	private int eventid;
	private String eventDesc;
	private String eventName;
	private String eventdate;
	private String addFolder;
	private int userid;
	private String folderName;
	private String folderCreationDate;
	private String removeSubfolder;
	private String user;
	private String groupName;
	private String groupDescription;

	private String contactemail;
	private String contactname;
	private String contactphno;
	private String wordName;
	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	private int contactid;

	public String getContactemail() {
		return contactemail;
	}

	public int getContactid() {
		return contactid;
	}

	public void setContactid(int contactid) {
		this.contactid = contactid;
	}

	public void setContactemail(String contactemail) {
		this.contactemail = contactemail;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactphno() {
		return contactphno;
	}

	public void setContactphno(String contactphno) {
		this.contactphno = contactphno;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	private int groupid;
	private String groupStatus;

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getEventid() {
		return eventid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAddFolder() {
		return addFolder;
	}

	public void setAddFolder(String addFolder) {
		this.addFolder = addFolder;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRemoveSubfolder() {
		return removeSubfolder;
	}

	public void setRemoveSubfolder(String removeSubfolder) {
		this.removeSubfolder = removeSubfolder;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getRename() {
		return rename;
	}

	public void setRename(String rename) {
		this.rename = rename;
	}

	private String oldName;
	private String rename;

	public int getFolderid() {
		return folderid;
	}

	public void setFolderid(int folderid) {
		this.folderid = folderid;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderCreationDate() {
		return folderCreationDate;
	}

	public void setFolderCreationDate(String folderCreationDate) {
		this.folderCreationDate = folderCreationDate;
	}
}
