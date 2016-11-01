package com.huyou.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.huyou.dao.GetSaiChengDao;
import com.huyou.dao.impl.GetSaiChengDaoImpl;
import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;
import com.huyou.service.GetSaiChengService;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class GetSaiChengServiceImpl implements GetSaiChengService {
	GetSaiChengDao dao = new GetSaiChengDaoImpl();
	@Override
	public List<TeamScore> geTeamLists() {
		return dao.getTeamLists();
	}
	
	@Override
	public boolean findTeamDetail(String player1, String player2, Timestamp time) {
		return dao.findTeamDetail(player1,player2,time);
	}
	
	

	@Override
	public TeamScore findTeamBy(String player1, String player2, Timestamp time) {
		return dao.findTeamBy(player1,player2,time);
	}

	@Override
	public Boolean updateScore(int id, String score,String status) {
		return dao.updateScore(id,score,status);
	}

	@Override
	public TeamMySqlTable findTeamByName(String pname) {
		return dao.finTeamByName(pname);
	}

	@Override
	public List<FenXi> getAllTeamDatas(String tbname) {
		return dao.getAllTeamDatas(tbname);
	}

	@Override
	public boolean add(TeamScore t) {
		return dao.add(t);
	}

	@Override
	public boolean addMatchPlayer1(TeamScore t, String player1tb) {
		return dao.addMatchPlayer1(t,player1tb);
	}

	@Override
	public boolean addMatchPlayer2(TeamScore t, String player2tb) {
		return dao.addMatchPlayer2(t,player2tb);
	}
		

}
