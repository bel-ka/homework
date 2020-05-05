package ru.otus.java.proxy;

import ru.otus.java.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Ioc {

    private Ioc() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T createTestClass(T object) {
        InvocationHandler handler = new ProxyInvocationHandler(object);
        Class<?>[] interfaces = object.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                interfaces, handler);
    }

    static class ProxyInvocationHandler implements InvocationHandler {
        private final Object invokeClass;
        private List<String> methodsWithLogAnnotation = new ArrayList<>();

        ProxyInvocationHandler(Object invokeClass) {
            this.invokeClass = invokeClass;

            Stream.of(invokeClass.getClass().getDeclaredMethods())
                    .filter(x -> x.isAnnotationPresent(Log.class))
                    .forEach(x -> methodsWithLogAnnotation.add(getMethodNameAndParams(x)));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String invokeMethodAndParam = getMethodNameAndParams(method);
            if (methodsWithLogAnnotation.contains(invokeMethodAndParam)) {
                System.out.print("executed method: " + method.getName());
                if (args != null) {
                    StringBuilder paramOfMethod = new StringBuilder();
                    for (Object param : args) {
                        paramOfMethod.append(" [").append(param.toString()).append("]");
                    }
                    System.out.print(", param:" + paramOfMethod);
                }
                System.out.print("\n");
            }
            return method.invoke(invokeClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + invokeClass +
                    '}';
        }
    }

    private static String getMethodNameAndParams(Method method) {
        StringBuilder methodString = new StringBuilder(method.getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            methodString.append("(");
            StringJoiner sj = new StringJoiner(",");
            for (Class<?> parameterType : parameterTypes) {
                sj.add(parameterType.getTypeName());
            }
            methodString.append(sj.toString());
            methodString.append(")");
        }
        return methodString.toString();
    }
}
