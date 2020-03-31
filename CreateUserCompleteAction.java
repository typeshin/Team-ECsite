package com.internousdev.galaxy.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.galaxy.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class CreateUserCompleteAction extends ActionSupport implements SessionAware {

	private Map<String,Object> session;

	//エラーの場合入力値表示
	public String execute() {
		String result = ERROR;
		String sex = null;
		if("女性".equals(String.valueOf(session.get("sex")))) {
				sex = "1";
		}else {
				sex = "0";
		}

		//DAOに登録
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		int count = userInfoDAO.createUser(
			session.get("familyName").toString(),
			session.get("firstName").toString(),
			String.valueOf(session.get("familyNameKana")),
			String.valueOf(session.get("firstNameKana")),
			sex,
			String.valueOf(session.get("email")),
			session.get("userIdForCreateUser").toString(),
			session.get("password").toString());

		if(count > 0){
			result = SUCCESS;
			session.put("createUserFlag", "1");
		}

		//完了画面の為削除
		session.remove("familyName");
		session.remove("firstName");
		session.remove("familNameKana");
		session.remove("firstNameKana");
		session.remove("sex");
		session.remove("sexList");
		session.remove("email");
		session.remove("password");

		return result;

	}

	public Map<String,Object> session(){
		return this.session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}
}