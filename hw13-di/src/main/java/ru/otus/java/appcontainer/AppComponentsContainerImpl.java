package ru.otus.java.appcontainer;

import lombok.extern.slf4j.Slf4j;
import ru.otus.java.appcontainer.api.AppComponent;
import ru.otus.java.appcontainer.api.AppComponentsContainer;
import ru.otus.java.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Slf4j
public class AppComponentsContainerImpl implements AppComponentsContainer {
    private Class<?> configClass;
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        this.configClass = configClass;
        findComponentMethodsInConfigClass();
    }

    private void findComponentMethodsInConfigClass() {
        Method[] methodsPublic = configClass.getDeclaredMethods();
        List<Method> methodsAppComponent = new ArrayList<>();
        Arrays.stream(methodsPublic).forEach(method -> {
            try {
                if (configClass.getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(AppComponent.class)) {
                    methodsAppComponent.add(method);
                }
            } catch (NoSuchMethodException e) {
                log.debug(e.getLocalizedMessage());
            }
        });

        saveComponents(methodsAppComponent);
    }

    private void saveComponents(List<Method> methodsAppComponent) {
        methodsAppComponent.stream()
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    Parameter[] methodParameters = method.getParameters();
                    Object[] arg = getObjectToParameters(methodParameters);
                    initComponentObject(method, arg);
                });
    }

    private Object[] getObjectToParameters(Parameter[] methodParameters) {
        List<Object> arg = new ArrayList<>();
        for (Parameter param : methodParameters) {
            Class<?> paramClazz = param.getType();
            Optional<Object> objectInComponent = getObjectFromComponent(paramClazz);
            objectInComponent.ifPresent(arg::add);
        }
        return arg.toArray();
    }

    private Optional<Object> getObjectFromComponent(Class<?> clazz) {
        return appComponents.stream()
                .filter(obj -> obj.getClass().equals(clazz)
                        || (clazz.isInterface()
                        && clazz.isAssignableFrom(obj.getClass()))).findFirst();
    }

    private void initComponentObject(Method method, Object... arg) {
        try {
            Object configObject = initConfigObject();
            Object objComponent = method.invoke(configObject, arg);
            putObjectToComponents(method, objComponent);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            log.debug(e.getLocalizedMessage());
        }
    }

    private void putObjectToComponents(Method method, Object objComponent) {
        appComponents.add(objComponent);
        appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), objComponent);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        Optional<Object> component = getObjectFromComponent(componentClass);
        return (C) component.orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.getOrDefault(componentName, null);
    }

    private Object initConfigObject() throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return configClass.getDeclaredConstructor().newInstance();
    }
}
