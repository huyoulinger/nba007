package com.huyou.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class NbaData {
	public int error_code;
	public String reason;
	public ResultData result;
	
	public class ResultData{
		public String title;
		public ArrayList<ListData> list;
		public StatuslistData statuslist;
		public ArrayList<TeammatchData> teammatch;
		
		
		@Override
		public String toString() {
			return "ResultData [title=" + title + ", list=" + list
					+ ", statuslist=" + statuslist + ", teammatch=" + teammatch
					+ "]";
		}
		
	}
	
	public class ListData{
		public String title;
		public ArrayList<TrData> tr;
		
		public class TrData{
			public String player1;
			public String player2;
			public String score;
			public String status;
			public String time;
			@Override
			public String toString() {
				return "TrData [player1=" + player1 + ", player2=" + player2
						+ ", score=" + score + ", status=" + status + ", time="
						+ time + "]";
			}
			
		}

		@Override
		public String toString() {
			return "ListData [title=" + title + ", tr=" + tr + "]";
		}
		
	}
	
	public class StatuslistData{
		public String st0;
		public String st1;
		public String st2;
		@Override
		public String toString() {
			return "StatuslistData [st0=" + st0 + ", st1=" + st1 + ", st2="
					+ st2 + "]";
		}
		
	}
	
	public class TeammatchData{
		public String name;
		public String url;
		@Override
		public String toString() {
			return "TeammatchData [name=" + name + ", url=" + url + "]";
		}
		
	}


	@Override
	public String toString() {
		return "NbaData [error_code=" + error_code + ", reason=" + reason
				+ ", result=" + result + "]";
	}
	
}
