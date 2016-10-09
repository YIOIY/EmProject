package cn.mldn.em.service.back;

import java.util.List;
import java.util.Map;

import cn.mldn.em.vo.Dept;

public interface IDeptServiceBack {
	/**
	 * 进行一个部门中所有雇员信息的分页列表显示
	 * @param deptno
	 * @param currentPage
	 * @param lineSize
	 * @return 返回的内容包括如下数据：<br>
	 * 1、key = allEmps、value = 所有的雇员列表；<br>
	 * 2、key = empCount、value = 雇员的数量信息。<>
	 * @throws Exception 
	 */
	public Map<String, Object> listEmpByDept(String mid,int deptno, int currentPage, int lineSize) throws Exception;
	/**
	 * 根据部门的编号查询出部门的基本信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Dept get(int id) throws Exception ;
	/**
	 * 列出全部的部门信息
	 * @param mid 描述的是操作用户，主要是进行权限认证使用
	 * @return
	 * @throws Exception
	 */
	public List<Dept> list(String mid) throws Exception ;
	/**
	 * 进行部门人数上限的更新处理，在更新的时候需要进行如下的操作：<br>
	 * 1、判断要更新人数的部门的当前人数是否小于等于新的部门上限；<br>
	 * 2、如果判断成功，那么则进行部门上限的修改。<br>
	 * @param mid
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean editMaxnum(String mid,Dept vo) throws Exception ;
}
