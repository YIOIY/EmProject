package cn.mldn.em.service.back;

import cn.mldn.em.vo.Level;

public interface ILevelServiceBack {
	/**
	 * 取得一个雇员级别的完整信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Level get(int id) throws Exception ;
}
