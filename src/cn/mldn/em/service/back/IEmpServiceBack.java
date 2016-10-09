package cn.mldn.em.service.back;

import java.util.Map;
import java.util.Set;

import cn.mldn.em.vo.Elog;
import cn.mldn.em.vo.Emp;

public interface IEmpServiceBack {
	/**
	 * 员工批量离职处理操作
	 * @param mid
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean removeEmp(String mid,Set<Integer> ids) throws Exception ;
	/**
	 * 显示一个雇员的详细信息
	 * @param eid 雇员编号
	 * @return 包含有如下的返回记录：<br>
	 * 1、key = emp、value = 雇员信息；<br>
	 * 2、key = dept、value = 部门信息；<br>
	 * 3、key = level、value = 职位等级信息；<br>
	 * 4、key = allElogs、value = 日志列表信息。<br>
	 * @throws Exception
	 */
	public Map<String,Object> getDetails(String mid,int eid) throws Exception ;
	
	/**
	 * 进行数据的分页列表显示，会根据column和keyword的内容来选择调用不同的方法；
	 * @param mid
	 * @param flag
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return 返回的内容包括如下组成：<br>
	 * 1、key = allEmps、value = 雇员信息集合；<br>
	 * 2、key = empCount、value = 雇员人数统计；<br>
	 * @throws Exception
	 */
	public Map<String, Object> listByFlag(String mid, int flag, String column, String keyWord, int currentPage,
			int lineSize) throws Exception; 
	/**
	 * 在雇员修改前进行相关信息列出，包括如下内容：<br>
	 * 1、需要列出所有的部门信息，利用IDeptDAO.findAllEmpty()方法；<br>
	 * 2、需要列出所有的工资等级数据，利用ILevelDAO.findAll()方法；<br>
	 * 3、查询出指定编号的雇员数据，利用IEmpDAO.findById()方法；<br>
	 * @return 返回的内容包含有如下的结果：<br>
	 * 1、key = allLevels、value = 所有的级别数据；<br>
	 * 2、key = allDepts、value = 所有的部门数据；<br>
	 * 3、key = emp、value = 指定编号的雇员信息。<br>
	 * @throws Exception
	 */
	public Map<String,Object> editPre(String mid,int empno) throws Exception ;
	/**
	 * 实现雇员信息的修改处理，要执行如下功能：<br>
	 * 1、首先需要查询出该雇员的原始数据，这样好确定工资的涨幅、部门是否变更；<br>
	 * 2、如果部门变更了，那么原始的部门的人数需要减1，新的部门人数需要加1；<br>
	 * 3、进行雇员数据的变更处理，调用doUpdate()方法；
	 * 4、进行日志的保存处理。
	 * @param vo
	 * @param log
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Emp vo,Elog log) throws Exception ;
	/**
	 * 在雇员追加时进行相关信息列出，包括如下内容：<br>
	 * 1、需要列出所有的部门信息，利用IDeptDAO.findAllEmpty()方法；<br>
	 * 2、需要列出所有的工资等级数据，利用ILevelDAO.findAll()方法；<br>
	 * @return 返回的内容包含有如下的结果：<br>
	 * 1、key = allLevels、value = 所有的级别数据；<br>
	 * 2、key = allDepts、value = 所有的部门数据；<br>
	 * @throws Exception
	 */
	public Map<String, Object> addPre(String mid) throws Exception;
	/**
	 * 实现雇员数据的追加控制操作，本操作要进行如下的调用：<br>
	 * 1、判断当前用户是否具备有相应的权限信息；
	 * 2、要判断当前的部门是否有空余的名额；<br>
	 * 3、要判断当前的工资是否在指定的级别范围之内；<br>
	 * 4、要进行雇员信息的保存；<br>
	 * 5、保存成功之后要进行部门人数加1修改；<br>
	 * 6、要进行相关日志的保存处理；<br>
	 * @param vo 包含有雇员数据以及追加的用户的mid数据
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean add(Emp vo,Elog log) throws Exception;
}
