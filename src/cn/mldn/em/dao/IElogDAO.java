package cn.mldn.em.dao;

import java.sql.SQLException;
import java.util.List;

import cn.mldn.em.vo.Elog;
import cn.mldn.util.dao.IDAO;

public interface IElogDAO extends IDAO<Integer, Elog> {
	/**
	 * 根据雇员编号查询一个雇员的详细日志记录
	 * @param empno
	 * @return
	 * @throws SQLException
	 */
	public List<Elog> findAllByEmp(Integer empno) throws SQLException ;
}
