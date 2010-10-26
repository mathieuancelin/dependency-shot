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

package cx.ath.mancel01.dependencyshot.aop.v2;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PatternHelper {

    public static boolean matchWithClass(String className, String pattern) {
        // Create the cards by splitting using a RegEx. If more speed
        // is desired, a simpler character based splitting can be done.
        String [] cards = pattern.split("\\*");
        // Iterate over the cards.
        for (String card : cards) {
            int idx = className.indexOf(card);
            // Card not detected in the text.
            if(idx == -1) {
                return false;
            }
            // Move ahead, towards the right of the text.
            className = className.substring(idx + card.length());
        }
        return true;
    }
}
