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

import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.CommandContext;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.RunnableCommand;

/**
 *
 * @author Mathieu ANCELIN
 */
public class FakeCommand implements RunnableCommand {

    private String message;

    public FakeCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(CommandContext context) {
        context.addMessage(message);
    }
}
