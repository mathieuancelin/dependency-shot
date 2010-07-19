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

package cx.ath.mancel01.dependencyshot.samples;

import cx.ath.mancel01.dependencyshot.samples.commandrunner.CommandRunner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathieuancelin
 */
public class CommandTest {

    public CommandTest() {
    }

    @Test
    public void testCommandRunner() {
        String[] commandLine = {"command1", "-v", "bla"};
        String[] commandLine2 = {"command", "-v", "bla"};
        CommandRunner runner = new CommandRunner();
        assertTrue(runner.run(commandLine).contains("execute command1 ..."));
        assertTrue(runner.run(commandLine2).contains("Can't find command named : command"));
    }

}