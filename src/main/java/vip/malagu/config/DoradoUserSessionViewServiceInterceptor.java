package vip.malagu.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import com.bstek.dorado.common.proxy.PatternMethodInterceptor;
import com.bstek.dorado.view.resolver.ClientRunnableException;

/**
 * 超时处理
 * @author Lynn -- 2020年5月11日 下午2:40:46
 */
@Component
public class DoradoUserSessionViewServiceInterceptor extends PatternMethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (Exception e) {
			throw new ClientRunnableException("window.tip('连接超时,请刷新页面');");
		}
	}

}
