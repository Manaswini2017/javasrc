package com.socialnet.dao;

import java.io.*;
import java.util.ArrayList;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Types;
import com.socialnet.bean.UserBean;
import com.socialnet.conections.*;
import com.socialnet.db.*;
import com.socialnet.util.DateWrapper;
import com.sun.org.apache.regexp.internal.RE;

public class UserDAO extends AbstractDataAccessObject {

	Connection con = null;
	PreparedStatement ps = null;
	Statement st = null;
	CallableStatement cstmt = null;
	ResultSet rs = null;

	public UserDAO() {
		con = getConnection();
		try {
			con.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println(e);

		}
	}

	public UserBean getUserContacts(String uname) {
		UserBean ubean = new UserBean();

		try {
			cstmt = con
					.prepareCall("{call GetUserContacts(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt.setString(1, uname);

			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.registerOutParameter(9, Types.VARCHAR);
			cstmt.registerOutParameter(10, Types.VARCHAR);
			cstmt.registerOutParameter(11, Types.VARCHAR);
			cstmt.registerOutParameter(12, Types.VARCHAR);
			cstmt.registerOutParameter(13, Types.DATE);

			cstmt.execute();

			ubean.setFirstname(cstmt.getString(2));
			ubean.setMidlename(cstmt.getString(3));
			ubean.setLastname(cstmt.getString(4));
			ubean.setMobile(cstmt.getString(5));
			ubean.setCity(cstmt.getString(6));
			ubean.setState(cstmt.getString(7));
			ubean.setCountry(cstmt.getString(8));
			ubean.setPin(cstmt.getString(9));
			ubean.setMail(cstmt.getString(10));
			ubean.setSex(cstmt.getString(11));
			ubean.setVillage(cstmt.getString(12));
			ubean.setDob(cstmt.getString(13));

		} catch (Exception e) {
			System.out
					.println("............exception rised in getusecontacts fun:");
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(ubean);
		return ubean;
	}

	public boolean updateUserContacts(UserBean ubean) {
		String f = "";
		boolean flag = false;

		try {
			cstmt = con
					.prepareCall("{call UpdateUserContacts(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, ubean.getUserid());

			cstmt.setString(2, ubean.getFirstname());
			cstmt.setString(3, ubean.getMidlename());
			cstmt.setString(4, ubean.getLastname());
			cstmt.setString(5, ubean.getMobile());
			cstmt.setString(6, ubean.getCity());
			cstmt.setString(7, ubean.getState());
			cstmt.setString(8, ubean.getCountry());
			cstmt.setString(9, ubean.getPin());
			cstmt.setString(10, ubean.getMail());
			cstmt.setString(11, ubean.getSex());
			cstmt.setString(12, ubean.getVillage());
			cstmt.setString(13, DateWrapper.parseDate(ubean.getDob()));
			cstmt.registerOutParameter(14, Types.VARCHAR);

			cstmt.execute();
			f = cstmt.getString(14);
			if (f.equalsIgnoreCase("true")) {
				flag = true;
				con.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public UserBean getUserPersonal(String user) {
		UserBean ubean = new UserBean();
		try {

			/*
			 * cstmt=con.prepareCall("{call
			 * GetUserPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			 * 
			 * cstmt.setString(1, user);
			 * 
			 * cstmt.registerOutParameter(2,Types.VARCHAR);
			 * cstmt.registerOutParameter(3,Types.INTEGER);
			 * cstmt.registerOutParameter(4,Types.VARCHAR);
			 * cstmt.registerOutParameter(5,Types.VARCHAR);
			 * cstmt.registerOutParameter(6, Types.VARCHAR);
			 * cstmt.registerOutParameter(7, Types.VARCHAR);
			 * cstmt.registerOutParameter(8,Types.VARCHAR);
			 * cstmt.registerOutParameter(9, Types.VARCHAR);
			 * cstmt.registerOutParameter(10, Types.VARCHAR);
			 * cstmt.registerOutParameter(11,Types.VARCHAR);
			 * cstmt.registerOutParameter(12,Types.VARCHAR);
			 * cstmt.registerOutParameter(13,Types.VARCHAR);
			 * 
			 * 
			 * cstmt.execute(); System.out.println(cstmt);
			 */
			ps = con
					.prepareStatement(" select nvl(user_fname,''),nvl(user_height,''),nvl(user_color,''),nvl(user_build,''),nvl(user_looks,''),nvl(user_bestlook,''),nvl(user_aboutme,''),"
							+ "  nvl(user_hobbies,''), nvl(user_habbits,''),nvl(user_i_hate,''),"
							+ "  nvl(user_i_like,''),nvl(user_purpose,'')   from user_contacts natural join user_personal where user_id=?");
			ps.setString(1, user);

			rs = ps.executeQuery();
			while (rs.next()) {
				ubean.setFirstname(rs.getString(1));
				ubean.setHight(rs.getInt(2));
				ubean.setColor(rs.getString(3));
				ubean.setBuild(rs.getString(4));
				ubean.setLooks(rs.getString(5));
				ubean.setBestlook(rs.getString(6));
				ubean.setAboutme(rs.getString(7));
				ubean.setHobbies(rs.getString(8));
				ubean.setHabbits(rs.getString(9));
				ubean.setIhate(rs.getString(10));
				ubean.setIlike(rs.getString(11));
				ubean.setPurpose(rs.getString(12));

			}

		} catch (SQLException e) {
			System.out.println(".............exceotiib rised in get personal:");
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ubean;

	}

	public boolean updateUserPersonal(UserBean ubean) {
		boolean flag = false;
		String f = "";
		try {
			cstmt = con
					.prepareCall("{call UpdateUserPersonal(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt.setString(1, ubean.getUserid());

			cstmt.setString(2, ubean.getFirstname());
			cstmt.setInt(3, ubean.getHight());
			cstmt.setString(4, ubean.getColor());
			cstmt.setString(5, ubean.getBuild());
			cstmt.setString(6, ubean.getLooks());
			cstmt.setString(7, ubean.getBestlook());
			cstmt.setString(8, ubean.getAboutme());
			System.out.println(ubean.getAboutme());
			cstmt.setString(9, ubean.getHobbies());
			cstmt.setString(10, ubean.getHabbits());
			cstmt.setString(11, ubean.getIhate());
			cstmt.setString(12, ubean.getIlike());
			cstmt.setString(13, ubean.getPurpose());
			cstmt.registerOutParameter(14, Types.VARCHAR);

			cstmt.execute();
			f = cstmt.getString(14);

			if (f.equalsIgnoreCase("true")) {
				flag = true;
				con.commit();
			} else
				con.rollback();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public UserBean getUserProfessional(String user) {
		UserBean ubean = new UserBean();
		try {

			ps = con
					.prepareStatement("select * from user_professional where user_id=? ");
			ps.setString(1, user);
			rs = ps.executeQuery();

			while (rs.next()) {
				ubean.setUserid(rs.getString(1));
				ubean.setQualification(rs.getString(2));
				ubean.setSchool(rs.getString(3));
				ubean.setLoc(rs.getString(4));
				ubean.setUniversity(rs.getString(5));
				ubean.setOccupation(rs.getString(6));
				ubean.setSkills(rs.getString(7));
				ubean.setInterests(rs.getString(8));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return ubean;
	}

	public boolean updateUserProfessional(UserBean ubean) {
		boolean flag = false;

		try {

			ps = con
					.prepareStatement("update  USER_PROFESSIONAL set user_qualification=?,user_school=?,user_loc=?,user_university=?,"
							+ "user_occupation=?,user_skills=?,user_interests=? where user_id=?");
			ps.setString(1, ubean.getQualification());
			ps.setString(2, ubean.getSchool());
			ps.setString(3, ubean.getLoc());
			ps.setString(4, ubean.getUniversity());
			ps.setString(5, ubean.getOccupation());
			ps.setString(6, ubean.getSkills());
			ps.setString(7, ubean.getInterests());
			ps.setString(8, ubean.getUserid());
			System.out.println(ubean.getUserid());
			int count = ps.executeUpdate();
			System.out.println(count);
			if (count > 0) {

				flag = true;
				con.commit();

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		return flag;
	}

	public UserBean getSocialDetails(String user) {
		UserBean ubean = new UserBean();
		try {
			ps = con
					.prepareStatement("select *from USER_SOCIAL where user_id=?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			while (rs.next()) {
				ubean.setUserid(rs.getString(1));
				ubean.setMaritual_status(rs.getString(2));
				ubean.setRelegion(rs.getString(3));
				ubean.setHumour(rs.getString(4));
				ubean.setSexuality(rs.getString(5));
				ubean.setSmoking(rs.getString(6));
				ubean.setDrinking(rs.getString(7));
				ubean.setPets(rs.getString(8));
				ubean.setLiving(rs.getString(9));
				ubean.setNativity(rs.getString(10));
				ubean.setMovies(rs.getString(11));
				ubean.setBooks(rs.getString(12));
				ubean.setWebsites(rs.getString(13));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ubean;
	}

	public boolean updateUserSocialDetails(UserBean ubean) {
		String f = "";

		boolean flag = false;
		try {
			cstmt = con
					.prepareCall("{call UpdateSocialDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt.setString(1, ubean.getUserid());

			cstmt.setString(2, ubean.getMaritual_status());
			cstmt.setString(3, ubean.getRelegion());
			cstmt.setString(4, ubean.getHumour());
			cstmt.setString(5, ubean.getSexuality());
			cstmt.setString(6, ubean.getSmoking());
			cstmt.setString(7, ubean.getDrinking());
			cstmt.setString(8, ubean.getPets());
			cstmt.setString(9, ubean.getLiving());
			cstmt.setString(10, ubean.getNativity());
			cstmt.setString(11, ubean.getMovies());
			cstmt.setString(12, ubean.getBooks());
			cstmt.setString(13, ubean.getWebsites());
			cstmt.registerOutParameter(14, Types.VARCHAR);

			cstmt.execute();
			f = cstmt.getString(14);
			if (f.equalsIgnoreCase("true")) {
				flag = true;
				con.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public boolean addServiceScore(String user, String score) {
		String f = "";
		boolean flag = false;
		try {
			System.out.println(user);
			System.out.println(score);
			if (score.equalsIgnoreCase("plus")) {
				ps = con
						.prepareStatement("update user_score set score=(select (select score from user_score where name=?)+1 from dual) where name=?");
			} else {
				ps = con
						.prepareStatement("update user_score set score=(select (select score from user_score where name=?)-1 from dual) where name=?");
			}
			ps.setString(1, user);
			ps.setString(2, user);
			int i = ps.executeUpdate();
			if (i > 0) {
				flag = true;
				con.commit();
			} else {
				flag = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public int viewServiceScore(String user) {
		String f = "";
		boolean flag = false;
		int score = 0;
		try {
			ps = con.prepareStatement("select * from user_score where name=?");
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				score = rs.getInt(2);
				System.out.println(score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return score;
	}

	public ArrayList<UserBean> getBirthdays(String user) {

		ArrayList<UserBean> alist = new ArrayList<UserBean>();
		try {
			int i = 0;
			ps = con
					.prepareStatement("select friend_id,user_mname,user_dob from user_friends natural join user_contacts where user_id=? and "
							+ "to_date(to_char(user_dob,'dd-mon'),'dd-mon') between to_date(to_char(sysdate,'dd-mon'),'dd-mon') and to_date(to_char(sysdate+5,'dd-mon'),'dd-mon')");
			ps.setString(1, user);
			rs = ps.executeQuery();
			while (rs.next()) {
				i++;
				UserBean ubean = new UserBean();
				ubean.setUserid(rs.getString(1));
				ubean.setMidlename(rs.getString(2));
				ubean.setDob(DateWrapper.parseDate(rs.getDate(3)));
				alist.add(ubean);
				if (i > 3)
					return alist;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return alist;

	}

	public ArrayList<UserBean> getRequestedFriends(String user) {
		ArrayList<UserBean> alist = new ArrayList<UserBean>();
		try {
			ps = con
					.prepareStatement("select friend_id,DO_REQST,nvl(msg,'hai') from user_friends_wait where user_id=?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean ubean = new UserBean();
				ubean.setUserid(rs.getString(1));
				ubean.setDob(rs.getDate(2).toString());
				ubean.setSdesc(rs.getString(3));
				alist.add(ubean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		return alist;
	}

	public boolean addFriend(String user, String requser) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("insert into user_friends values(?,?,sysdate)");
			ps.setString(1, user);
			ps.setString(2, requser);
			int count = ps.executeUpdate();
			if (count > 0) {
				ps = con
						.prepareStatement("delete from USER_FRIENDS_WAIT where friend_id=? and user_id=?");

				ps.setString(1, requser);
				ps.setString(2, user);
				int c = ps.executeUpdate();
				if (c > 0) {
					flag = true;
					con.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public boolean rejectFriend(String user, String ruser) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("delete from user_friends_wait where user_id=? and friend_id=?");
			ps.setString(1, user);
			ps.setString(2, ruser);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public ArrayList<UserBean> getSawals() {
		ArrayList<UserBean> slist = new ArrayList<UserBean>();
		try {
			ps = con.prepareStatement("select *from user_sawals");
			rs = ps.executeQuery();

			while (rs.next()) {
				UserBean sbean = new UserBean();
				sbean.setUserid(rs.getString(1));
				sbean.setSid(rs.getInt(2));
				sbean.setSbody(rs.getString(3));
				sbean.setSdop(rs.getDate(4));
				sbean.setSdesc(rs.getString(5));
				sbean.setSdoex(rs.getDate(6));
				slist.add(sbean);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return slist;
	}

	public boolean postSawalComment(UserBean sbean) {

		boolean flag = false;

		try {
			ps = con
					.prepareStatement("insert into SAWAL_COMENTS values(?,?,?,sysdate)");
			ps.setInt(1, sbean.getSid());
			ps.setString(2, sbean.getScmnt());
			ps.setString(3, sbean.getSender());
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public ArrayList<UserBean> getSawalComments(int sid) {
		ArrayList<UserBean> clist = new ArrayList<UserBean>();
		try {
			ps = con
					.prepareStatement("select SAWAL_COMT,CMT_SENDER,CMT_DOS from SAWAL_COMENTS where sawal_id=?");
			ps.setInt(1, sid);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean sbean = new UserBean();
				sbean.setScmnt(rs.getString(1));
				System.out.println(rs.getString(1));
				sbean.setSender(rs.getString(2));
				sbean.setDob(rs.getDate(3).toString());
				clist.add(sbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clist;
	}

	public boolean postNewSawal(String user, String sawal, String doexp,
			String head) {
		boolean flag = false;

		try {
			ps = con
					.prepareStatement("insert into  user_sawals values(?,(select nvl(max(user_sawalid),100)+1 from user_sawals),?,sysdate,?,to_date(?))");
			ps.setString(1, user);
			ps.setString(2, sawal);
			ps.setString(3, head);
			ps.setString(4, doexp);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;

	}

	public boolean deleteScrap(String name, String uid, int sid) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("delete from user_scraps where user_id=? and user_scrapid=? and user_sender=?");
			ps.setString(1, name);
			ps.setInt(2, sid);
			ps.setString(3, uid);
			int count = ps.executeUpdate();
			if (count > 0) {

				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public String getFriendName(String user) {
		String friend = null;
		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select friend_id from user_friends where user_id='"
							+ user + "'");
			if (rs.next())
				friend = rs.getString(1);
		} catch (Exception e) {

		}
		return friend;
	}

	public boolean insertScrap(String sender, String recipiant, String scrap) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement(" select friend_id from user_friends where user_id=? and friend_id=?");
			ps.setString(1, recipiant);
			ps.setString(2, sender);
			rs = ps.executeQuery();
			if (rs.next()) {
				ps = con
						.prepareStatement("insert into user_scraps values(?,(select nvl(max(user_scrapid),100)+1 from user_scraps),?,?,?,sysdate)");
				ps.setString(1, recipiant);
				ps.setString(2, sender);
				System.out.println("sender:" + sender);
				ps.setString(3, recipiant);
				ps.setString(4, scrap);
				int count = ps.executeUpdate();
				if (count > 0) {
					flag = true;
					con.commit();
					return flag;
				}
			} else {
				ps = con
						.prepareStatement("select user_scraps from user_permisions where user_id=?");
				ps.setString(1, recipiant);
				rs = ps.executeQuery();
				if (rs.next()) {
					if (rs.getString(1).equalsIgnoreCase("public")) {
						ps = con
								.prepareStatement("insert into user_scraps values(?,(select nvl(max(user_scrapid),100)+1 from user_scraps),?,?,?,sysdate)");
						ps.setString(1, recipiant);
						ps.setString(2, sender);
						System.out.println("sender:" + sender);
						ps.setString(3, recipiant);
						ps.setString(4, scrap);
						int count = ps.executeUpdate();
						if (count > 0) {
							flag = true;
							con.commit();
							return flag;
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;

	}

	public ArrayList<UserBean> getScraps(String userid) {
		ArrayList<UserBean> slist = new ArrayList<UserBean>();
		try {
			ps = con
					.prepareStatement("select *from user_scraps where user_id=?");
			ps.setString(1, userid);
			rs = ps.executeQuery();

			while (rs.next()) {
				UserBean ubean = new UserBean();

				ubean.setUserid(rs.getString(1));
				ubean.setSid(rs.getInt(2));
				ubean.setSender(rs.getString(3));
				ubean.setReciever(rs.getString(4));
				ubean.setScrap(rs.getString(5));
				ubean.setSdop(rs.getDate(6));
				slist.add(ubean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return slist;
	}

	public int getScrapCount(String userid) {
		try {
			ps = con
					.prepareStatement("select count(user_scrapid) from user_scraps where user_id=?");
			ps.setString(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public ArrayList<UserBean> getPolls() {
		ArrayList<UserBean> plist = new ArrayList<UserBean>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("select *from user_polls");
			while (rs.next()) {
				UserBean ub = new UserBean();
				ub.setUserid(rs.getString(1));
				ub.setSid(rs.getInt(2));
				ub.setPoll(rs.getString(3));
				ub.setPoptn1(rs.getString(4));
				ub.setPoptn2(rs.getString(5));

				ub.setSdop(rs.getDate(6));
				ub.setSdoex(rs.getDate(7));
				plist.add(ub);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return plist;
	}

	public ArrayList<UserBean> getPollComments(int pid) {
		ArrayList<UserBean> plist = new ArrayList<UserBean>();
		try {
			ps = con
					.prepareStatement("select poll_id,nvl(poll_coment,'no coment'),poll_dop,poll_optn,comt_sender from poll_coments where poll_id=?");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean ub = new UserBean();
				ub.setSid(rs.getInt(1));
				ub.setScmnt(rs.getString(2));
				ub.setSdop(rs.getDate(3));
				ub.setPoptn1(rs.getString(4));
				ub.setSender(rs.getString(5));
				plist.add(ub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return plist;
	}

	public UserBean getPollById(int pid) {
		UserBean ub = new UserBean();
		;
		try {
			ps = con
					.prepareStatement("select *from user_polls where user_pollid=?");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ub.setUserid(rs.getString(1));
				ub.setSid(rs.getInt(2));
				ub.setPoll(rs.getString(3));
				ub.setPoptn1(rs.getString(4));
				ub.setPoptn2(rs.getString(5));
				ub.setSdop(rs.getDate(6));
				ub.setSdoex(rs.getDate(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ub;
	}

	public boolean postPollComent(int pid, String sender, String pcmnt,
			String poptn) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("insert into poll_coments values(?,?,sysdate,?,?)");
			ps.setInt(1, pid);
			ps.setString(2, pcmnt);
			ps.setString(3, poptn);
			ps.setString(4, sender);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {

		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean postNewPoll(UserBean bean) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("insert into  user_polls values(?,(select nvl(max(user_pollid),100)+1 from user_polls),?,?,?,sysdate,to_date(?))");
			ps.setString(1, bean.getSender());
			ps.setString(2, bean.getPoll());
			ps.setString(3, bean.getPoptn1());
			ps.setString(4, bean.getPoptn2());
			ps.setString(5, bean.getDob());
			ps.executeUpdate();
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;

	}

	public boolean checkUserId(String username) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("select login_id from login_details where login_id=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				String user = rs.getString(1);
				if (user.equals(username))
					flag = true;

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;

	}

	public boolean requestFriend(String userid, String ruser, String msg) {
		boolean flag = false;
		try {
			System.out.println(userid);
			System.out.println(ruser);
			System.out.println(msg);
			ps = con
					.prepareStatement("insert into  USER_FRIENDS_WAIT values(?,?,sysdate,?,?)");
			ps.setString(1, ruser);
			ps.setString(2, userid);
			ps.setString(3, "notaccept");
			ps.setString(4, msg);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flag;

	}

	public ArrayList<UserBean> getFriendsList(String user) {
		ArrayList<UserBean> flist = new ArrayList<UserBean>();
		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select user_id,friend_id,to_char(do_accept),user_mname,user_village from user_friends natural join user_contacts where user_id="
							+ "'" + user + "'");
			while (rs.next()) {
				UserBean ub = new UserBean();
				ub.setUserid(rs.getString(1));
				ub.setFriendid(rs.getString(2));
				ub.setDoj(rs.getString(3));
				ub.setMidlename(rs.getString(4));
				ub.setVillage(rs.getString(5));
				flist.add(ub);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flist;

	}

	public ArrayList<UserBean> getAllUsersList(String user, String hint,
			String realpath) throws NullPointerException {
		ArrayList<UserBean> flist = new ArrayList<UserBean>();
		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select u.user_id,u.user_mname,u.user_village,p.potos from user_contacts u,home_potos p where u.user_id=p.user_id and u.user_id like '"+ hint + "%'");
			while (rs.next()) {
				String path = "";
				UserBean ub = new UserBean();
				ub.setUserid(rs.getString(1));
				ub.setMidlename(rs.getString(2));
				System.out.println("userid : " + rs.getString(1));
				System.out.println("username : " + rs.getString(2));
				ub.setVillage(rs.getString(3));
				System.out.println(rs.getBlob(4));
				try {
					Blob b = rs.getBlob(4);
					byte b1[] = b.getBytes(1, (int) b.length());
					path = realpath + "/" + rs.getString(1) + ".jpg";
					System.out.println("path  :" + path);
					OutputStream fout = new FileOutputStream(path);
					fout.write(b1);
					ub.setPhoto(path);
					flist.add(ub);
				} catch (Exception e) {
					ub.setPhoto(realpath);
					flist.add(ub);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();

		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flist;

	}

	public boolean changePassword(String user, String newpwd) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("update login_details set login_pwd=? where login_id=?");
			ps.setString(1, newpwd);
			ps.setString(2, user);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public boolean changePermissions(String user, String scraps, String photos) {
		boolean flag = false;
		try {
			System.out.println(user);
			System.out.println(scraps);
			System.out.println(photos);
			ps = con
					.prepareStatement("update USER_PERMISIONS set USER_SCRAPS=?,USER_POTOS=? where user_id=?");
			ps.setString(1, scraps);
			ps.setString(2, photos);
			ps.setString(3, user);
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean insertPhoto(String path, String user) {
		boolean flag = false;
		int count = 0;
		try {
			System.out.println(path);
			con = getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("update home_potos set potos=? where user_id=?");

			System.out.println("File=" + path);
			File f = new File(path);
			System.out.println("path:" + path);
			System.out.println("fole:" + f.length());

			pstmt.setBinaryStream(1, new FileInputStream(f), (int) f.length());
			pstmt.setString(2, user);
			int i = pstmt.executeUpdate();
			if (i != 0) {
				flag = true;
				con.commit();
			} else
				flag = false;
		} catch (Exception e) {

			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return flag;
	}

	public String getPhoto(String user, String realpath) {
		String path = "";
		try {
			con = getConnection();
			System.out.println("ur photo :" + user);
			PreparedStatement ps = con
					.prepareStatement("select poto_id,potos from home_potos where user_id=?");
			// cstmt = con.prepareCall("{call GetPhoto(?,?,?,?)}");
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();

			System.out.println("user :" + user);
			if (rs.next()) {
				int pid = rs.getInt(1);
				Blob b = rs.getBlob(2);
				byte b1[] = null;
				try {
					b1 = b.getBytes(1, (int) b.length());
					path = realpath + "\\" + rs.getInt(1) + ".jpg";
					System.out.println(path);
					OutputStream fout = new FileOutputStream(path);
					fout.write(b1);
				} catch (Exception e) {
					path = "";
					path = realpath + "\\example.jpeg";
					System.out.println(path);
					// OutputStream fout = new FileOutputStream(path);
					// fout.write(b1);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public ArrayList getUserPhotos(String user, String path) {
		ArrayList<UserBean> alist = new ArrayList<UserBean>();
		String realpath = "";
		try {
			System.out.println("hai");
			ps = con
					.prepareStatement("select * from user_photos where useridref=?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			while (rs.next()) {
				realpath = "";
				UserBean bean = new UserBean();
				bean.setPotoid(rs.getInt(1));
				System.out.println(rs.getInt(1));
				Blob b = rs.getBlob(3);
				System.out.println("in userDao:blob:" + b);
				realpath = path + "/" + rs.getInt(1) + "." + rs.getString(4);
				byte b1[] = b.getBytes(1, (int) b.length());
				OutputStream fout = new FileOutputStream(realpath);
				fout.write(b1);
				bean.setPhoto(realpath);
				alist.add(bean);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();

		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return alist;
	}

	public boolean deletePick(String user, int pid) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("delete from user_photos where photoidref=?");
			ps.setInt(1, pid);
			int c = ps.executeUpdate();
			if (c > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				rs.close();
				ps.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return flag;
	}

	public void deleteAction() {
		try {
			st = con.createStatement();
			st.executeUpdate("delete from user_sawals where USER_SAWAL_DOEXP=sysdate");
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean recoveryPass(String user, String qust, String ans) {
		boolean flag = false;
		try {
			ps = con
					.prepareStatement("select user_pwd from user_contacts where user_id=? and USER_QUESTN=? and USER_SECANS=?");
			ps.setString(1, user);
			ps.setString(2, qust);
			ps.setString(3, ans);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getPermissions(String user) {
		String mode = "";
		try {
			ps = con
					.prepareStatement("select user_potos from user_permisions where user_id=?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			if (rs.next()) {
				mode = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mode;
	}

	public boolean insertPicture(String path, String user)
			throws FileNotFoundException, SQLException {
		boolean flag = false;
		String fileType = "";
		String fullpath = path;
		String[] extension = fullpath.split("\\.");
		int i;
		for (i = 0; i < extension.length; i++) {
			System.out.println(extension[i]);
			fileType = extension[i];
		}

		System.out.println(path);
		System.out.println(fileType);
		System.out.println(user);
		File file = new File(path);
		FileInputStream fisfile = new FileInputStream(file);
		try {
			con = getConnection();
			ps = con
					.prepareStatement("insert into USER_PHOTOS values((select nvl(max(photoidref),100)+1 from USER_PHOTOS),?,?,?)");
			ps.setString(1, user);
			ps.setBinaryStream(2, fisfile, (int) file.length());
			ps.setString(3, fileType);
			int j = ps.executeUpdate();
			if (j > 0) {
				flag = true;
			} else
				flag = false;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

}