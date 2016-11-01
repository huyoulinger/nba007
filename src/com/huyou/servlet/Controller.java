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




		// ��ȡ�������Ӧ�ı���
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
		// ����
		/*
		 * for (int i = 0; i < ji.size(); i++) { if (i == 0) { m = 1; } if (i >
		 * 0) { if (ji.get(i) == ji.get(i - 1)) { m++; } else { m = 1; } }
		 * list.get(i).setDanlian(m); }
		 */
		// ����
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
		// �����ݴ�ŵ�session��
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("max", max);
		// ҳ���ض�����ҳ��
		response.sendRedirect(request.getContextPath() + "/list.jsp");
	}
	/**
	 * ��������
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
				//ƴ�ӳɱ�׼ʱ���ַ���
				String mytime = "2016/"+time+":00";
				String mytime1 = mytime.replace("/", "-");
				//��ʱ���ַ���ת����timestamp��ʽ
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
			// ˵����ӳɹ��ˣ�ת����ҳ��
			// �����²�ѯ���ݿ⣬��ȡ���ݺ���ת��
			getSaiChengDatas(request, response);
		} else {
			// ���ʧ��
			request.setAttribute("error", "���ʧ��");
			request.getRequestDispatcher("/tui_team_add.jsp").forward(request,
					response);
		}
	}

	/**
	 * ��������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateData(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//�������ȡ����
		data=WebUtils.getRequest1();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < data.result.list.get(i).tr.size(); j++) {
				//��ȡ�����
				String mPlayer1=data.result.list.get(i).tr.get(j).player1;
				String mPlayer2=data.result.list.get(i).tr.get(j).player2;
				//�����ת��
				String player1 = WebUtils.changName(mPlayer1);
				String player2 = WebUtils.changName(mPlayer2);
				//��ȡ������
				String title = data.result.list.get(i).title;
				String score = data.result.list.get(i).tr.get(j).score;
				String time =  data.result.list.get(i).tr.get(j).time;
				String mytime = "2016/"+time+":00";
				String mytime1 = mytime.replace("/", "-");
				Timestamp mTimestamp =Timestamp.valueOf(mytime1);

				String status =data.result.list.get(i).tr.get(j).status;
				//�����������жϱ����ǲ���δ�������Ǿ����
				if ("0".equals(status)) {


					//����player1,player2,time��ѯ�����Ƿ���ڣ����ڲ�������̣����������
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
			// ˵����ӳɹ��ˣ�ת����ҳ��
			// �����²�ѯ���ݿ⣬��ȡ���ݺ���ת��
			getSaiChengDatas(request, response);
		} else {
			// ���ʧ��
			request.setAttribute("error", "���ʧ��");
			request.getRequestDispatcher("/tui_team_add.jsp").forward(request,
					response);
		}

		//String title = data.result.title;
		//String[] scArr = title.split("_");
		//saishi = scArr[0];					

	}

	/**
	 * ��ȡ�б�
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getSaiChengDatas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<TeamScore> list = gs.geTeamLists();

		// �����ݴ�ŵ�session��
		request.getSession().setAttribute("teamlist", list);

		// ҳ���ض�����ҳ��
		response.sendRedirect(request.getContextPath() + "/test.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
