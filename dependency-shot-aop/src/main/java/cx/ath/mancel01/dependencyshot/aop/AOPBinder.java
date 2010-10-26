/*
 *  Copyright 2010 mathieuancelin.
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
package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aopalliance.intercept.MethodInterceptor;

/**
 *
 * @author Mathieu ANCELIN
 */
public abstract class AOPBinder extends Binder {

    private List<Class<?>> cutClasses = new ArrayList<Class<?>>();
    private String cutStringClasses = null;
    private Map<Class<?>, ArrayList<Class<? extends MethodInterceptor>>> advices =
            new HashMap<Class<?>, ArrayList<Class<? extends MethodInterceptor>>>();
    private Map<String, ArrayList<Class<? extends MethodInterceptor>>> stringAdvices =
            new HashMap<String, ArrayList<Class<? extends MethodInterceptor>>>();
    
    public final CutBinder cut(Class<?>... classes) {
        cutClasses.clear();
        cutClasses.addAll(Arrays.asList(classes));
        return new CutBinder(this);
    }

    public final CutBinder cut(String classes) {
        cutStringClasses = null;
        cutStringClasses = classes;
        return new CutBinder(this);
    }

    final void with(Class<? extends MethodInterceptor>... interceptors) {
        for (Class clazz : cutClasses) {
            if (!advices.containsKey(clazz)) {
                advices.put(clazz, new ArrayList<Class<? extends MethodInterceptor>>());
            }
            advices.get(clazz).addAll(Arrays.asList(interceptors));
        }
        cutClasses.clear();
        if (cutStringClasses != null) {
            if (!stringAdvices.containsKey(cutStringClasses)) {
                stringAdvices.put(cutStringClasses, new ArrayList<Class<? extends MethodInterceptor>>());
            }
            stringAdvices.get(cutStringClasses).addAll(Arrays.asList(interceptors));
        }
        cutStringClasses = null;
    }

    Map<Class<?>, ArrayList<Class<? extends MethodInterceptor>>> getAdvices() {
        return advices;
    }

    List<MethodInterceptorWrapper> getInterceptors(Class<?> clazz, InjectorImpl injector) {
        List<MethodInterceptorWrapper> wrappers = new ArrayList<MethodInterceptorWrapper>();
        if (advices.containsKey(clazz)) {
            ArrayList<Class<? extends MethodInterceptor>> interceptors = advices.get(clazz);
            for (Class<? extends MethodInterceptor> interceptor : interceptors) {
                wrappers.add(new MethodInterceptorWrapper(injector.getInstance(interceptor)));
            }
        }
        for (String pattern : stringAdvices.keySet()) {
            if (PatternHelper.matchWithClass(clazz.getName(), pattern)) {
                ArrayList<Class<? extends MethodInterceptor>> interceptors = stringAdvices.get(pattern);
                for (Class<? extends MethodInterceptor> interceptor : interceptors) {
                    wrappers.add(new MethodInterceptorWrapper(injector.getInstance(interceptor)));
                }
            }
            if (pattern.endsWith("()")) {
                for (Method m : clazz.getMethods()) {
                    if (PatternHelper.matchWithClass(clazz.getName() + "." + m.getName() + "()", pattern)) {
                        ArrayList<Class<? extends MethodInterceptor>> interceptors = stringAdvices.get(pattern);
                        for (Class<? extends MethodInterceptor> interceptor : interceptors) {
                            MethodInterceptorWrapper wrap = new MethodInterceptorWrapper(injector.getInstance(interceptor));
                            wrap.addInterceptedMethod(m);
                            wrappers.add(wrap);
                        }
                    }
                }
            }
        }
        return wrappers;
    }
}
