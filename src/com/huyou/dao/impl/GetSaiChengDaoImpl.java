package com.huyou.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.huyou.dao.GetSaiChengDao;
import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;
import com.huyou.utils.JdbcUtils;
import com.huyou.utils.WebUtils;

public class GetSaiChengDaoImpl implements GetSaiChengDao {


	//String driver = "com.mysql.jdbc.Driver";
	//String username = System.getenv("ACCESSKEY");
	//String password = System.getenv("SECRETKEY");
	//System.getenv("MYSQL_HOST_S"); 为从库，只读
	//String sql_url = "jdbc:mysql://"+System.getenv("MYSQL_HOST")+":"+System.getenv("MYSQL_PORT")+"/"+System.getenv("MYSQL_DB");

	@Override
	public List<TeamScore> getTeamLists() {
		ResultSet rs = null;
		List<TeamScore> list = new ArrayList<TeamScore>();
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();

		//Connection conn = null;
		PreparedStatement pstmt =null;
		try {
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection(sql_url,username,password);
			pstmt = conn.prepareStatement("select * from saicheng");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				TeamScore teamList = new TeamScore();

				teamList.setId(rs.getInt("id"));
				teamList.setTitle(rs.getString("title"));
				teamList.setPlayer1(rs.getString("player1"));
				teamList.setScore(rs.getString("score"));
				teamList.setPlayer2(rs.getString("player2"));
				teamList.setTime(rs.getString("time"));
				teamList.setStatus(rs.getString("status"));
				list.add(teamList);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, pstmt, conn);
		}


		return list;
	}

	@Override
	public boolean findTeamDetail(String player1, String player2, String time) {
		ResultSet rs = null;
		Connection conn = JdbcUtils.getConnection();
		//Connection conn =null;
		PreparedStatement pstmt = null;
		int a=0;
		// 加载Mysql驱动
		try {
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection(sql_url,username,password);
			String sqlString ="select * from saicheng where player1 = ? and player2 = ? and time = ?";
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setString(1, player1);
			pstmt.setString(2, player2);
			pstmt.setString(3, time);

			rs=pstmt.executeQuery();
			if (!rs.next()) {
				a=0;
			} else {
				a=1;
			}
		} catch (Exception e) {
			System.out.print(e);
		}finally{
			JdbcUtils.release(null, pstmt, conn);
		}		
		return a==0 ? true : false;
	}

	@Override
	public boolean update(String player1, String player2, String title,
			String time, String score,String status) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();

		//ResultSet rs = null;
		//Connection conn =null;
		PreparedStatement pstmt = null;
		int n=0;
		try {
			// 加载Mysql驱动
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection(sql_url,username,password);
			String insertSql = "insert into saicheng (title,player1,score,player2,time,status) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setString(1, title);
			pstmt.setString(2, player1);
			pstmt.setString(3, score);
			pstmt.setString(4, player2);
			pstmt.setString(5, time);
			pstmt.setString(6, status);
			n = pstmt.executeUpdate();

			if (n != 0) {
				System.out.println("插入数据成功");
			} else {
				System.out.println("插入数据失败");
			}

		} catch (Exception e) {
			System.out.print(e);
		}finally{
			JdbcUtils.release(null, pstmt, conn);
		}						

		return  n >0 ? true : false;
	}

	@Override
	public TeamScore findTeamBy(String player1, String player2, String time) {
		ResultSet rs = null;
		Connection conn = JdbcUtils.getConnection();
		//Connection conn =null;
		PreparedStatement pstmt = null;
		TeamScore teamList = new TeamScore();
		// 加载Mysql驱动
		try {
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection(sql_url,username,password);
			String sqlString ="select * from saicheng where player1 = ? and player2 = ? and time = ?";
			pstmt = conn.prepareStatement(sqlString);
			pstmt.setString(1, player1);
			pstmt.setString(2, player2);
			pstmt.setString(3, time);

			rs=pstmt.executeQuery();
			if (rs.next()) {
				// 封装数据

				teamList.setId(rs.getInt("id"));
				teamList.setTitle(rs.getString("title"));
				teamList.setPlayer1(rs.getString("player1"));
				teamList.setScore(rs.getString("score"));
				teamList.setPlayer2(rs.getString("player2"));
				teamList.setTime(rs.getString("time"));
				teamList.setStatus(rs.getString("status"));

			}
		} catch (Exception e) {
			System.out.print(e);
		}finally{
			JdbcUtils.release(rs, pstmt, conn);
		}		
		return teamList;
	}

	@Override
	public Boolean updateScore(int id, String score,String status) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		//Connection conn =null;
		PreparedStatement pstmt = null;
		int n=0;
		try {
			// 加载Mysql驱动
			//Class.forName(driver).newInstance();
			//conn = DriverManager.getConnection(sql_url,username,password);
			String insertSql = "update saicheng set score = ?,status = ? where id = ?";
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setString(1, score);
			pstmt.setString(2, status);
			pstmt.setInt(3, id);
			
			n = pstmt.executeUpdate();

			if (n != 0) {
				System.out.println("插入数据成功");
			} else {
				System.out.println("插入数据失败");
			}

		} catch (Exception e) {
			System.out.print(e);
		}finally{
			JdbcUtils.release(null, pstmt, conn);
		}						

		return  n >0 ? true : false;	

	}

	
	@Override
	public TeamMySqlTable finTeamByName(String pname) {
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		TeamMySqlTable t = new TeamMySqlTable();
		
		sql = "select * from team_id where name = ?";
		
		// 创建预处理命令对象
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pname);
			// 执行sql语句
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 封装数据
				
				t.setId(rs.getString("id"));				
				t.setSaishi(rs.getString("saishi"));
				t.setName(rs.getString("name"));
				t.setTbname(rs.getString("tbname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return t;
		
	}

	
	@Override
	public List<FenXi> getAllTeamDatas(String name) {
		ResultSet rs = null;
		List<FenXi> list = new ArrayList<FenXi>();
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("select * from " + name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				FenXi f = new FenXi();

				String sc = rs.getString("score");
				
				
				String[] scArr = sc.split("-");				
				
				int a = Integer.parseInt(scArr[0]);
				int b = Integer.parseInt(scArr[1]);
				
				
				int total = a + b;
				
				String zhujiou;
				String kejiou;
				String totaljiou;
				String banjiou;
				if (a % 2 == 0) {
					zhujiou = "偶";
				} else {
					zhujiou = "奇";
				}
				if (b % 2 == 0) {
					kejiou = "偶";
				} else {
					kejiou = "奇";
				}
				if (total % 2 == 0) {
					totaljiou = "偶";
				} else {
					totaljiou = "奇";
				}				
								
				f.setId(rs.getInt("id"));
				f.setTitle(rs.getString("title"));
				f.setPlayer1(rs.getString("player1"));
				f.setPlayer1parity(zhujiou);				
				f.setScore(rs.getString("score"));
				f.setPlayer2parity(kejiou);
				f.setPlayer2(rs.getString("player2"));
				f.setTotalparity(totaljiou);
				f.setTime(rs.getString("time"));
				f.setStatus(rs.getString("status"));
				list.add(f);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return list;
	}

}
