package cn.itbaizhan.tyut.exam.sys.services.impl;

import java.util.List;

import cn.itbaizhan.tyut.exam.common.PageControl;
import cn.itbaizhan.tyut.exam.common.Pager;
import cn.itbaizhan.tyut.exam.model.SysFunction;
import cn.itbaizhan.tyut.exam.model.Sysrole;
import cn.itbaizhan.tyut.exam.model.Sysuser;
import cn.itbaizhan.tyut.exam.sys.dao.impl.UserDao;
import cn.itbaizhan.tyut.exam.sys.dao.interfaces.IUserDao;
import cn.itbaizhan.tyut.exam.sys.services.interfaces.IUserService;

public class UserService implements IUserService {

	IUserDao dao = new UserDao();
	
	public Sysuser login(Sysuser user) {//登陆
		return dao.login(user);
	}

	public List<SysFunction> initpage(Sysuser user) {//初始化用户功能列表
		return dao.initpage(user);
	}
	public Pager<Sysuser> list(Sysuser user, PageControl pc) {//获取系统用户列表
		return dao.list(user, pc);
	}

	public Integer add(Sysuser user) {//增加用户
		try{
			return dao.add(user);
		}catch(Exception e){
			throw new RuntimeException();
		}
		
	}
	public Sysuser detail(Sysuser user) {//获取用户详细信息
		return dao.detail(user);
	}
	public Integer edit(Sysuser user) {//修改用户功能
		return dao.edit(user);
	}

	public Integer editpwd(Sysuser user) {//修改用户密码功能
		// TODO Auto-generated method stub
		return dao.editpwd(user);
	}
	public Sysuser stulogin(Sysuser user) {//学生登陆
		return dao.stulogin(user);
	}
}
