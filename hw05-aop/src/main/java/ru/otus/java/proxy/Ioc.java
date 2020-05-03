package ru.otus.java.proxy;

import ru.otus.java.annotations.Log;
import ru.otus.java.test.TestLoggingImpl;
import ru.otus.java.test.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

public class Ioc {

    private Ioc() {
    }

    public static TestLoggingInterface createTestClass() {
        InvocationHandler handler = new ProxyInvocationHandler(new TestLoggingImpl());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class ProxyInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testClass;

        ProxyInvocationHandler(TestLoggingImpl testClass) {
            this.testClass = testClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Stream.of(testClass.getClass().getDeclaredMethods())
                    .filter(x -> x.getName().equals(methodName) && x.isAnnotationPresent(Log.class))
                    .findFirst()
                    .ifPresent(implMethod ->
                            {
                                System.out.print("executed method: " + methodName);
                                if (args != null) {
                                    StringBuilder paramOfMethod = new StringBuilder();
                                    for (Object param : args) {
                                        paramOfMethod.append(" [").append(param.toString()).append("]");
                                    }
                                    System.out.print(", param:" + paramOfMethod);
                                }
                                System.out.print("\n");
                            }
                    );
            return method.invoke(testClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + testClass +
                    '}';
        }
    }
}
