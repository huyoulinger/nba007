package com.huyou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huyou.domain.FenXi;
import com.huyou.domain.Max;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;
import com.huyou.service.GetSaiChengService;
import com.huyou.service.impl.GetSaiChengServiceImpl;
import com.huyou.utils.UiUtils;
import com.huyou.utils.WebUtils;

public class Controller extends HttpServlet {

	private NbaData data;			
	GetSaiChengService gs = new GetSaiChengServiceImpl();
	private boolean flag;
	private Boolean flag1;

	private String single;
	private String total;

	private List<String> singleList;
	private List<String> totalList;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String op = request.getParameter("op");

		if ("saicheng".equals(op)) {
			getSaiChengDatas(request, response);
		} else if ("updatedata".equals(op)) {
			updateData(request,response);
		}else if ("updatescore".equals(op)) {
			updateScore(request,response);
		}else if ("fenxi".equals(op)) {
			getFenXiDatas(request,response);
		}
	}
	private void getFenXiDatas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String nString = new String(name.getBytes("iso-8859-1"), "UTF-8");

		int m = 1;
		int n = 1;


		singleList = new ArrayList<String>();
		totalList = new ArrayList<String>();

		ArrayList<Integer> danMax = new ArrayList<Integer>();
		ArrayList<Integer> totalMax = new ArrayList<Integer>();




		// 获取球队所对应的表名
		// String name = UiUtils.getTeamBiaoMing(id);
		TeamMySqlTable t = gs.findTeamByName(nString);
		String tbname = t.getTbname();
		String tName = t.getName();
		List<FenXi> list = gs.getAllTeamDatas(tbname);

		for (int i = 0; i < list.size(); i++) {

			if (tName.equals(list.get(i).getPlayer1())) {
				single = list.get(i).getPlayer1parity();
			}
			if (tName.equals(list.get(i).getPlayer2())) {
				single = list.get(i).getPlayer2parity();
			}
			total = list.get(i).getTotalparity();

			singleList.add(single);
			totalList.add(total);

		}
		// 正序
		/*
		 * for (int i = 0; i < ji.size(); i++) { if (i == 0) { m = 1; } if (i >
		 * 0) { if (ji.get(i) == ji.get(i - 1)) { m++; } else { m = 1; } }
		 * list.get(i).setDanlian(m); }
		 */
		// 倒序
		for (int i = singleList.size() - 1; i >= 0; i--) {
			if (i == singleList.size() - 1) {
				m = 1;
			}
			if (i < singleList.size() - 1) {
				if (singleList.get(i) == singleList.get(i + 1)) {
					m++;
				} else {
					m = 1;
				}
			}
			danMax.add(m);
			list.get(i).setSingle(m);
		}

		for (int i = totalList.size() - 1; i >= 0; i--) {
			if (i == totalList.size() - 1) {
				n = 1;
			}
			if (i < totalList.size() - 1) {
				if (totalList.get(i) == totalList.get(i + 1)) {
					n++;
				} else {
					n = 1;
				}
			}
			totalMax.add(n);
			list.get(i).setTotal(n);
		}



		List<Max> max =new ArrayList<Max>();
		Max ma = new Max();	
		ma.setDanmax(UiUtils.CalculationMax(danMax));
		ma.setTotalmax(UiUtils.CalculationMax(totalMax));		
		max.add(ma);
		// 将数据存放到session中
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("max", max);
		// 页面重定向到主页面
		response.sendRedirect(request.getContextPath() + "/list.jsp");
	}
	/**
	 * 更新赛果
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void updateScore(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		data=WebUtils.getRequest1();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < data.result.list.get(i).tr.size(); j++) {
				String mPlayer1=data.result.list.get(i).tr.get(j).player1;
				String mPlayer2=data.result.list.get(i).tr.get(j).player2;
				String player1 = WebUtils.changName(mPlayer1);
				String player2 = WebUtils.changName(mPlayer2);				
				String title = data.result.list.get(i).title;
				String score = data.result.list.get(i).tr.get(j).score;
				String time = data.result.list.get(i).tr.get(j).time;
				String status =data.result.list.get(i).tr.get(j).status;
				//拼接成标准时间字符串
				String mytime = "2016/"+time+":00";
				String mytime1 = mytime.replace("/", "-");
				//把时间字符串转换成timestamp格式
				Timestamp mTimestamp =Timestamp.valueOf(mytime1);

				if ("2".equals(status)) {
					TeamScore teamScore = gs.findTeamBy(player1,player2,mTimestamp);					
					String statString = teamScore.getStatus();
					int id = teamScore.getId();
					if ("0".equals(statString)) {

						TeamMySqlTable t1 = gs.findTeamByName(player1);
						TeamMySqlTable t2 = gs.findTeamByName(player2);
						String player1tb = t1.getTbname();
						String player2tb = t2.getTbname();

						TeamScore t = new TeamScore();
						t.setTitle(title);
						t.setTime(mTimestamp);
						t.setPlayer1(player1);
						t.setScore(score);
						t.setPlayer2(player2);
						t.setStatus(status);


						boolean flagPlayer1 = gs.addMatchPlayer1(t,player1tb);
						boolean flagPlayer2 = gs.addMatchPlayer2(t,player2tb);
						flag1 = gs.updateScore(id,score,status);
					}
				}
			}
		}

		if (flag1) {
			// 说明添加成功了，转向主页面
			// 先重新查询数据库，拿取数据后在转向
			getSaiChengDatas(request, response);
		} else {
			// 添加失败
			request.setAttribute("error", "添加失败");
			request.getRequestDispatcher("/tui_team_add.jsp").forward(request,
					response);
		}
	}

	/**
	 * 更新赛程
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateData(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//从网络获取数据
		data=WebUtils.getRequest1();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < data.result.list.get(i).tr.size(); j++) {
				//获取球队名
				String mPlayer1=data.result.list.get(i).tr.get(j).player1;
				String mPlayer2=data.result.list.get(i).tr.get(j).player2;
				//球队名转换
				String player1 = WebUtils.changName(mPlayer1);
				String player2 = WebUtils.changName(mPlayer2);
				//获取其他项
				String title = data.result.list.get(i).title;
				String score = data.result.list.get(i).tr.get(j).score;
				String time =  data.result.list.get(i).tr.get(j).time;
				String mytime = "2016/"+time+":00";
				String mytime1 = mytime.replace("/", "-");
				Timestamp mTimestamp =Timestamp.valueOf(mytime1);

				String status =data.result.list.get(i).tr.get(j).status;
				//根据特征码判断比赛是不是未开赛，是就添加
				if ("0".equals(status)) {


					//根据player1,player2,time查询数据是否存在？存在不添加赛程，不存在添加
					boolean dataExris =gs.findTeamDetail(player1,player2,mTimestamp);

					if (dataExris) {

						TeamScore t = new TeamScore();
						t.setPlayer1(player1);
						t.setPlayer2(player2);
						t.setTitle(title);
						t.setTime(mTimestamp);
						t.setScore(score);
						t.setStatus(status);

						//flag = gs.update(player1,player2,title,time,score,status);
						flag =gs.add(t);
					}

				}
			}
		}

		if (flag) {
			// 说明添加成功了，转向主页面
			// 先重新查询数据库，拿取数据后在转向
			getSaiChengDatas(request, response);
		} else {
			// 添加失败
			request.setAttribute("error", "添加失败");
			request.getRequestDispatcher("/tui_team_add.jsp").forward(request,
					response);
		}

		//String title = data.result.title;
		//String[] scArr = title.split("_");
		//saishi = scArr[0];					

	}

	/**
	 * 获取列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getSaiChengDatas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<TeamScore> list = gs.geTeamLists();

		// 将数据存放到session中
		request.getSession().setAttribute("teamlist", list);

		// 页面重定向到主页面
		response.sendRedirect(request.getContextPath() + "/test.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
