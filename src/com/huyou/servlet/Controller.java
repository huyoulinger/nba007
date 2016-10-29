package com.huyou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huyou.domain.NbaData;
import com.huyou.domain.TeamScore;
import com.huyou.service.GetSaiChengService;
import com.huyou.service.impl.GetSaiChengServiceImpl;
import com.huyou.utils.WebUtils;

public class Controller extends HttpServlet {
	
	private NbaData data;			
	GetSaiChengService gs = new GetSaiChengServiceImpl();
	private boolean flag;
	private Boolean flag1;

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
		}
	}
	private void updateScore(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		data=WebUtils.getRequest1();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < data.result.list.get(i).tr.size(); j++) {
				String mPlayer1=data.result.list.get(i).tr.get(j).player1;
				String mPlayer2=data.result.list.get(i).tr.get(j).player2;
				String player1 = WebUtils.changName(mPlayer1);
				String player2 = WebUtils.changName(mPlayer2);				
				//String title = data.result.list.get(i).title;
				String score = data.result.list.get(i).tr.get(j).score;
				String time = data.result.list.get(i).tr.get(j).time;
				String status =data.result.list.get(i).tr.get(j).status;

				if ("2".equals(status)) {
					TeamScore teamScore = gs.findTeamBy(player1,player2,time);
					String statString = teamScore.getStatus();
					int id = teamScore.getId();
					if ("0".equals(statString)) {
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


	private void updateData(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//�������ȡ����
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

				if ("0".equals(status)) {
					boolean dataExris =gs.findTeamDetail(player1,player2,time);

					if (dataExris) {
						flag = gs.update(player1,player2,title,time,score,status);																		
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
