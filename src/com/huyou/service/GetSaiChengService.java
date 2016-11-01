package com.huyou.service;

import java.sql.Timestamp;
import java.util.List;

import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;

public interface GetSaiChengService {

	public List<TeamScore> geTeamLists();

	//public boolean update(NbaData data);

	public boolean findTeamDetail(String player1, String player2, Timestamp mTimestamp);


	public TeamScore findTeamBy(String player1, String player2, Timestamp mTimestamp);

	public Boolean updateScore(int id, String score, String status);

	public TeamMySqlTable findTeamByName(String pname);

	public List<FenXi> getAllTeamDatas(String tbname);

	public boolean add(TeamScore t);

	public boolean addMatchPlayer1(TeamScore t, String player1tb);

	public boolean addMatchPlayer2(TeamScore t, String player2tb);

}
