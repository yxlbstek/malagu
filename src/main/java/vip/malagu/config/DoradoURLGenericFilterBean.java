
package vip.malagu.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


//@Component
public class DoradoURLGenericFilterBean implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest)request;
//	    HttpServletResponse res = (HttpServletResponse)response;
//	    String orgId = MultitenantUtils.getLoginOrgId();
//	    if (orgId != null && !orgId.equals("master")) {
//	    	if (req.getServletPath().indexOf("view.company.CompanyMaintain.d") != -1) {
//				res.sendRedirect("/Main.d");
//	    	}
//	    	if (req.getServletPath().indexOf("view.OrganizationMaintain.d") != -1) {
//	    		res.sendRedirect("/Main.d");
//	    	}
//	    }
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

}
