package com.github.ignaciotcrespo.randomobject.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crespo on 2/20/17.
 */
public class ClassUtils {

    private static final Map<String, Class<?>> namePrimitiveMap = new HashMap<>();

    static {
        namePrimitiveMap.put("boolean", Boolean.TYPE);
        namePrimitiveMap.put("byte", Byte.TYPE);
        namePrimitiveMap.put("char", Character.TYPE);
        namePrimitiveMap.put("short", Short.TYPE);
        namePrimitiveMap.put("int", Integer.TYPE);
        namePrimitiveMap.put("long", Long.TYPE);
        namePrimitiveMap.put("double", Double.TYPE);
        namePrimitiveMap.put("float", Float.TYPE);
        namePrimitiveMap.put("void", Void.TYPE);
    }

    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }

    private static final Map<Class<?>, Object> primitiveDefaultMap = new HashMap<>();

    static {
        primitiveDefaultMap.put(Boolean.TYPE, false);
        primitiveDefaultMap.put(Byte.TYPE, (byte) 0);
        primitiveDefaultMap.put(Character.TYPE, ' ');
        primitiveDefaultMap.put(Short.TYPE, (short) 0);
        primitiveDefaultMap.put(Integer.TYPE, 0);
        primitiveDefaultMap.put(Long.TYPE, 0L);
        primitiveDefaultMap.put(Double.TYPE, 0.0D);
        primitiveDefaultMap.put(Float.TYPE, 0.0F);
        //primitiveDefaultMap.put(Void.TYPE, Void.TYPE);
    }

    public static Object primitiveToDefault(final Class<?> cls) {
        if (cls != null && cls.isPrimitive()) {
            return primitiveDefaultMap.get(cls);
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        }
        if (instance == null) {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors != null && constructors.length > 0) {
                try {
                    Constructor<?> constructor = constructors[0];
                    constructor.setAccessible(true);
                    Class<?>[] paramTypes = constructor.getParameterTypes();
                    if (paramTypes == null || paramTypes.length == 0) {
                        instance = (T) constructor.newInstance();
                    } else {
                        Object[] params = new Object[paramTypes.length];
                        for (int i = 0; i < paramTypes.length; i++) {
                            if (paramTypes[i].isPrimitive()) {
                                params[i] = primitiveToDefault(paramTypes[i]);
                            } else {
                                params[i] = newInstance(paramTypes[i]);
                            }
                        }
                        instance = (T) constructor.newInstance(params);
                    }
                } catch (InstantiationException e) {
//                    e.printStackTrace();
                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
                }
            }
        }
        return instance;
    }
}
