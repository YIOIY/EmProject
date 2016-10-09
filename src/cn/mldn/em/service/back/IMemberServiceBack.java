package cn.mldn.em.service.back;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.em.vo.Member;

public interface IMemberServiceBack {
	/**
	 * 进行角色数据的认证处理
	 * @param mid 要进行权限以及管理员标记的认证处理的mid
	 * @return 返回的内容包括如下信息：<br>
	 * key = allRoles、value = 所有的角色数据；<br>
	 * @throws Exception
	 */
	public Map<String,Object> addPre(String mid) throws Exception ;
	/**
	 * 做mid的数据检测使用，主要为了ajax异步验证使用
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	public Member get(String umid,String mid) throws Exception ;
	/**
	 * 实现管理员数据的添加操作处理，包括如下的操作：<br>
	 * 1、需要进行管理员标记的检测以及权限的检测；<br>
	 * 2、要进行mid的重复的检测；<br>
	 * 3、要向用户表中保存数据；<br>
	 * 4、要向member_role关系表中保存数据。<br>
	 * @param mid 用于权限验证使用
	 * @param vo
	 * @return 
	 * @throws Excepiton
	 */
	public boolean add(String mid,Member vo,Set<Integer> rids) throws Exception ;
	/**
	 * 进行全部管理员的列表显示
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	public List<Member> list(String mid) throws Exception ; 
	/**
	 * 实现用户的登录处理，在登录处理之中需要进行如下的功能操作：<br>
	 * 1、实现基本的用户名和密码检测，检测成功之后需要取出mid（登录检测）和name信息进行前台显示；<br>
	 * 2、要根据用户查询出所有的角色信息；<br>
	 * 3、要根据用户查询出所有对应的权限信息。<br>
	 * @param vo 包含有用户输入的用户名和密码
	 * @return 要返回有如下数据内容：<br>
	 * key = flag 、value = true | false，表示登录成功或失败的标记；<br>
	 * key = sflag、value = 1 | 0，表示当前登录的账户是否是超级管理员；<br>
	 * key = name、value = 真实姓名，表示当前管理员的真实姓名，此信息要在界面上显示；<br>
	 * key = allRoles、value = Set，保存所有的角色数据；<br>
	 * key = allActions、value = Set，保存所有的权限数据；<br>
	 * @throws Exception 
	 */
	public Map<String,Object> login(Member vo) throws Exception ;
}
