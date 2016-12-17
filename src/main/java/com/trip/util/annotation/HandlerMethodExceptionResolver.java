package com.trip.util.annotation;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 * 视图与JSON结合异常处理,有@ResponseBody方法异常，输出JSON，无@ResponseBody方法异常，输出错误页面
 * @author wangxichun
 *
 */
public class HandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver   {
//	public class AnnotationHandlerMethodExceptionResolver implements HandlerExceptionResolver {
	private String defaultErrorView;
	
	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

//	  @Override  
//	 public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {  
//	        logger.error("Catch Exception: ",ex);//把漏网的异常信息记入日志  
//	        Map<String,Object> map = new HashMap<String,Object>();  
//	        StringPrintWriter strintPrintWriter = new StringPrintWriter();  
//	        ex.printStackTrace(strintPrintWriter);  
//	        map.put("errorMsg", strintPrintWriter.getString());//将错误信息传递给view  
////	        return new ModelAndView(defaultErrorView,map);
//	        return new ModelAndView(new RedirectView(defaultErrorView),"errorMsg",map);
//	 }  
	
	@Override
	public ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
		if (handlerMethod == null) {
			return null;
		}
		
		Method method = handlerMethod.getMethod();

		if (method == null) {
			return null;
		}
		ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		if (responseBodyAnn != null) {
			try {
				ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(method, ResponseStatus.class);
				if (responseStatusAnn != null) {
					HttpStatus responseStatus = responseStatusAnn.value();
					String reason = responseStatusAnn.reason();
					if (!StringUtils.hasText(reason)) {
						response.setStatus(responseStatus.value());
					} else {
						try {
							response.sendError(responseStatus.value(), reason);
						} catch (IOException e) { }
					}
				}
				return handleResponseBody(returnValue, request, response);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		if(returnValue.getViewName() == null){
//			 mav.getModelMap().put("message", "操作出现未知异常！");
//			returnValue.setView(new RedirectView(defaultErrorView));
			returnValue.setViewName("forward:/"+defaultErrorView);
		}
		return returnValue;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private ModelAndView handleResponseBody(ModelAndView returnValue, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map value = returnValue.getModelMap();
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		Class<?> returnValueType = value.getClass();
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		if (messageConverters != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {
				for (HttpMessageConverter messageConverter : messageConverters) {
					if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
						messageConverter.write(value, acceptedMediaType, outputMessage);
						return new ModelAndView();
					}
				}
			}
		}
		if (logger.isWarnEnabled()) {
			logger.warn("Could not find HttpMessageConverter that supports return type [" + returnValueType + "] and " + acceptedMediaTypes);
		}
		return null;
	}

}
