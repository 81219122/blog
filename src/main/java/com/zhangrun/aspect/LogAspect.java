package com.zhangrun.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/29 13:36
 * 切面操作 处理日志  捕获用户操作的信息
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass()); //拿到日志记录
    /*拦截controller中所有的方法
    * * com.zhangrun.controller.*.*(..) controller包下所有类中所有类（*）的方法*(..)
    * */
    @Pointcut("execution(* com.zhangrun.controller.*.*(..))")
    public void log(){

    }
    /*在切面之前来执行这个方法*/
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //拿到页面请求request
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();  //拿到页面请求的其中
        String ip=request.getRemoteAddr();  //拿到页面请求的ip
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(); //拿到请求的方类名及方法名
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);

        logger.info("-------Request:{}-------",requestLog);
    }
    @After("log()")
    public void doAfter(){
        //logger.info("-------doAfter-------");
    }

    /*方法返回拦截*/
    @AfterReturning(returning = "result",pointcut = "log()")  /*result 对象下面方法的参数result*/
    public void doAfterReturn(Object result){
        logger.info("-------Result:{}-------",result);
    }

    /*内部类 获取页面的url ip*/
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object [] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" + "url='" + url + '\'' + ", ip='" + ip + '\'' + ", classMethod='" + classMethod + '\'' + ", args=" + Arrays.toString(args) + '}';
        }
    }

}
