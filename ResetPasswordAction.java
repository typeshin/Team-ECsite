package com.internousdev.galaxy.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordAction extends ActionSupport implements SessionAware{

	//変数定義
	private String backFlag;
	private Map<String, Object> session;

	public String execute() {

		//履歴削除
		if (backFlag == null) {
			session.remove("userIdForResetPassword");
		}

		return SUCCESS;
	}

	public String getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}