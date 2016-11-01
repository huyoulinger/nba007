package com.huyou.dao;

import java.sql.Timestamp;
import java.util.List;

import com.huyou.domain.FenXi;
import com.huyou.domain.NbaData;
import com.huyou.domain.TeamMySqlTable;
import com.huyou.domain.TeamScore;

public interface GetSaiChengDao {
	/**
	 * ��ȡ�б�
	 * @return�����б���
	 */
	public List<TeamScore> getTeamLists();
	
	/**
	 * ����player1��player2��time��ѯ�ó������Ƿ��Ѿ�����
	 * @param player1�����
	 * @param player2�����
	 * @param time����ʱ��
	 * @return���ش��ڻ��ǲ����� true�����ڣ�false����
	 */
	public boolean findTeamDetail(String player1, String player2, Timestamp time);
	
	
	/**
	 * ��ѯ�ó������Ƿ���ڣ����ڷ��ظó������Ķ���
	 * @param player1
	 * @param player2
	 * @param time
	 * @return
	 */
	public TeamScore findTeamBy(String player1, String player2, Timestamp time);
	
	/**
	 * ����id���±���������������
	 * @param id
	 * @param score����
	 * @param status������
	 * @return�Ƿ���³ɹ�
	 */
	public Boolean updateScore(int id, String score, String status);

	public TeamMySqlTable finTeamByName(String pname);

	public List<FenXi> getAllTeamDatas(String tbname);

	public boolean add(TeamScore t);

	public boolean addMatchPlayer1(TeamScore t, String player1tb);

	public boolean addMatchPlayer2(TeamScore t, String player2tb);

}
