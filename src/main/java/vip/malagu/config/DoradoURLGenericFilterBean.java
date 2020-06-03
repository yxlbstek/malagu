
package vip.malagu.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;


@Component
public class DoradoURLGenericFilterBean implements Filter {
	
	/**
	 *   HttpServletRequest req = (HttpServletRequest)request;
	 *   HttpServletResponse res = (HttpServletResponse)response;
	 *   String orgId = MultitenantUtils.getLoginOrgId();
	 *   if (orgId != null && !orgId.equals("master")) {
	 *   	if (req.getServletPath().indexOf("view.company.CompanyMaintain.d") != -1) {
	 *			res.sendRedirect("/Main.d");
	 *   	}
	 *   	if (req.getServletPath().indexOf("view.OrganizationMaintain.d") != -1) {
	 *   		res.sendRedirect("/Main.d");
	 *   	}
	 *   }
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 暂无需实现
	}

	@Override
	public void destroy() {
		// 暂无需实现
	}

	

}
