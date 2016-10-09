package cn.mldn.em.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import cn.mldn.em.vo.Emp;
import cn.mldn.util.dao.IDAO;

public interface IEmpDAO extends IDAO<Integer, Emp> {
	/**
	 * 根据部门编号进行雇员数据的分页列表显示
	 * @param deptno 部门编号
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws SQLException
	 */
	public List<Emp> findAllByDept(Integer deptno, Integer currentPage, Integer lineSize) throws SQLException;
	/**
	 * 统计一个部门中的雇员人数
	 * @param deptno
	 * @return
	 * @throws SQLException
	 */
	public Integer getAllCountByDept(Integer deptno) throws SQLException ; 
	
	public List<Emp> findAllByFlag(Integer flag, String column, String keyWord, Integer currentPage, Integer lineSize)
			throws SQLException;

	public List<Emp> findAllByFlag(Integer flag, Integer currentPage, Integer lineSize) throws SQLException;

	public Integer getAllCountByFlag(Integer flag, String column, String keyWord) throws SQLException;

	public Integer getAllCountByFlag(Integer flag) throws Exception;
	/**
	 * 员工的批量离职处理
	 * @param ids
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateFlag(Set<Integer> ids,Integer flag) throws Exception ;
} 
