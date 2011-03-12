/*
 *  Copyright 2011 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.exceptions;

import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManager.ManagedThrowable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mathieuancelin
 */
public class ExceptionManagedException extends RuntimeException {

    private final ExceptionManager.ManagedThrowable throwable;

    public ExceptionManagedException(ExceptionManager.ManagedThrowable throwable) {
        this.throwable = throwable;
    }

    public ManagedThrowable getThrowable() {
        return throwable;
    }

    @Override
    public Throwable getCause() {
        if (throwable.getT() != null) {
            return throwable.getT().getCause();
        }
        return super.getCause();
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder().append("\n");
        if (throwable.getType() != null) {
            builder.append(throwable.getType().getName()).append(" : ");
        } else {
            if (throwable.getT() != null) {
                builder.append(throwable.getT().getClass().getName()).append(" : ");
            }
        }
        if (throwable.getMessage() != null) {
            builder.append(throwable.getMessage());
            if (throwable.getArgs() != null) {
                builder.append("\n");
                for (Object obj : throwable.getArgs()) {
                    builder.append(obj.toString()).append("\n");
                }
            }
        } else {
            if (throwable.getT() != null) {
                builder.append(throwable.getT().getMessage());
            }
        }
        return builder.toString();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (throwable.getT() != null) {
            return throwable.getT().getStackTrace();
        }
        List<StackTraceElement> stack = Arrays.asList(super.getStackTrace());
        return stack.subList(1, stack.size()).toArray(new StackTraceElement[] {});
    }

    @Override
    public String toString() {
        return getMessage();
    }

    @Override
    public void printStackTrace() {
        if (throwable.getT() != null) {
            throwable.getT().printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
