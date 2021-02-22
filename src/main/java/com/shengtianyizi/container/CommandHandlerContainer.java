package com.shengtianyizi.container;

import com.shengtianyizi.handler.Handler;
import com.shengtianyizi.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName    CommandHandlerContainer
 * Package	    com.shengtianyizi.container
 * Description
 *
 * @author admin
 * @date 2021-02-22 10:22
 */
@Component
@Slf4j
public class CommandHandlerContainer implements InitializingBean {

    private final Map<Integer, Handler> handlers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /***
     * 初始化完成bean后执行 指令加载到容器
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<Handler> values = applicationContext.getBeansOfType(Handler.class).values();
        //添加到handlers中
        values.forEach(messageHandler -> handlers.put(messageHandler.getType(), messageHandler));
        log.info("[afterPropertiesSet][消息处理器数量：{}]", handlers.size());
    }

    /**
     * 获得类型对应的 Handler
     *
     * @param type 类型
     * @return Handler
     */
    public Handler getCommandHandler(Integer type) {
        Handler handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 Handler 处理器", type));
        }
        return handler;
    }

    /**
     * 获得 Handler 处理的消息类
     *
     * @param handler 处理器
     * @return 消息类
     */
    public static Class<? extends Message> getHandlerVOClass(Handler handler) {
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的 Type 数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) { // 此处，是以父类的接口为准
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            // 遍历 interfaces 数组
            for (Type type : interfaces) {
                // 要求 type 是泛型参数
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是 MessageHandler 接口
                    if (Objects.equals(parameterizedType.getRawType(), Handler.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到指令类型", handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到指令类型", handler));
    }
}
