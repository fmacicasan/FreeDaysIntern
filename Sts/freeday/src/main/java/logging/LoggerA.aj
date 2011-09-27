package logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
@Aspect
@Component
public class LoggerA {                                                       
	private final Log log = LogFactory.getLog(this.getClass());

	//27.09.2011 fmacicasan : excluded CheckFreeDaySpecificDateConstraint logging to solve the vacation creation issue
	@Around("execution(* freedays..*.*(..)) && !execution(* freedays.validation.CheckFreeDaySpecificDateConstraint.*(..)) && !execution(* get*()) && !execution(* set*(..)) && !execution(* toString*())")
	public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			Object retVal = joinPoint.proceed();


			stopWatch.stop();

			StringBuffer logMessage = new StringBuffer();
			StringBuffer checker = new StringBuffer();
			try{
				logMessage.append(joinPoint.getTarget().getClass().getName());
				logMessage.append(".");
				logMessage.append(joinPoint.getSignature().getName());
				logMessage.append("(");
				// append args
				Object[] args = joinPoint.getArgs();
				for (int i = 0; i < args.length; i++) {
					int oldSize = logMessage.length();
					logMessage.append(args[i]).append(",");
					int newSize = logMessage.length();
					if(newSize - oldSize > 50){
						logMessage.setLength(oldSize+1);
						logMessage.append("2long");
					}
	
				}
				if (args.length > 0) {
					logMessage.deleteCharAt(logMessage.length() - 1);
				}
	
				logMessage.append(")");
				logMessage.append(" execution time: ");
				logMessage.append(stopWatch.getTotalTimeMillis());
				logMessage.append(" ms");
				log.info(logMessage.toString());
			}catch(NullPointerException e){
				//logMessage.append("nothing");
			}
			
			return retVal;
	}
}
