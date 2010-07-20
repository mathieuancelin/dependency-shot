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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathieu ANCELIN
 */
public class CommandRunnerContext implements CommandContext {

    private String[] rawParams;

    private List<String> commandLineParams;

    private List<String> messages = new ArrayList<String>();

    private boolean badCommand = false;

    @Override
    public List<String> getCommandLineParams() {
        return commandLineParams;
    }

    @Override
    public void setCommandLineParams(List<String> commandLineParams) {
        this.commandLineParams = commandLineParams;
    }

    @Override
    public String[] getRawParams() {
        return rawParams;
    }

    @Override
    public void setRawParams(String[] rawParams) {
        this.rawParams = rawParams;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public boolean isBadCommand() {
        return badCommand;
    }

    @Override
    public void setBadCommand(boolean badCommand) {
        this.badCommand = badCommand;
    }

}
