/*
 *  Copyright 2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic.v2;

/**
 *
 * @author mathieu
 */
@Dynamic
public class CreditCardServiceImpl implements PaymentService {

    @Override
    public void pay(double price) {
        System.out.println("pay " + price + " with credit card");
    }

}
