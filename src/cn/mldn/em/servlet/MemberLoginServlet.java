package cn.mldn.em.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;

import cn.mldn.em.service.back.IMemberServiceBack;
import cn.mldn.em.service.back.impl.MemberServiceBackImpl;
import cn.mldn.em.vo.Member;
import cn.mldn.util.EncryptUtil;
import cn.mldn.util.factory.ServiceFactory;
import cn.mldn.util.servlet.DispatcherServlet;

@SuppressWarnings("serial")
@WebServlet("/MemberLoginServlet/*")
public class MemberLoginServlet extends DispatcherServlet {
	private Member member = new Member();

	public Member getMember() {
		return member;
	}
	
	public String logout() {
		super.getSession().invalidate(); 	// 让session失效
		super.setUrlAndMsg("login.page", "logout.msg");
		return "forward.page" ;
	} 

	public String login() {
		this.member.setPassword(EncryptUtil.getPwd(this.member.getPassword())); // 需要针对于密码进行MD5的加密处理
		IMemberServiceBack memberServiceBack = ServiceFactory.getInstance(MemberServiceBackImpl.class);
		try {
			Map<String,Object> result = memberServiceBack.login(this.member) ;
			boolean flag = (Boolean) result.get("flag") ;
			if (flag) { // 登录检测
				super.setSessionAttribute("mid", this.member.getMid());
				super.setSessionAttribute("name", result.get("name"));
				super.setSessionAttribute("sflag", result.get("sflag"));
				super.setSessionAttribute("allRoles", result.get("allRoles"));
				super.setSessionAttribute("allActions", result.get("allActions"));
				super.setUrlAndMsg("index.page", "login.success.msg");
			} else { // 登录失败
				super.setUrlAndMsg("login.page", "login.failure.msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	} 

	@Override
	public String getDefaultColumn() {
		return null;
	}

	@Override
	public String getUploadDir() {
		return null;
	}

	@Override
	public String getType() {
		return null;
	}

}
