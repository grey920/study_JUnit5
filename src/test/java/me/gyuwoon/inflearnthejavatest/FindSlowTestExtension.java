package me.gyuwoon.inflearnthejavatest;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final long THRESHOLD = 1000L; // 1초

    public FindSlowTestExtension(long l) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        // 시작시간이 현재 시간이라는 것을 store에 넣어둠
        store.put("START_TIME", System.currentTimeMillis());
    }
    
    private ExtensionContext.Store getStore(ExtensionContext context){
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        // 클래스 네임과 메소드 네임을 조합해서 create 메소드에 들어갈 parts를 만든다.
        return context.getStore(Namespace.create(testClassName, testMethodName)); // create 팩토리메소드로 만든 파라미터로 Namespace를 받는다. 
          
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod(); //자바의 reflection
        // slow 어노테이션 가져오기
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);
        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(context); 
        // store에서 꺼내고 나서 지우고 값을 long타입으로 start_time을 가져올 수 있다. 
         long start_time = store.remove("START_TIME", long.class);
         //현재 시간 - 걸린 시간 
         long duration = System.currentTimeMillis() - start_time;
         // 일정시간 이상 (1초 이상) 걸리고 @Slow 어노테이션이 없으면 메시지를 출력한다
         if(duration > THRESHOLD && annotation == null) {
             System.out.printf("Please consider mark method [%s] with @slowTest. \n", testMethodName);
         }
         
    }

}
