package com.huyou.dao;

import java.sql.Timestamp;
import java.util.List;

import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;

public interface GetSaiChengDao {
	/**
	 * 获取列表
	 * @return返回列表集合
	 */
	public List<TeamScore> getTeamLists();
	
	/**
	 * 根据player1、player2、time查询该场比赛是否已经存在
	 * @param player1球队名
	 * @param player2球队名
	 * @param time开赛时间
	 * @return返回存在还是不存在 true不存在，false存在
	 */
	public boolean findTeamDetail(String player1, String player2, Timestamp time);
	
	
	/**
	 * 查询该场比赛是否存在，存在返回该场比赛的对象
	 * @param player1
	 * @param player2
	 * @param time
	 * @return
	 */
	public TeamScore findTeamBy(String player1, String player2, Timestamp time);
	
	/**
	 * 根据id更新比赛赛果和特征码
	 * @param id
	 * @param score赛果
	 * @param status特征码
	 * @return是否更新成功
	 */
	public Boolean updateScore(int id, String score, String status);

	public TeamMySqlTable finTeamByName(String pname);

	public List<FenXi> getAllTeamDatas(String tbname);

	public boolean add(TeamScore t);

	public boolean addMatchPlayer1(TeamScore t, String player1tb);

	public boolean addMatchPlayer2(TeamScore t, String player2tb);

}
