package cn.mldn.em.servlet.back;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import cn.mldn.em.service.back.IMemberServiceBack;
import cn.mldn.em.service.back.impl.MemberServiceBackImpl;
import cn.mldn.em.servlet.abs.EMServlet;
import cn.mldn.em.vo.Member;
import cn.mldn.util.EncryptUtil;
import cn.mldn.util.factory.ServiceFactory;

@SuppressWarnings("serial")
@WebServlet("/pages/back/member/MemberServletBack/*")
public class MemberServletBack extends EMServlet {
	private Member member = new Member() ;
	public Member getMember() {
		return member;
	} 
	public String addPre() {
		if (super.admin()) {
			IMemberServiceBack memberService = ServiceFactory.getInstance(MemberServiceBackImpl.class);
			try {
				Map<String, Object> map = memberService.addPre(super.getMid());
				super.request.setAttribute("allRoles2", map.get("allRoles"));
			} catch (Exception e) { 
				e.printStackTrace();
			}
			return "member.add.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}

	public void checkMid() {
		if (super.admin()) {
			String mid = super.getStringParameter("mid") ;
			IMemberServiceBack memberService = ServiceFactory.getInstance(MemberServiceBackImpl.class);
			try {
				Member vo = memberService.get(super.getMid(), mid) ;
				super.print(vo == null); 	// 为空表示可用 
			} catch (Exception e) {
				e.printStackTrace();
				super.print(false);
			}
		} else {
			super.print(false);
		}
	}

	public String add() {
		if (super.admin()) {
			String rids [] = super.request.getParameterValues("rid") ;	// 所有的角色数据
			Set<Integer> allRids = new HashSet<Integer>() ;
			try {
				for (int x = 0; x < rids.length; x++) {
					allRids.add(Integer.parseInt(rids[x])) ;
				}
			} catch (Exception e) {}
			IMemberServiceBack memberService = ServiceFactory.getInstance(MemberServiceBackImpl.class);
			this.member.setPassword(EncryptUtil.getPwd(this.member.getPassword()));
			try {
				if (memberService.add(super.getMid(), this.member, allRids)) {
					super.setUrlAndMsg("member.add.servlet", "vo.add.success.msg");
				} else {
					super.setUrlAndMsg("member.add.servlet", "vo.add.failure.msg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setUrlAndMsg("member.add.servlet", "vo.add.failure.msg");
			}
			return "forward.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	} 

	public String list() {
		if (super.auth("member:list")) { // 判断当前用户是否具备有指定的权限
			IMemberServiceBack memberService = ServiceFactory.getInstance(MemberServiceBackImpl.class);
			try {
				super.request.setAttribute("allMembers", memberService.list(super.getMid()));
			} catch (Exception e) {
				e.printStackTrace();
				return "error.page"; // 回到错误页上
			}
			return "member.list.page";
		} else {
			super.addError("auth", "auth.failure.msg");
			return "error.page"; // 回到错误页上
		}
	}
	@Override
	public String getType() {
		return "管理员";
	}
}
