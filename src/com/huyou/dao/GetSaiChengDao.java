package com.huyou.dao;

import java.util.List;

import com.huyou.domain.NbaData;
import com.huyou.domain.TeamScore;

public interface GetSaiChengDao {

	public List<TeamScore> getTeamLists();

	//public boolean update(NbaData data);

	public boolean findTeamDetail(String player1, String player2, String time);

	public boolean update(String player1, String player2, String title,
			String time, String score, String status);

	public TeamScore findTeamBy(String player1, String player2, String time);

	public Boolean updateScore(int id, String score, String status);

}
