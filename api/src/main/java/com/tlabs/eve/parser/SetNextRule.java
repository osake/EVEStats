package com.tlabs.eve.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetNextRule extends BaseRule {
    private static final Logger LOG = LoggerFactory.getLogger(SetNextRule.class);

    private String methodName;

    public SetNextRule(String methodName) {
        super();
        this.methodName = methodName;
    }

    @Override
    public void doEnd(String name) {
        Object toSet = getDigester().peek(0);
        Object bean = getDigester().peek(1);

        try {
            Method addMethod = bean.getClass().getMethod(this.methodName, toSet.getClass());
            addMethod.invoke(bean, toSet);
        }
        catch (NoSuchMethodException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage(), e);
            }
            LOG.warn(bean.getClass().getSimpleName() + " NoSuchMethodException:" + e.getMessage() + "(" + toSet.getClass() + ")");
        }
        catch (InvocationTargetException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage(), e);
            }
            LOG.warn(bean.getClass().getSimpleName() + " InvocationTargetException:" + e.getMessage() + "(" + toSet.getClass() + ")");
        }
        catch (IllegalAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage(), e);
            }
            LOG.warn(bean.getClass().getSimpleName() + " IllegalAccessException:" + e.getMessage() + "(" + toSet.getClass() + ")");
        }
    }

}
