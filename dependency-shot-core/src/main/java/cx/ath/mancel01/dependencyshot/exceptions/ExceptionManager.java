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

import java.util.UUID;

/**
 *
 * @author mathieuancelin
 */
public class ExceptionManager {


    public static ManagedThrowable makeException(Throwable throwable) {
        if (throwable instanceof ExceptionManagedException) {
            return ((ExceptionManagedException) throwable).getThrowable();
        } else {
            ManagedThrowable t = new ManagedThrowable();
            t.setT(throwable);
            return t;
        }
    }

    public static ManagedThrowable makeReThrowableException(Throwable throwable) {
        if (throwable instanceof ExceptionManagedException) {
            return ((ExceptionManagedException) throwable).getThrowable();
        } else {
            ManagedThrowable t = new ManagedThrowable();
            t.setT(throwable);
            return t;
        }
    }

    public static ManagedThrowable makeException(Class<? extends Throwable> type, String message, Object... args) {
        ManagedThrowable t = new ManagedThrowable();
        t.setArgs(args);
        t.setMessage(message);
        t.setType(type);
        return t;
    }
    
    public static ManagedThrowable makeException(String message, Object... args) {
        return makeException(DSException.class, message, args);
    }

    public String digestManaged(ManagedThrowable throwable) {
        return throwable.toString();
    }

    public static class ManagedThrowable {

        private String uuid;

        private Throwable t;

        private String message;

        private Object[] args;

        private Class<? extends Throwable> type;

        public ManagedThrowable() {
            uuid = UUID.randomUUID().toString();
        }

//        public void throwManaged() throws RuntimeException {
//            throw new ExceptionManagedException(this);
//        }

        public ExceptionManagedException get() {
            return new ExceptionManagedException(this);
        }

        public void setT(Throwable t) {
            this.t = t;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setType(Class<? extends Throwable> type) {
            this.type = type;
        }

        public Object[] getArgs() {
            return args;
        }

        public String getMessage() {
            return message;
        }

        public Throwable getT() {
            return t;
        }

        public Class<? extends Throwable> getType() {
            return type;
        }

        public String getUuid() {
            return uuid;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ManagedThrowable other = (ManagedThrowable) obj;
            if ((this.uuid == null) ? (other.uuid != null) : !this.uuid.equals(other.uuid)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "ManagedThrowable{" + "uuid=" + uuid + "t=" + t + "message=" + message + "args=" + args + "type=" + type + '}';
        }
    }
}
