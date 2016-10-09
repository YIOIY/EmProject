package cn.mldn.em.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.mldn.util.ResourceUtils;
@WebFilter("/pages/*")
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req ;
		HttpSession session = request.getSession() ;
		if (session.getAttribute("mid") != null) {	// 已经登录过了
			chain.doFilter(req, resp); 
		} else {	// 应该跳转到错误页，进行信息展示
			String msg = ResourceUtils.get("Messages", "login.check.failure.msg") ;
			String forp = ResourceUtils.get("Pages", "forward.page") ;
			String url = ResourceUtils.get("Pages", "login.page") ;
			req.setAttribute("msg", msg);
			req.setAttribute("url", url);
			req.getRequestDispatcher(forp).forward(req, resp);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
