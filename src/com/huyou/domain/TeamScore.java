package com.huyou.domain;

import java.sql.Timestamp;

public class TeamScore {

	private int id;
	private String title;
	private Timestamp time;
	private String player1;
	private String score;
	private String player2;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlayer1() {
		return player1;
	}
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPlayer2() {
		return player2;
	}
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TeamScore [id=" + id + ", title=" + title + ", player1="
				+ player1 + ", score=" + score + ", player2=" + player2
				+ ", time=" + time + ", status=" + status + "]";
	}


}

