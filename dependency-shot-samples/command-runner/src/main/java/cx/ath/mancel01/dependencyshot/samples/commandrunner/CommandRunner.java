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

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.CommandContext;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.RunnableCommand;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.config.CommandBinder;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mathieu ANCELIN
 */
public class CommandRunner {

    private String[] params;

    private CommandContext context ;
    
    public List<String> run(String[] params) {
        this.params = params;
        context = new CommandRunnerContext();
        context.setRawParams(params);
        context.setCommandLineParams(Arrays.asList(params));
        DSInjector injector = DependencyShot.getInjector(new CommandBinder(context));
        RunnableCommand command = injector.getInstance(RunnableCommand.class);
        command.execute(context);
        List<String> messages = context.getMessages();
        for(String message : messages)
            System.out.println(message);
        return messages;
    }
}
