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

package cx.ath.mancel01.dependencyshot.webfwk.rendering;

import java.io.File;
import java.io.InputStream;

/**
 *
 * @author mathieuancelin
 */
public interface Renderer {

    TemplateBuilder prepareTemplate(String template);

    void renderXml(Object o);

    void renderJSon(Object o);

    void renderText(Object o);

    void renderBinary(InputStream is);

    void renderBinary(File f);

}
