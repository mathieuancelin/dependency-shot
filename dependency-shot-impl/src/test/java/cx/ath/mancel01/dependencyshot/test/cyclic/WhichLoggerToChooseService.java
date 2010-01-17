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

package cx.ath.mancel01.dependencyshot.test.cyclic;

import javax.inject.Inject;

/**
 * Warning, this class is supposed to fail test and throwing exception
 * because of cyclic dependency with logger.
 *
 * @author Mathieu ANCELIN
 */
public class WhichLoggerToChooseService {

//    @Inject
//    private LoggerService logger;

    public int which(){
        int which = (int) ((Math.random() * 3) + 1);
//        logger.log("chosing logging framework number " + which);
        return which;
    }
}
