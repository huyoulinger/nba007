package com.huyou.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.huyou.domain.NbaData;

public class WebUtils {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	//配置您申请的KEY
	public static final String APPKEY ="4a99d4d7e1dd179d87d62c95a25dffe6";
	public static NbaData data;

	//1.NBA常规赛赛程赛果
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static NbaData getRequest1(){

		String result =null;
		String url ="http://op.juhe.cn/onebox/basketball/nba";//请求接口地址
		Map params = new HashMap();//请求参数
		params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
		params.put("dtype","");//返回数据的格式,xml或json，默认json

		try {
			result =net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if(object.getInt("error_code")==0){
				System.out.println(object.get("result"));
			}else{
				System.out.println(object.get("error_code")+":"+object.get("reason"));
			}
			parseData(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private static void parseData(String result) {

		Gson gson = new Gson();
		data = gson.fromJson(result, NbaData.class);
		//System.out.println(data);

	}



	/**
	 *
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return  网络请求字符串
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String net(String strUrl, Map params,String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params!= null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String,Object>data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String changName(String mPlayer) {
		String newName = "";
		if ("76人".equals(mPlayer)) {
			newName="费城76人";
		}else if ("步行者".equals(mPlayer)) {
			newName="印第安纳步行者";
		}else if ("热火".equals(mPlayer)) {
			newName="迈阿密热火";
		}else if ("魔术".equals(mPlayer)) {
			newName="奥兰多魔术";
		}else if ("小牛".equals(mPlayer)) {
			newName="达拉斯小牛";
		}else if ("篮网".equals(mPlayer)) {
			newName="布鲁克林篮网";
		}else if ("凯尔特人".equals(mPlayer)) {
			newName="波士顿凯尔特人";
		}else if ("活塞".equals(mPlayer)) {
			newName="底特律活塞";
		}else if ("猛龙".equals(mPlayer)) {
			newName="多伦多猛龙";
		}else if ("公牛".equals(mPlayer)) {
			newName="芝加哥公牛";
		}else if ("黄蜂".equals(mPlayer)) {
			newName="夏洛特黄蜂";
		}else if ("雄鹿".equals(mPlayer)) {
			newName="密尔沃基雄鹿";
		}else if ("森林狼".equals(mPlayer)) {
			newName="明尼苏达森林狼";
		}else if ("灰熊".equals(mPlayer)) {
			newName="孟菲斯灰熊";
		}else if ("掘金".equals(mPlayer)) {
			newName="丹佛掘金";
		}else if ("鹈鹕".equals(mPlayer)) {
			newName="新奥尔良鹈鹕";
		}else if ("雷霆".equals(mPlayer)) {
			newName="俄克拉荷马城雷霆";
		}else if ("国王".equals(mPlayer)) {
			newName="萨克拉门托国王";
		}else if ("太阳".equals(mPlayer)) {
			newName="菲尼克斯太阳";
		}else if ("火箭".equals(mPlayer)) {
			newName="休斯顿火箭";
		}else if ("湖人".equals(mPlayer)) {
			newName="洛杉矶湖人";
		}else if ("奇才".equals(mPlayer)) {
			newName="华盛顿奇才";
		}else if ("马刺".equals(mPlayer)) {
			newName="圣安东尼奥马刺";
		}else if ("开拓者".equals(mPlayer)) {
			newName="波特兰开拓者";
		}else if ("勇士".equals(mPlayer)) {
			newName="金州勇士";
		}else if ("爵士".equals(mPlayer)) {
			newName="犹他爵士";
		}else if ("尼克斯".equals(mPlayer)) {
			newName="纽约尼克斯";
		}else if ("骑士".equals(mPlayer)) {
			newName="克里夫兰骑士";
		}else if ("老鹰".equals(mPlayer)) {
			newName="亚特兰大老鹰";
		}else if ("快船".equals(mPlayer)) {
			newName="洛杉矶快船";
		}
		
		return newName;
	}
}
