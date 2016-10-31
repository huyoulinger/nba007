package com.huyou.service;

import java.util.List;

import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;

public interface GetSaiChengService {

	public List<TeamScore> geTeamLists();

	//public boolean update(NbaData data);

	public boolean findTeamDetail(String player1, String player2, String time);

	public boolean update(String player1, String player2, String title,
			String time, String score, String status);

	public TeamScore findTeamBy(String player1, String player2, String time);

	public Boolean updateScore(int id, String score, String status);

	public TeamMySqlTable findTeamByName(String pname);

	public List<FenXi> getAllTeamDatas(String tbname);
	
}
