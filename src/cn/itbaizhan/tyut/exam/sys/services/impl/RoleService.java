package cn.itbaizhan.tyut.exam.sys.services.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itbaizhan.tyut.exam.common.PageControl;
import cn.itbaizhan.tyut.exam.common.Pager;
import cn.itbaizhan.tyut.exam.model.SysFunction;
import cn.itbaizhan.tyut.exam.model.Sysrole;
import cn.itbaizhan.tyut.exam.sys.dao.impl.RoleDao;
import cn.itbaizhan.tyut.exam.sys.dao.interfaces.IRoleDao;
import cn.itbaizhan.tyut.exam.sys.services.interfaces.IRoleService;

public class RoleService implements IRoleService {

	IRoleDao dao = new RoleDao();
	
	public Pager<Sysrole> list(Sysrole role, PageControl pc) {
		return dao.list(role, pc);//获取角色列表
	}

	public Integer add(Sysrole role) {
		return dao.add(role);//新增角色
	}

	public List<SysFunction> initfunlist(Sysrole role) {
		return dao.initfunlist(role);//分配权限初始化页面数据
	}

	public Sysrole detail(Sysrole role) {
		return dao.detail(role);//获取角色详细信息
	}

	public Integer saveright(String roleid, String[] funids) {
		return dao.saveright(roleid, funids);//保存角色权限
	}
	public Integer edit(Sysrole role) {
		return dao.edit(role);//修改角色功能
	}

	public ArrayList<Sysrole> getALL() {
		// TODO Auto-generated method stub
		return dao.getALL();//获取全部角色功能
	}
}
