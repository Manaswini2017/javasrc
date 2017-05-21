package com.socialnet.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.SQLException;

import com.socialnet.bean.AdminBean;
import com.socialnet.bean.UserBean;
import com.socialnet.conections.*;
import com.socialnet.db.*;
import com.socialnet.util.DateWrapper;

public class MasterDao extends AbstractDataAccessObject {
	public MasterDao() {
		con = getConnection();
	}

	Connection con = null;
	PreparedStatement ps = null, ps1 = null;
	Statement st = null;
	CallableStatement cstmt = null;
	ResultSet rs = null, rs1 = null;

	public boolean registerUser(UserBean ubean) {
		String f = "";
		boolean flag = false;
		try {
			
			cstmt = con
					.prepareCall("{call Usercontactsinsert(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, ubean.getUserid());
			System.out.println("userid:" + ubean.getUserid());
			cstmt.setString(2, ubean.getUserpwd());
			System.out.println("userid:" + ubean.getUserpwd());
			cstmt.setString(3, ubean.getFirstname());
			System.out.println("userid:" + ubean.getFirstname());
			cstmt.setString(4, ubean.getMidlename());
			System.out.println("userid:" + ubean.getMidlename());
			cstmt.setString(5, ubean.getLastname());
			System.out.println("userid:" + ubean.getLastname());
			cstmt.setString(6, ubean.getMobile());
			System.out.println("userid:" + ubean.getMobile());
			cstmt.setString(7, ubean.getCity());
			System.out.println("userid:" + ubean.getCity());
			cstmt.setString(8, ubean.getState());
			System.out.println("userid:" + ubean.getState());
			cstmt.setString(9, ubean.getCountry());
			System.out.println("userid:" + ubean.getCountry());
			cstmt.setString(10, ubean.getPin());
			System.out.println("userid:" + ubean.getPin());
			cstmt.setString(11, ubean.getMail());
			System.out.println("userid:" + ubean.getMail());
			cstmt.setString(12, ubean.getSex());
			cstmt.setString(13, ubean.getQuestn());
			cstmt.setString(14, ubean.getVillage());
			cstmt.setString(15, DateWrapper.parseDate(ubean.getDob()));
			cstmt.setString(16, ubean.getSecans());
			cstmt.setString(17, ubean.getAccmode());
			File f1 = new File(ubean.getPhoto());
			FileInputStream fis = new FileInputStream(f1);
			System.out.println("fole=" + f1.length());
			cstmt.setBinaryStream(18, fis, (int) f1.length());
			System.out.println(ubean.getPhoto());
			cstmt.registerOutParameter(19, Types.VARCHAR);

			cstmt.execute();
			f = cstmt.getString(19);
			System.out.println("value of f:" + f);

			if (f.equalsIgnoreCase("true")) {
				/*
				 * ps=con.prepareStatement(
				 * "insert into user_permisions values(?,?,?)"); ps.setString(1,
				 * ubean.getUserid()); ps.setString(2, "public");
				 * ps.setString(3, "public"); int c=ps.executeUpdate(); if(c>0)
				 * {
				 */
				flag = true;
				con.commit();
				/* } */
			} else {
				con.rollback();
			}

			/* } */

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();

			}
			// TODO: handle exception
		} finally {
			try {
				con.close();
				/* ps.close(); */
				cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return flag;
	}

	public String isAuthenticated(String uid, String upwd) {
		String utype = "";
		try {
			ps = con
					.prepareStatement("select login_pwd,login_type,lock_info from login_details where login_id=? and login_pwd=?");
			ps.setString(1, uid);
			ps.setString(2, upwd);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(upwd)
						&& rs.getString(3).equals("unlock")) {
					utype = rs.getString(2);
					System.out.println(utype);
					return utype;
				} else if (rs.getString(1).equals(upwd)) {
					utype = "locked";
				}
			}
		} catch (Exception e) {
			System.out.println("Sql exception rised");
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return utype;
	}

	public boolean changePassword(AdminBean abean) {
		boolean flag = false;
		try {

			ps = con
					.prepareStatement("update login_details set login_id=?,login_pwd=? where login_id=?");
			ps.setString(1, abean.getNewadminid());
			ps.setString(2, abean.getNewadminpwd());
			ps.setString(3, abean.getAdminid());
			System.out.println(abean.getNewadminid());
			int count = ps.executeUpdate();
			if (count > 0) {
				flag = true;
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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

	public AdminBean getProfile(String user, String pass, String type) {
		AdminBean abean = new AdminBean();
		try {
			/*
			 * cstmt=con.prepareCall(
			 * "{call profile_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"); //we have to
			 * perform this by using prepare statement cstmt.setString(1,user);
			 * cstmt.setString(2, type);
			 * cstmt.registerOutParameter(3,Types.VARCHAR);
			 * cstmt.registerOutParameter(4,Types.VARCHAR);
			 * cstmt.registerOutParameter(5,Types.VARCHAR);
			 * cstmt.registerOutParameter(6, Types.VARCHAR);
			 * cstmt.registerOutParameter(7, Types.VARCHAR);
			 * cstmt.registerOutParameter(8,Types.VARCHAR);
			 * cstmt.registerOutParameter(9, Types.VARCHAR);
			 * cstmt.registerOutParameter(10, Types.VARCHAR);
			 * cstmt.registerOutParameter(11, Types.VARCHAR);
			 * cstmt.registerOutParameter(12,Types.VARCHAR);
			 * cstmt.registerOutParameter(13,Types.VARCHAR);
			 * cstmt.registerOutParameter(14,Types.VARCHAR);
			 * 
			 * cstmt.execute();
			 * 
			 * 
			 * abean.setFirstname(cstmt.getString(3));
			 * 
			 * System.out.println(cstmt.getString(3));
			 * 
			 * abean.setMidlename(cstmt.getString(4));
			 * abean.setLastname(cstmt.getString(5));
			 * abean.setDob(cstmt.getString(6));
			 * abean.setUniversity(cstmt.getString(7));
			 * abean.setQualification(cstmt.getString(8));
			 * abean.setVillage(cstmt.getString(9));
			 * abean.setMobile(cstmt.getString(10));
			 * abean.setCity(cstmt.getString(11));
			 * abean.setState(cstmt.getString(12));
			 * abean.setCountry(cstmt.getString(13));
			 * abean.setPin(cstmt.getString(14));
			 */

			ps = con
					.prepareStatement("select nvl(admin_fname,''),nvl(admin_mname,''),nvl(admin_lname,''),nvl(admin_dob,''),nvl(admin_qualification,''),nvl(admin_university,''),nvl(admin_village,'') from admin_personal where admin_id=?");
			ps.setString(1, user);
			rs = ps.executeQuery();
			ps1 = con
					.prepareStatement("select nvl(admin_mobile,''),nvl(admin_city,''),nvl(admin_state,''),nvl(admin_country,''),nvl(admin_pin,'') from admin_contacts where admin_id=?");
			ps1.setString(1, user);
			rs1 = ps1.executeQuery();
			while (rs.next() && rs1.next()) {
				abean.setFirstname(rs.getString(1));
				abean.setMidlename(rs.getString(2));
				abean.setLastname(rs.getString(3));
				abean.setDob(rs.getString(4));
				abean.setQualification(rs.getString(5));
				abean.setUniversity(rs.getString(6));
				abean.setVillage(rs.getString(7));

				abean.setMobile(rs1.getString(1));
				abean.setCity(rs1.getString(2));
				abean.setState(rs1.getString(3));
				abean.setCountry(rs1.getString(4));
				abean.setPin(rs1.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				con.close();
				ps.close();
				rs.close();
				rs1.close();
				ps1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return abean;
	}

	public boolean updateAdminDetails(AdminBean abean) {
		boolean flag = false;
		String f = "";
		try {
			cstmt = con
					.prepareCall("{call Admin_Insert_Procedure(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, abean.getAdminid());
			cstmt.setString(2, abean.getAdmintype());
			cstmt.setString(3, abean.getFirstname()); // insert into personal
			// table
			cstmt.setString(4, abean.getMidlename());
			cstmt.setString(5, abean.getLastname());
			cstmt.setString(6, abean.getDob());

			cstmt.setString(7, abean.getUniversity());
			cstmt.setString(8, abean.getQualification());

			System.out.println(abean.getUniversity());
			System.out.println(abean.getQualification());
			cstmt.setString(9, abean.getMobile());
			cstmt.setString(10, abean.getVillage());
			cstmt.setString(11, abean.getCity());
			cstmt.setString(12, abean.getState());
			cstmt.setString(13, abean.getCountry());
			cstmt.setString(14, abean.getPin());
			cstmt.registerOutParameter(15, Types.VARCHAR);
			cstmt.executeUpdate();

			f = cstmt.getString(15);
			if (f.equalsIgnoreCase("true")) {
				flag = true;
				con.commit();
			} else
				con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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

	public boolean addNewAdmin(AdminBean abean) {
		boolean flag = false;
		try {
			System.out.println(abean.getAdminid());
			System.out.println(abean.getAdminpwd());
			
			String subadmin="Govtbody";

			ps = con
					.prepareStatement("insert into login_details(login_id,login_pwd,login_type,doj) values(?,?,?,sysdate)");

			ps.setString(1, abean.getAdminid());
			ps.setString(2, abean.getAdminpwd());
			ps.setString(3, subadmin);

			int count = ps.executeUpdate();

			if (count > 0) {
				ps = con
						.prepareStatement("insert into home_potos(poto_id,user_id) values((select max(poto_id)+1 from home_potos),?)");
				ps.setString(1, abean.getAdminid());
				int c = ps.executeUpdate();
				if (c > 0) {
					flag = true;
					con.commit();
				}
			} else
				con.rollback();
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

	public ArrayList<UserBean> getAccountList(String realpath) {
		ArrayList<UserBean> accountlist = new ArrayList<UserBean>();
		try {
			st = con.createStatement();
			rs = st
					.executeQuery("select login_id,lock_info,doj,(select poto_id from home_potos where home_potos.user_id=login_id),(select potos from home_potos where home_potos.user_id=login_id) from login_details where login_type='user'");
			UserBean ubean = null;
			while (rs.next()) {
				ubean = new UserBean();
				ubean.setUserid(rs.getString(1));
				ubean.setLockinfo(rs.getString(2));
				ubean.setDoj(DateWrapper.parseDate(rs.getDate(3)));
				ubean.setPhotoid(rs.getInt(4));
				try {
					String path = "";
					Blob b = rs.getBlob(5);
					byte b1[] = b.getBytes(1, (int) b.length());
					path = realpath + "/" + rs.getString(1) + ".jpg";
					System.out.println("path  :" + path);
					OutputStream fout = new FileOutputStream(path);
					fout.write(b1);
					ubean.setPhoto(path);
				} catch (Exception e) {
					System.out.println(e);
				}
				accountlist.add(ubean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accountlist;
	}

	/*
	 * public void init() throws ServletException { try {
	 * AbstractDataAccessObject ado=new AbstractDataAccessObject();
	 * con=ado.getConnection();
	 * 
	 * 
	 * } catch(Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}
