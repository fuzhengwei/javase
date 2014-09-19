package com.junit;

import static org.junit.Assert.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.junit.Test;

import com.stack.bean.PageBean;
import com.stack.bean.UserBean;
import com.stack.util.rsq.ReflectQueryDao;

public class JunitStack {

	ReflectQueryDao rqd = new ReflectQueryDao("com.stack.bean.UserBean");

	@Test
	public void insert() {
		UserBean user = new UserBean();
		user.setUser_name(URLEncoder.encode("付政委"));
		rqd.doInsertModel(user);

	}

	@Test
	public void delete() {
		rqd.doDeleteModelById("23");
	}

	@Test
	public void update() {
		UserBean user = new UserBean();
		user.setUser_id("1");
		user.setUser_name(URLEncoder.encode("奥巴马"));
		rqd.doUpdateModel(user);
	}

	@Test
	public void select() {

		// 设置分页 默认为每页10条数据
		PageBean pageBean = new PageBean();
		pageBean.setFirstResult(1);

		ArrayList<UserBean> modelList = (ArrayList<UserBean>) rqd
				.doSelectModelList(pageBean);

		for (UserBean user : modelList) {
			System.out.print(user.getUser_id() + " ");
			System.out.print(URLDecoder.decode(user.getUser_name()) + " ");
		}

	}

	@Test
	public void selectSerach() {

		// 设置分页 默认为每页10条数据
		PageBean pageBean = new PageBean();
		pageBean.setFirstResult(1);

		UserBean user = new UserBean();
		user.setUser_id("1");
		user.setUser_name("admin");
		// user.setUser_pwd("11");

		ArrayList<UserBean> modelList = (ArrayList<UserBean>) rqd
				.doSelectModelListBySerach(pageBean, user);
		for (UserBean bean : modelList) {
			System.out.println(bean.getUser_id() + " " + bean.getUser_name()
					+ " " + bean.getUser_pwd());
		}
	}

	@Test
	public void selectById() {
		UserBean user = (UserBean) rqd.doSelectModelById("1");
		System.out.println(URLDecoder.decode(user.getUser_name()));
	}

}
