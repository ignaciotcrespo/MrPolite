/**
 * The MIT License
 * <p>
 * Copyright (c) 2017, Ignacio Tomas Crespo (itcrespo@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.ignaciotcrespo.mrpolite.utils;

import com.github.ignaciotcrespo.mrpolite.MrPolite;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by crespo on 2/20/17.
 */
public class ClassUtils {

    private static final Map<String, Class<?>> namePrimitiveMap = new HashMap<String, Class<?>>();

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

    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();

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

    private static final Map<Class<?>, Object> primitiveDefaultMap = new HashMap<Class<?>, Object>();

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

    public static <T> Object newInstance(Class<T> clazz, final Randomizer randomizer) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            if (clazz.getName().equals(List.class.getName())) {
                return new ArrayList();
            } else if (clazz.getName().equals(Set.class.getName())) {
                return new HashSet<Object>();
            } else if (clazz.getName().equals(Queue.class.getName())) {
                return new LinkedList<Object>();
            } else if (clazz.getName().equals(Map.class.getName())) {
                return new HashMap<Object, Object>();
            }
        }
        if (Modifier.isInterface(clazz.getModifiers())) {
            return Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class[]{clazz},
                    new InterfaceInvocationHandler(randomizer, clazz));
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
                                params[i] = newInstance(paramTypes[i], randomizer);
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
                } catch (Exception exc) {

                }
            }
        }
        return instance;
    }

    public static Object getFieldValue(Object parent, String fieldName) {
        Field field = null;
        try {
            field = parent.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(parent);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return field;
    }

    private static class InterfaceInvocationHandler implements InvocationHandler {

        private static final HashMap<String, Object> values = new HashMap<String, Object>();

        private final Randomizer randomizer;
        private final Class clazz;

        public InterfaceInvocationHandler(Randomizer randomizer, Class clazz) {
            this.randomizer = randomizer;
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String key = getKey(method, args, randomizer.getSeed());
            if (!values.containsKey(key)) {
                Object value = MrPolite.one(method.getReturnType()).withSeed(Math.abs(randomizer.getSeed() * key.hashCode())).please();
                values.put(key, value);
            }
            return values.get(key);
        }

        private String getKey(Method method, Object[] args, long seed) {
            String key = method.toString();
            if (args != null) {
                for (Object object : args) {
                    key += object.getClass().toString();
                    key += object.toString();
                }
            }
            key += seed;
            return key;
        }

        @Override
        public String toString() {
            return "InterfaceProxy{" +
                    "clazz=" + clazz +
                    '}';
        }
    }
}
