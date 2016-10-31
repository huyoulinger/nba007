package com.huyou.dao;

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
	public boolean findTeamDetail(String player1, String player2, String time);
	
	/**
	 * 插入赛程
	 * @param player1
	 * @param player2
	 * @param title
	 * @param time
	 * @param score
	 * @param status
	 * @return是否插入成功
	 */

	public boolean update(String player1, String player2, String title,
			String time, String score, String status);
	
	/**
	 * 查询该场比赛是否存在，存在返回该场比赛的对象
	 * @param player1
	 * @param player2
	 * @param time
	 * @return
	 */
	public TeamScore findTeamBy(String player1, String player2, String time);
	
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

}
