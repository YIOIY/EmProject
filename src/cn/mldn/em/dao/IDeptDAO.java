package cn.mldn.em.dao;

import java.sql.SQLException;
import java.util.List;

import cn.mldn.em.vo.Dept; 
import cn.mldn.util.dao.IDAO;

public interface IDeptDAO extends IDAO<Integer, Dept> {
	/**
	 * 修改部门的上限信息
	 * @param vo 包含有deptno和maxnum的内容 
	 * @return 修改成功返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean doUpdateMaxnum(Dept vo) throws SQLException ;
	/**
	 * 列出有空余人数的所有部门数据，这样可以在雇员添加的时候进行验证处理
	 * @return
	 * @throws SQLException
	 */
	public List<Dept> findAllByEmpty() throws SQLException ;
	/**
	 * 必须包含有指定的部门编号的数据显示。
	 * @param deptno
	 * @return
	 * @throws SQLException
	 */
	public List<Dept> findAllByEmpty(Integer deptno) throws SQLException ;
	/**
	 * 进行部门当前人数的更新处理操作
	 * @param eid 雇员编号
	 * @param num 要修改的人员的数量（currnum）
	 * @return
	 * @throws SQLException
	 */
	public boolean doUpdateCurrnumByEmp(Integer eid,Integer num) throws SQLException ;
	/**
	 * 进行部门当前人数的更新处理操作
	 * @param id 部门编号
	 * @param num 要修改的人员的数量（currnum）
	 * @return
	 * @throws SQLException
	 */
	public boolean doUpdateCurrnum(Integer id,Integer num) throws SQLException ;
} 
