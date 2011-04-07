/*
 *  Copyright 2011 mathieuancelin.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package cx.ath.mancel01.dependencyshot.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Annotation mock to create fake runtime annotations.
 *
 * @author mathieuancelin
 */
public class AnnotationMocker {

    private static ThreadLocal<AnnotationMockImpl> mock =
            new ThreadLocal<AnnotationMockImpl>();

    private AnnotationMocker() {
    }

    public static <Z extends Annotation> AnnotationMockSet<Z> forAnnotation(Class<Z> annotation) {
        return AnnotationMockImpl.newMock(annotation);
    }

    public static <T> GenericAnnotationMockWith<T> set(T call) {
        return new GenericAnnotationMockWith<T>() {

            @Override
            public void with(T value) {
                mock.get().withObject(value);
            }
        };
    }

    public static class AnnotationMockImpl<T>
            implements
                AnnotationMock<T>,
                AnnotationMockSet<T>,
                AnnotationMockWith<T>,
                InvocationHandler {

        private final Map<String, Object> values = new HashMap<String, Object>();
        private final Class<T> anno;
        private Method method;

        private AnnotationMockImpl(Class<T> anno) {
            this.anno = anno;
        }

        static <S extends Annotation> AnnotationMockImpl<S> newMock(Class<S> anno) {
            return new AnnotationMockImpl<S>(anno);
        }

        @Override
        public AnnotationMockWith<T> set(String method) {
            try {
                this.method = anno.getMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return this;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            mock.set(this);
            this.method = method;
            if (method.getName().equals("toString")) {
                StringBuilder builder = new StringBuilder();
                builder.append(anno.getSimpleName());
                builder.append(" : \n");
                for (String name : values.keySet()) {
                    builder.append("    ");
                    builder.append(name);
                    builder.append(" : ");
                    builder.append(values.get(name));
                    builder.append("\n");
                }
                return builder.toString();
            } else {
                if (values.containsKey(method.getName())) {
                    return values.get(method.getName());
                } else {
                    try {
                        return anno.getMethod(method.getName()).getDefaultValue();
                    } catch (Exception e) { return null; }
                }
            }
        }

        @Override
        public T make() {
            return (T) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{anno}, this);
        }

        AnnotationMockSet<T> withObject(Object o) {
            if (method != null) {
                values.put(method.getName(), o);
            } else {
                throw new RuntimeException("Field is not set ...");
            }
            method = null;
            mock.remove();
            return this;
        }

        @Override
        public AnnotationMockSet<T> with(byte b) {
            return withObject(b);
        }

        @Override
        public AnnotationMockSet<T> with(short s) {
            return withObject(s);
        }

        @Override
        public AnnotationMockSet<T> with(int i) {
            return withObject(i);
        }

        @Override
        public AnnotationMockSet<T> with(long l) {
            return withObject(l);
        }

        @Override
        public AnnotationMockSet<T> with(float f) {
            return withObject(f);
        }

        @Override
        public AnnotationMockSet<T> with(double d) {
            return withObject(d);
        }

        @Override
        public AnnotationMockSet<T> with(char c) {
            return withObject(c);
        }

        @Override
        public AnnotationMockSet<T> with(boolean b) {
            return withObject(b);
        }

        @Override
        public AnnotationMockSet<T> with(String str) {
            return withObject(str);
        }

        @Override
        public AnnotationMockSet<T> with(Class clazz) {
            return withObject(clazz);
        }

        @Override
        public AnnotationMockSet<T> with(Annotation anno) {
            return withObject(anno);
        }

        @Override
        public AnnotationMockSet<T> with(Enum e) {
            return withObject(e);
        }

        @Override
        public AnnotationMockSet<T> with(byte[] b) {
            return withObject(b);
        }

        @Override
        public AnnotationMockSet<T> with(short[] s) {
            return withObject(s);
        }

        @Override
        public AnnotationMockSet<T> with(int[] i) {
            return withObject(i);
        }

        @Override
        public AnnotationMockSet<T> with(long[] l) {
            return withObject(l);
        }

        @Override
        public AnnotationMockSet<T> with(float[] f) {
            return withObject(f);
        }

        @Override
        public AnnotationMockSet<T> with(double[] d) {
            return withObject(d);
        }

        @Override
        public AnnotationMockSet<T> with(char[] c) {
            return withObject(c);
        }

        @Override
        public AnnotationMockSet<T> with(boolean[] b) {
            return withObject(b);
        }

        @Override
        public AnnotationMockSet<T> with(String[] str) {
            return withObject(str);
        }

        @Override
        public AnnotationMockSet<T> with(Class[] clazz) {
            return withObject(clazz);
        }

        @Override
        public AnnotationMockSet<T> with(Annotation[] anno) {
            return withObject(anno);
        }

        @Override
        public AnnotationMockSet<T> with(Enum[] e) {
            return withObject(e);
        }
    }

    public static interface GenericAnnotationMockWith<T> {

        void with(T value);
    }

    public static interface AnnotationMock<T> {

        T make();
    }

    public static interface AnnotationMockSet<T> extends AnnotationMock<T> {

        AnnotationMockWith<T> set(String method);
    }

    public static interface AnnotationMockWith<T> extends AnnotationMock<T> {

        AnnotationMockSet<T> with(byte b);

        AnnotationMockSet<T> with(short s);

        AnnotationMockSet<T> with(int i);

        AnnotationMockSet<T> with(long l);

        AnnotationMockSet<T> with(float f);

        AnnotationMockSet<T> with(double d);

        AnnotationMockSet<T> with(char c);

        AnnotationMockSet<T> with(boolean b);

        AnnotationMockSet<T> with(String str);

        AnnotationMockSet<T> with(Class clazz);

        AnnotationMockSet<T> with(Annotation anno);

        AnnotationMockSet<T> with(Enum e);

        AnnotationMockSet<T> with(byte[] b);

        AnnotationMockSet<T> with(short[] s);

        AnnotationMockSet<T> with(int[] i);

        AnnotationMockSet<T> with(long[] l);

        AnnotationMockSet<T> with(float[] f);

        AnnotationMockSet<T> with(double[] d);

        AnnotationMockSet<T> with(char[] c);

        AnnotationMockSet<T> with(boolean[] b);

        AnnotationMockSet<T> with(String[] str);

        AnnotationMockSet<T> with(Class[] clazz);

        AnnotationMockSet<T> with(Annotation[] anno);

        AnnotationMockSet<T> with(Enum[] e);
    }
}
