package cn.mldn.em.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.em.dao.IActionDAO;
import cn.mldn.em.dao.IMemberDAO;
import cn.mldn.em.dao.IRoleDAO;
import cn.mldn.em.dao.impl.ActionDAOImpl;
import cn.mldn.em.dao.impl.MemberDAOImpl;
import cn.mldn.em.dao.impl.RoleDAOImpl;
import cn.mldn.em.service.abs.AbstractService;
import cn.mldn.em.service.back.IMemberServiceBack;
import cn.mldn.em.vo.Member;
import cn.mldn.util.factory.DAOFactory;

public class MemberServiceBackImpl extends AbstractService implements IMemberServiceBack {
	@Override
	public Map<String, Object> addPre(String mid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (super.admin(mid)) {
			IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
			map.put("allRoles", roleDAO.findAll());
		}
		return map;
	}

	@Override
	public boolean add(String mid, Member vo,Set<Integer> rids) throws Exception {
		if (super.admin(mid)) { 
			if (rids == null || rids.size() == 0) {
				return false ;
			}
			IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
			Member member = memberDAO.findById(vo.getMid()) ;
			if (member == null) {	// 现在这个用户名不存在，表示可以使用
				vo.setSflag(0);  
				if (memberDAO.doCreate(vo)) {	// 保存用户信息
					IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class) ;
					return roleDAO.doCreateMemberAndRole(vo.getMid(), rids) ;
				}
			}
		}
		return false;
	} 

	@Override
	public Member get(String umid, String mid) throws Exception {
		if (super.admin(umid)) {
			IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
			return memberDAO.findById(mid);
		}
		return null;
	}

	@Override
	public List<Member> list(String mid) throws Exception {
		List<Member> all = new ArrayList<Member>();
		if (super.auth(mid, "member:list")) {
			IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
			return memberDAO.findAll();
		}
		return all;
	}

	@Override
	public Map<String, Object> login(Member vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IMemberDAO memberDAO = DAOFactory.getInstance(MemberDAOImpl.class);
		Member ckVo = memberDAO.findById(vo.getMid()); // 根据用户的ID取得用户完整数据
		if (ckVo != null) {
			if (ckVo.getPassword().equals(vo.getPassword())) { // 现在密码验证通过
				IRoleDAO roleDAO = DAOFactory.getInstance(RoleDAOImpl.class);
				IActionDAO actionDAO = DAOFactory.getInstance(ActionDAOImpl.class);
				map.put("flag", true); // 存储登录成功的标志位
				map.put("name", ckVo.getName()); // 将真实姓名取出
				map.put("sflag", ckVo.getSflag()); // 保存管理员标志
				map.put("allRoles", roleDAO.findAllByMember(vo.getMid()));
				map.put("allActions", actionDAO.findAllByMember(vo.getMid()));
			} else {
				map.put("flag", false); // 存储登录成功的标志位
			}
		} else {
			map.put("flag", false); // 存储登录成功的标志位
		}
		return map;
	}
}
