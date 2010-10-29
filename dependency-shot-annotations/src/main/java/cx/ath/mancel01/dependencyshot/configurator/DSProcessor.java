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
package cx.ath.mancel01.dependencyshot.configurator;

import java.io.Writer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 *
 * @author Mathieu ANCELIN
 */
@SupportedAnnotationTypes({"javax.inject.Singleton",
    "javax.inject.Named",
    "javax.annotation.ManagedBean"})
public class DSProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment re) {
        Filer filer = processingEnv.getFiler();
        StringBuilder named = new StringBuilder();
        StringBuilder singleton = new StringBuilder();
        StringBuilder managed = new StringBuilder();
        for (TypeElement annotation : set) {
            FileObject o = null;
            Writer w = null;
            try {
                if (annotation.getQualifiedName().toString().equals("javax.inject.Singleton")) {
                    for (Element e : re.getElementsAnnotatedWith(annotation)) {
                        if (ElementKind.CLASS.equals(e.getKind())) {
                            singleton.append(e.toString());
                            singleton.append("\n");
                        }
                    }
                    o = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "singleton.annotated");
                    w = o.openWriter();
                    w.write(singleton.toString());
                }
                if (annotation.getQualifiedName().toString().equals("javax.inject.Named")) {
                    for (Element e : re.getElementsAnnotatedWith(annotation)) {
                        if (ElementKind.CLASS.equals(e.getKind())) {
                            named.append(e.toString());
                            named.append("\n");
                        }
                    }
                    o = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "named.annotated");
                    w = o.openWriter();
                    w.write(named.toString());
                }
                if (annotation.getQualifiedName().toString().equals("javax.annotation.ManagedBean")) {
                    for (Element e : re.getElementsAnnotatedWith(annotation)) {
                        if (ElementKind.CLASS.equals(e.getKind())) {
                            managed.append(e.toString());
                            managed.append("\n");
                        }
                    }
                    o = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "managed.annotated");
                    w = o.openWriter();
                    w.write(managed.toString());
                }
                w.close();
            } catch (Exception ex) {
                Logger.getLogger(DSProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
}
