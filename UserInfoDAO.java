package com.internousdev.galaxy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.internousdev.galaxy.dto.UserInfoDTO;
import com.internousdev.galaxy.util.DBConnector;

public class UserInfoDAO {

	//ユーザー登録
	public int createUser(String familyName,String firstName,String familyNameKana,String firstNameKana,String sex,String email,String userId,String password){

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		int count = 0;
		String sql = "INSERT INTO user_info(family_name,first_name,family_name_kana,first_name_kana,sex,email,user_id,password,status,logined,regist_date,update_date)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,now(),now())";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, familyName);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, familyNameKana);
			preparedStatement.setString(4, firstNameKana);
			preparedStatement.setString(5, sex);
			preparedStatement.setString(6, email);
			preparedStatement.setString(7, userId);
			preparedStatement.setString(8, password);
			preparedStatement.setInt(9, 0);
			preparedStatement.setInt(10, 1);
			count = preparedStatement.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	//ログインチェックとpw再設定
	public boolean isExistsUserInfo(String userId, String password) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean result = false;

		String sql = "select count(*) as count from user_info where user_id=? and password=?";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if (resultSet.getInt("count") > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//ユーザー登録でユーザー存在チェック
	public boolean isExistsUserInfo(String userId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean result = false;

		String sql = "select count(*) as count from user_info where user_id=?";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if (resultSet.getInt("count") > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//DTO格納、ログインとpw再設定
	public UserInfoDTO getUserInfo(String userId, String password) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		UserInfoDTO userInfoDTO = new UserInfoDTO();

		String sql = "select * from user_info where user_id=? and password=?";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				userInfoDTO.setId(resultSet.getInt("id"));
				userInfoDTO.setUserId(resultSet.getString("user_id"));
				userInfoDTO.setPassword(resultSet.getString("password"));
				userInfoDTO.setFamilyName(resultSet.getString("family_name"));
				userInfoDTO.setFirstName(resultSet.getString("first_name"));
				userInfoDTO.setFamilyNameKana(resultSet.getString("family_name_kana"));
				userInfoDTO.setSex(resultSet.getInt("sex"));
				userInfoDTO.setEmail(resultSet.getString("email"));
				userInfoDTO.setTempLoginId(resultSet.getInt("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfoDTO;
	}

	//DTO格納、ID紐付け
	public UserInfoDTO getUserInfo(String userId) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		UserInfoDTO userInfoDTO = new UserInfoDTO();

		String sql = "select * from user_info where user_id=?";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				userInfoDTO.setId(resultSet.getInt("id"));
				userInfoDTO.setUserId(resultSet.getString("user_id"));
				userInfoDTO.setPassword(resultSet.getString("password"));
				userInfoDTO.setFamilyName(resultSet.getString("family_name"));
				userInfoDTO.setFirstName(resultSet.getString("first_name"));
				userInfoDTO.setFamilyNameKana(resultSet.getString("family_name_kana"));
				userInfoDTO.setFirstNameKana(resultSet.getString("first_name_kana"));
				userInfoDTO.setSex(resultSet.getInt("sex"));
				userInfoDTO.setEmail(resultSet.getString("email"));
				userInfoDTO.setTempLoginId(resultSet.getInt("logined"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfoDTO;
	}

	//パスワード再設定部分
	public int resetPassword(String userId, String password) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "update user_info set password=?, update_date=now() where user_id=?";
		int result = 0;

		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, userId);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}