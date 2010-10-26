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

package cx.ath.mancel01.dependencyshot.test.aop.v2.all;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.aop.v2.AOPBinder;
import cx.ath.mancel01.dependencyshot.aop.v2.PatternHelper;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.test.aop.v2.ResultSingleton;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class AOPTest {

    @Test
    public void testAllTogether() throws Exception {

        DSInjector injector = DependencyShot.getInjector(new AOPBinder() {
            @Override
            public void configureBindings() {
                bind(Service.class).to(HelloService.class);

                cut(HelloService.class).with(HelloInterceptor.class);
                cut("cx*.all.HelloService").with(ClassPatternInterceptor.class);
                cut("cx.ath.*.all.HelloS*.hell*()").with(MethodPatternInterceptor.class);
            }
        });

        Service service = injector.getInstance(Service.class);
        ResultSingleton result = injector.getInstance(ResultSingleton.class);
        
        service.hello();
        Assert.assertTrue(result.getBeforeCall() == 4);
        Assert.assertTrue(result.getAfterCall() == 4);
        service.hello();
        Assert.assertTrue(result.getBeforeCall() == 8);
        Assert.assertTrue(result.getAfterCall() == 8);
        service.something();
        Assert.assertTrue(result.getBeforeCall() == 12);
        Assert.assertTrue(result.getAfterCall() == 12);
        service.goodbye();
        Assert.assertTrue(result.getBeforeCall() == 12);
        Assert.assertTrue(result.getAfterCall() == 12);
    }

    @Test
    public void testSimplePackagesMatcher() throws Exception {
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.package1.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.package1.sub1.*"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc", 
                               "cx.ath.mancel01.project.package1.sub1.Truc"));
    }

    @Test
    public void testComplexPackagesMatcher() throws Exception {
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.*.package1.sub1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.****.*.sub1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.*.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.pack*.sub1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.m*.project.*.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.*.mancel01.proj*t.pac*age1.s*b1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "*.project.*.sub1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.*.*.*.sub1.Truc"));
        Assert.assertTrue(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.package1.sub1.Tr*"));
    }

    @Test
    public void testFalsePackagesMatcher() throws Exception {
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.project.package1.sub1.Tr*k"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.*.*.*.*.*.*k"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.*.*.*.*.*.*.Truc"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "*.*.*.*.*.*.*.Truc"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.*2.project.package1.*.Truc"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "cx.ath.mancel01.projet.package1.sub1.Truc"));
        Assert.assertFalse(PatternHelper.
                matchWithClass("cx.ath.mancel01.project.package1.sub1.Truc",
                               "*.*.ath.mancel01.project.package1.sub1.Truc"));
    }
}
