package cn.mldn.em.service.back.impl;

import cn.mldn.em.dao.ILevelDAO;
import cn.mldn.em.dao.impl.LevelDAOImpl;
import cn.mldn.em.service.back.ILevelServiceBack;
import cn.mldn.em.vo.Level;
import cn.mldn.util.dao.AbstractDAO;
import cn.mldn.util.factory.DAOFactory;

public class LevelServiceBackImpl extends AbstractDAO implements ILevelServiceBack {

	@Override
	public Level get(int id) throws Exception {
		ILevelDAO levelDAO = DAOFactory.getInstance(LevelDAOImpl.class);
		return levelDAO.findById(id); 
	}

}
