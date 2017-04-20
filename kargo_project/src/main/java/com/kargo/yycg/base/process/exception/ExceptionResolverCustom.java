package com.kargo.yycg.base.process.exception;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kargo.yycg.base.pojo.vo.ActiveUser;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.DataGridResultInfo;
import com.kargo.yycg.base.process.result.ExceptionResultInfo;
import com.kargo.yycg.base.process.result.ResultInfo;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.service.SystemConfigService;
import com.kargo.yycg.common.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * <p>
 * Title: ExceptionResolverCustom
 * </p>
 * <p>
 * Description:全局异常处理器
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 苗润土
 * @date 2014年11月26日下午5:36:56
 * @version 1.0
 */
public class ExceptionResolverCustom implements HandlerExceptionResolver {
	private static Logger logger = LoggerFactory.getLogger(ExceptionResolverCustom.class);
	// json转换器
	// 将异常信息转json
	private HttpMessageConverter jsonMessageConverter;

	// 前端控制器调用此方法执行异常处理
	// handler，执行的action类就包装了一个方法（对应url的方法）

	@Autowired
	private SystemConfigService managerSystemConfig;
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();

		if (method == null) {
			return null;
		}
		//如果返回结果为数据列表进行异常处理，需要将结果数据格式化为jqueryeasyui需要的格式。
		if(method.getReturnType() == DataGridResultInfo.class){
			try {
				return handleDataGridResultInfo(ex, request, response);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);
		if (responseBodyAnn != null) {
			try {
				return handleResponseBody(ex, request, response);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}


		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.setViewName(Config.ERROR_PAGE);
		//ResultInfo resultInfo = null;
		ExceptionResultInfo resultInfo = null;
		if(ex instanceof ExceptionResultInfo){
			//resultInfo = ((ExcepResultInfo) ex).getResultInfo();
			resultInfo = (ExceptionResultInfo) ex;

			if(request.getSession().getAttribute(Config.ACTIVEUSER_KEY) != null){//记录异常日志
				ActiveUser activeUser = (ActiveUser)request.getSession().getAttribute(Config.ACTIVEUSER_KEY);
				try {
					managerSystemConfig.saveFailLog(activeUser.getUserid(), activeUser.getUsername(), IpUtil.getClientIp(request), resultInfo.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info(e.getMessage());
				}
			}
			int messageCode = resultInfo.getResultInfo().getMessageCode();
			if(messageCode == 105 ){//无权限错误代码
				modelAndView.setViewName(Config.REFUSE_PAGE);
			}else if(messageCode == 106){//登录页面
				modelAndView.setViewName(Config.LOGIN_PAGE);
			}

		}else{
			logger.info(ex.getMessage());
			ex.printStackTrace();
			//构造resultInfo
			//resultInfo = ResultInfo.createFail("001");
			resultInfo = new ExceptionResultInfo(ResultUtil.createFail(Config.MESSAGE,900,null));
		}
		modelAndView.addObject(Config.RESULTINFO_KEY, resultInfo);

		return modelAndView;
	}

	// 异常信息解析方法
	private ExceptionResultInfo resolveExceptionCustom(Exception ex) {
		ResultInfo resultInfo = null;
		if (ex instanceof ExceptionResultInfo) {
			// 抛出的是系统自定义异常
			resultInfo = ((ExceptionResultInfo) ex).getResultInfo();
		} else {
			// 重新构造“未知错误”异常
			resultInfo = new ResultInfo();
			resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
			resultInfo.setMessage("未知错误！");
		}

		return new ExceptionResultInfo(resultInfo);

	}

	// 将异常信息转json输出
	private ModelAndView resolveJsonException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		// 解析异常
		ExceptionResultInfo exceptionResultInfo = resolveExceptionCustom(ex);
		
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		
		try {
			//将exceptionResultInfo对象转成json输出
			jsonMessageConverter.write(exceptionResultInfo, MediaType.APPLICATION_JSON, outputMessage);
		} catch (HttpMessageNotWritableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return new ModelAndView();

	}
	private ModelAndView handleDataGridResultInfo(Exception ex,
												  HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ResultInfo value =  ((ExcepResultInfo) ex).getResultInfo();
		if(!(ex instanceof ExceptionResultInfo)){
			logger.info(ex.getMessage());
			ex.printStackTrace();
			ex = new ExceptionResultInfo(ResultUtil.createFail(Config.MESSAGE,900,null));
		}
		ExceptionResultInfo value = (ExceptionResultInfo) ex;
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo(value.getResultInfo());
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				response);
		jsonMessageConverter.write(dataGridResultInfo, MediaType.APPLICATION_JSON,
				outputMessage);
		return new ModelAndView();

	}
	private ModelAndView handleResponseBody(Exception ex,
											HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ResultInfo value =  ((ExcepResultInfo) ex).getResultInfo();
		if(!(ex instanceof ExceptionResultInfo)){
			logger.info(ex.getMessage());
			ex.printStackTrace();
			ex = new ExceptionResultInfo(ResultUtil.createFail(Config.MESSAGE,900,null));
		}
		ExceptionResultInfo value = (ExceptionResultInfo) ex;
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders()
				.getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				response);
		Class<?> returnValueType = value.getClass();

		if (jsonMessageConverter != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {

				if (jsonMessageConverter.canWrite(returnValueType,
						acceptedMediaType)) {
					jsonMessageConverter.write(value, acceptedMediaType,
							outputMessage);
					return new ModelAndView();
				}
			}
		}


		return null;
	}
	public HttpMessageConverter getJsonMessageConverter() {
		return jsonMessageConverter;
	}

	public void setJsonMessageConverter(
			HttpMessageConverter jsonMessageConverter) {
		this.jsonMessageConverter = jsonMessageConverter;
	}

}
