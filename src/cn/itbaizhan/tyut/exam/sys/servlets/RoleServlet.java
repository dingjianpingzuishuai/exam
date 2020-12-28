package cn.itbaizhan.tyut.exam.sys.servlets;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itbaizhan.tyut.exam.common.PageControl;
import cn.itbaizhan.tyut.exam.common.Pager;
import cn.itbaizhan.tyut.exam.common.Tools;
import cn.itbaizhan.tyut.exam.model.SysFunction;
import cn.itbaizhan.tyut.exam.model.Sysrole;
import cn.itbaizhan.tyut.exam.sys.services.impl.RoleService;
import cn.itbaizhan.tyut.exam.sys.services.interfaces.IRoleService;

public class RoleServlet extends HttpServlet {

	IRoleService service = new RoleService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		if(cmd.equals("list")){//获取角色列表
			list(request,response);
		}else if(cmd.equals("add")){//新增角色
			add(request,response);
		}else if(cmd.equals("initrole")){//初始化权限分配页面
			initrole(request,response);
		}else if(cmd.equals("saveright")){//保存角色权限
			saveright(request,response);
		}else if(cmd.equals("toedit")){//角色初始化修改页面
			toedit(request,response);
		}else if(cmd.equals("edit")){//修改角色功能
			edit(request,response);
		}
	}

	/**
	 * 保存角色权限
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveright(HttpServletRequest request,//保存角色权限(实现接口函数的功能(RoleService/IRoleService))
			HttpServletResponse response) throws IOException, ServletException {
		
		String[] funids = request.getParameterValues("ckrr");
		String roleid = request.getParameter("roleid");
		Integer rtn = service.saveright(roleid, funids);
		if(rtn>0){
			response.sendRedirect(Tools.Basepath(request, response)+"sys/role?cmd=list");//获取基地址
		}else{
			request.setAttribute("msg", "保存角色权限失败");
			request.getRequestDispatcher("/error.jsp").forward(request, response);//转向
		}
	}

	/**
	 * 初始化权限分配页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initrole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Sysrole role = new Sysrole();//实例化一个JAVABEAN(Sysrole)
		role.setRoleid(Integer.parseInt(request.getParameter("roleid")));//调用Sysrole中的函数
		List<SysFunction> list = service.initfunlist(role);//调用IRoleService中的函数(分配权限初始化页面数据)
		request.setAttribute("list",list);
		
		role = service.detail(role);//获取角色详细信息
		request.setAttribute("role", role);
		request.getRequestDispatcher("/sys/role/right.jsp").forward(request, response);
	}

	/**
	 * 新增角色
	 * @param request
	 * @param response
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) {
		
		Sysrole role = new Sysrole();
		try {
			BeanUtils.populate(role, request.getParameterMap());
			//这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
			//通过前台表单中的name值进行获取的，获取到后又进行了一次封装。 之所以返回的map中的value为字符串类型的数组，是为了解决表单中有多个name值一样的项。
			Integer rtn = service.add(role);
			if(rtn>0){
				response.sendRedirect(Tools.Basepath(request, response)+"sys/role?cmd=list");
			}else{
				request.setAttribute("msg", "保存角色失败");
				request.getRequestDispatcher("/sys/role/add.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取角色列表
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sname = request.getParameter("sname");
		Sysrole role = new Sysrole();
		if(sname!=null && !sname.equals("")){
			role.setRolename(sname);
		}
		
		PageControl pc = new PageControl();
		Integer currindex = 1;
		if(request.getParameter("index")!=null){
			currindex = Integer.parseInt(request.getParameter("index"));
		}
		pc.setCurrentindex(currindex);
		//pc.setPagesize(5);
		
		Pager<Sysrole> pager = service.list(role, pc);
		request.setAttribute("pager", pager);
		request.getRequestDispatcher("/sys/role/list.jsp").forward(request, response);
		
	}
	/**
	 * 修改角色功能
	 * @param request
	 * @param response
	 */
	private void edit(HttpServletRequest request, HttpServletResponse response) {
		
		Sysrole role = new Sysrole();
		
		try {
			BeanUtils.populate(role, request.getParameterMap());
			Integer rtn = service.edit(role);
			if(rtn>0){			
				response.sendRedirect(Tools.Basepath(request, response)+"sys/role?cmd=list");
			}else{
				request.setAttribute("msg", "保存角色失败！");
				request.getRequestDispatcher("/sys/role/edit.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 角色初始化修改页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void toedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Sysrole role = new Sysrole();
		role.setRoleid(Integer.parseInt(request.getParameter("id")));
		role = service.detail(role);
		if(role!=null){
			request.setAttribute("item",role);
			request.getRequestDispatcher("/sys/role/edit.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "需要修改的角色功能不存在。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
