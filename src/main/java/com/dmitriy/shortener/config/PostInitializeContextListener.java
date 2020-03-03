package com.dmitriy.shortener.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PostInitializeContextListener {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @EventListener
    @SneakyThrows
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName == null) {
                continue;
            }

            Class<?> beanClass = Class.forName(beanClassName);
            Method[] methods = beanClass.getMethods();
            for(Method method : methods) {
                if (method.isAnnotationPresent(PostInitialize.class)) {
                    Object bean = context.getBean(name);
                    Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                    currentMethod.invoke(bean);
                }
            }
        }
    }
}
