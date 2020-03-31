package com.internousdev.galaxy.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.galaxy.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordCompleteAction extends ActionSupport implements SessionAware {

	//変数定義
	private Map<String,Object> session;

	public String execute() {

			String result = ERROR;

			//DAOに新しいpwを登録
			UserInfoDAO userInfoDAO = new UserInfoDAO();
			int count = userInfoDAO.resetPassword(session.get("userIdForResetPassword").toString(),session.get("newPassword").toString());

			if(count > 0) {
				result = SUCCESS;
			}

			//成功した場合の履歴削除
			session.remove("userIdForResetPassword");
			session.remove("newPassword");

		return result;
	}

	public Map<String,Object> getSession(){
		return session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}
}