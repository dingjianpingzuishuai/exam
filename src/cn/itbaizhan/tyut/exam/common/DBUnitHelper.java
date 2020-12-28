package cn.itbaizhan.tyut.exam.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DBUnitHelper {

	/**
	 * 鑾峰彇鏁版嵁搴撻摼鎺�
	 * @return
	 */
	public static Connection getConn(){
		Connection conn = null;	
		try {
			
			DbUtils.loadDriver("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/exam?useUnicode=true&characterEncoding=utf8", "root", "root");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Integer executeUpdate(String sql,Object ...objects){
		
		Connection conn = getConn();
		QueryRunner qr = new QueryRunner();
		Integer rtn = 0;
		try {
			if(objects == null){
				rtn = qr.update(conn, sql);
			}else{
				rtn = qr.update(conn, sql, objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return rtn;
	}
	
	public static Integer executeUpdate(String sql){
		return executeUpdate(sql, null);
	}
	
	public static <T> List<T> executeQuery(String sql,Class<T> cls,Object ...objects){
		Connection conn = getConn();
		List<T> list = null;
		try{
			QueryRunner rq = new QueryRunner();
			if(objects == null){
				list = rq.query(conn, sql,new BeanListHandler<T>(cls)); 
			}else{
				list = rq.query(conn, sql,new BeanListHandler<T>(cls),objects); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public static <T> List<T> executeQuery(String sql,Class<T> cls){
		return executeQuery(sql,cls,null);
	}
	
	/**
	 * 甯﹀垎椤电殑鏌ヨ
	 * @param sql SQL璇彞
	 * @param map SQL鍙傛暟
	 * @param pc 鍒嗛〉鎺у埗瀵硅薄锛岄渶瑕佷紶閫掑弬鏁帮細褰撳墠绗嚑椤碉紙currentindex锛�,姣忛〉鏄剧ず澶氬皯琛岋細(pagesize)
	 * 鍒嗛〉鎺т欢鏄剧ず澶氬皯涔燂細showpcount
	 * @return
	 */
	public static <T> Pager<T> execlist(String sql,PageControl pc,Class cls,String pk,Object...object){

		//鑾峰彇鎬昏褰曟暟sql		 
		String sqlcount = "select count(*) as count from ("+sql+") a";
		//鑾峰彇鍏蜂綋鏁版嵁鐨凷QL璇彞
		Integer min = (pc.getCurrentindex()-1)*pc.getPagesize();
		Integer max = pc.getPagesize();
		String sqllist = "select * from ("+sql+") a where a."+pk+" limit "+min+","+max;
		
		Connection conn = getConn();
		Pager<T> pager = new Pager<T>();
		try {
			
			QueryRunner rq = new QueryRunner();//QueryRunner是Dbutils的核心类之一，它显著的简化了SQL查询
			Object count = rq.query(conn, sqlcount, new ScalarHandler<Object>("count"), object);
			//query执行选择查询，在查询中，对象阵列的值被用来作为查询的置换参数。
			List<T> list = executeQuery(sqllist,cls,object);
			//璁剧疆鎬昏褰曟暟
			Integer c = 0;
			if(count!=null){
				c=Integer.parseInt(count.toString());
			}
			pc.setRscount(c);
			
			pager.setList(list);
			pc = dealpage(pc);
			pager.setPagectrl(pc);	
			DbUtils.close(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}					
		return pager;
	}
	/**
	 * 澶勭悊鍒嗛〉鍙傛暟
	 * @param pc
	 * @return
	 */
	private static PageControl dealpage(PageControl pc){
		//鑾峰彇鎬婚〉鏁�
		Integer pagecount = pc.getRscount()/pc.getPagesize();
		if(pc.getRscount()%pc.getPagesize()>0){
			pagecount++;
		}
		pc.setPagecount(pagecount);
		
		//璁＄畻鏈�澶�(鏈�灏�)鏄剧ず椤垫暟
		Integer showpcount = pc.getShowpcount();//鍒嗛〉涓�娆℃樉绀哄灏戦〉
		Integer maxpage = 0;//褰撳墠鏄剧ず鏈�澶ч〉鐮�
		Integer minpage = 0;
		Integer index = pc.getCurrentindex();//褰撳墠绗嚑椤�
		if(pagecount<=showpcount){//褰撴�婚〉鏁板皬浜庣瓑浜庢樉绀虹殑椤垫暟鏃�
			maxpage = pagecount;
			minpage = 1;
		}else{
			Integer buff = showpcount/2; //鍙栦腑闂存暟銆俶axpage=index+buff
			buff = index+buff;
			if(buff<=showpcount){
				maxpage = showpcount;
				minpage = 1;
			}else if(buff<pagecount){
				maxpage = buff;
				minpage = maxpage - showpcount + 1;
				
			}else if(buff>=pagecount){
				maxpage = pagecount;
				minpage = maxpage - showpcount + 1;
			}
		}
		pc.setMaxpage(maxpage);	
		pc.setMinpage(minpage);
		return pc;
	}
	/*public static void main(String args[]) throws SQLException
	{
		Connection con=getConn();
		for(int i=0;i<=700;i++){
		String sql="insert into r values(?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, i);
		pstmt.executeUpdate();
		System.out.println("鎻掑叆鎴愬姛"+i);
		}
	}*/
}
