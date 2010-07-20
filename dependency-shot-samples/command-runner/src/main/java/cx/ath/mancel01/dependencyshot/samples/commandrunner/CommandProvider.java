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

package cx.ath.mancel01.dependencyshot.samples.commandrunner;

import cx.ath.mancel01.dependencyshot.samples.commandrunner.annotation.Command;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.CommandContext;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 *
 * @author Mathieu ANCELIN
 */
public class CommandProvider implements Provider {

    private CommandContext context;

    private static Set<String> classes = null;

    private static URL[] urls = null;

    private static AnnotationDB db = null;

    private static Map<String, Class> availableCommands = null;

    public CommandProvider(CommandContext context) {
        try {
            this.context = context;
            if (urls == null)
                urls = ClasspathUrlFinder.findClassPaths();
            if (db == null) {
                db = new AnnotationDB();
                db.scanArchives(urls);
                classes = db.getAnnotationIndex().get(Command.class.getName());
            }
            if (availableCommands == null) {
                availableCommands = new HashMap<String, Class>();
                for (String classe : classes) {              
                        Class clazz = Class.forName(classe);
                        Command commandAnnotation = (Command) clazz.getAnnotation(Command.class);
                        String name = commandAnnotation.value();
                        availableCommands.put(name, clazz);  
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CommandProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object get() {
        String command = context.getRawParams()[0];
        Class commandClazz = availableCommands.get(command);
        if (commandClazz != null) {
            try {
                return commandClazz.newInstance();
            } catch (Exception ex) {
                Logger.getLogger(CommandProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return new FakeCommand("Can't find command named : " + command); // TODO try to find matching names
        }
        return null;
    }
}
