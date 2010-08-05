/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
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
package cx.ath.mancel01.dependencyshot.utils;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.util.EnhancedProvider;
import cx.ath.mancel01.dependencyshot.utils.annotations.JndiLookup;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Built-in provider for JNDI objects injection.
 *
 * @author Mathieu ANCELIN
 */
public class JndiProvider implements EnhancedProvider {

    private static final Logger logger = Logger.getLogger(JndiProvider.class.getSimpleName());

    @Override
    public final Object enhancedGet(InjectionPoint p) {
        Object ret = null;
        String jndiName = "";
        for (Annotation qualifier : p.getAnnotations()) {
            if (qualifier instanceof JndiLookup) {
                JndiLookup lookup = (JndiLookup) qualifier;
                String key = lookup.value();
                if (key.equals("")) {
                    key = p.getMember().getName();
                }
                try {
                    Context ctx = new InitialContext();
                    ret = ctx.lookup(jndiName);
                } catch (Exception e) {
                    logger.warning("Can't retrieve \""
                            + jndiName
                            + "\" from the JNDI context in class : "
                            + p.getBeanClass()
                            + ". Injecting null instead.\n"
                            + e.toString());
                }
                break;
            }
        }
        return ret;
    }

    @Override
    public final Object get() {
        throw new UnsupportedOperationException(
                "This operation isn't supported on EnhancedProvider.");
    }
}
