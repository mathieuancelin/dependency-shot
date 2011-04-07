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
package cx.ath.mancel01.dependencyshot.test.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static cx.ath.mancel01.dependencyshot.util.AnnotationMocker.set;

import cx.ath.mancel01.dependencyshot.util.AnnotationMocker;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.jws.WebService;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationMockerTest {
    static final String hello = "Hello";
    static final String kevin = "Kevin";
    static final String mathieu = "Mathieu";
    static final String ns = "www.test.com";
    static final String service = "Service";
    static final String port = "PortType";
    static final String wsdl = "www.test.com?wsdl";
    static final String itf = "MyInterface";

    @Test
    public void testAnnotationMock() {
        
        Named named = AnnotationMocker.forAnnotation(Named.class)
                .set("value").with(hello)
                .make();
        Named mockNamed = AnnotationMocker.forAnnotation(Named.class).make();
        set(mockNamed.value()).with(kevin);

        MyNamed myNamed1 = AnnotationMocker.forAnnotation(MyNamed.class).make();
        set(myNamed1.ok()).with(Boolean.FALSE);
        MyNamed myNamed2 = AnnotationMocker.forAnnotation(MyNamed.class).make();

        WebService ws = AnnotationMocker.forAnnotation(WebService.class)
                .set("name").with(hello)
                .set("targetNamespace").with(ns)
                .set("serviceName").with(service)
                .set("portName").with(port)
                .set("wsdlLocation").with(wsdl)
                .set("endpointInterface").with(itf)
                .make();
        
        Assert.assertEquals(hello, ws.name());
        Assert.assertEquals(ns, ws.targetNamespace());
        Assert.assertEquals(service, ws.serviceName());
        Assert.assertEquals(port, ws.portName());
        Assert.assertEquals(wsdl, ws.wsdlLocation());
        Assert.assertEquals(itf, ws.endpointInterface());
        Assert.assertEquals(kevin, mockNamed.value());
        Assert.assertEquals(mathieu, myNamed2.value());
        Assert.assertFalse(myNamed1.ok());
        Assert.assertEquals(hello, named.value());
    }

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface MyNamed {

        String value() default AnnotationMockerTest.mathieu;
        boolean ok() default true;
    }
}
