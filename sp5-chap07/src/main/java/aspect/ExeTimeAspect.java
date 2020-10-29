package aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExeTimeAspect {
	
	@Pointcut("execution(public * chap07..*(..))") //Pointcut : 공통 기능을 적용할 대상 설정(실제 advice가 적용되는 JoinPoint)
	//chap07패키지나 그 하위 패키지에 위치한 public 메서드를 Pointcut으로 설정한다는 의미
	private void publicTarget() { 
		//다른 클래스에 위치한 @Around애노테이션에서 publicTarget()메서드의 Pointcut을 사용하고 싶다면 publicTarget메서드를 public으로 바꾸면 된다.
	}

	@Around("publicTarget()") //Around Advice 설정, publicTarget()메서드에 정의한 Pointcut에 공통 기능을 적용
	//ProcedingJoinPoint타입 파라미터는 프록시 대상 객체의 메서드를 호출할 때 사용한다.
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed(); //proceed 메서드를 사용해서 실제 대상 객체의 메서드를 호출
			// proceed 메서드를 호출하면 대상 객체의 메서드가 실행되므로 이 코드 이전과 이후에 공통 기능을 위한 코드를 위치시키면 되므로
			// 이 코드 위 아래에 시간을 구하는 코드가 있다.(메소드 실행 시간 구하기 위한 것)
			return result;
		} finally {
			long finish = System.nanoTime();
			Signature sig = joinPoint.getSignature();
			System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n",
					joinPoint.getTarget().getClass().getSimpleName(),
					sig.getName(), Arrays.toString(joinPoint.getArgs()),
					(finish - start));
		}
	}

}
