package io.xylitol.handler;

import io.xylitol.task.TaskContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.xylitol.util.internal.ObjectUtil.classTypeEquals;

/**
 * Created on 2018/1/22.
 *
 * @author xuyandong
 */
public abstract class AbstractTaskHandler implements TaskHandler {

    /**
     * 在所有方法中筛选出需要执行的方法
     *
     * @param taskContext
     */
    protected Method getMethod(TaskContext taskContext) throws NoSuchMethodException {

        List<Method> methods = new ArrayList<>();

        for (Method method : taskContext.getClazz().getDeclaredMethods()) {
            if (method.getName().equals(taskContext.getMethodName()) && method.getParameterCount() == (taskContext.getArgs().length)) {
                methods.add(method);
            }
        }

        if (methods.size() == 1) {
            return methods.get(0);
        } else if (methods.size() > 1) {
            Optional<Method> optionalMethod = methods
                    .stream()
                    .filter(method -> {
                        Class<?>[] ParameterClazz = method.getParameterTypes();
                        for (int i = 0; i < ParameterClazz.length; i++) {
                            if (classTypeEquals(ParameterClazz[i], taskContext.getArgs()[i].getClass())) {
                                continue;
                            }
                            return false;
                        }
                        return true;
                    }).findAny();

            if (optionalMethod.isPresent()) {
                return optionalMethod.get();
            }

        }
        throw new NoSuchMethodException();
    }
}
