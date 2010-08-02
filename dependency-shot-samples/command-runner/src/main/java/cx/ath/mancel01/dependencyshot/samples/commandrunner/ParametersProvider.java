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

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.util.EnhancedProvider;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.annotation.Param;
import cx.ath.mancel01.dependencyshot.samples.commandrunner.api.CommandContext;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mathieu ANCELIN
 */
public class ParametersProvider implements EnhancedProvider {

    private CommandContext context;

    public ParametersProvider(CommandContext context) {
        this.context = context;
    }

    @Override
    public Object enhancedGet(InjectionPoint p) {
        String name = p.getMember().getName();
        Param param = null;
        for (Annotation qualifier : p.getAnnotations()) {
            if (qualifier instanceof Param) {
                param = (Param) qualifier;
            }
        }
        String description = param.description();
        String key = param.i18nKey();
        if(!param.name().equals("")) {
            name = param.name();
        }
        List<String> acceptableValues = Arrays.asList(param.acceptableValues().split(","));
        Object value = param.defaultValue();
  
        boolean optional = param.optional();
        boolean primary = param.primary();
        String shortName = param.shortName();

        String prefix = "-";
        if (!shortName.equals("")) {
            prefix = "--";
        }
        if (p.getType().equals(Boolean.class)) {
            value = handleBooleanParam(value, prefix, name, shortName, optional);
        }
        if (p.getType().equals(String.class)) {
            value = handleStringParam(value, prefix, name, shortName, optional);
        }
        return value;
    }

    @Override
    public Object get() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Object handleBooleanParam(Object value, String prefix,
            String name, String shortName, boolean optional) {
        if (context.getCommandLineParams().contains(prefix + name)) {
            value = true;
        } else {
            if (context.getCommandLineParams().contains("-" + shortName)) {
                value = true;
            } else {
                value = false;
                if (!optional) {
                    context.setBadCommand(true);
                    StringBuilder error = new StringBuilder().append("Parameter ").append(name);
                    if (!shortName.equals("")) {
                        error.append(" (or \"").append(shortName).append("\") ");
                    }
                    error.append(" is not present and is mandatory.").append(" Retry the command with good arguments.");
                    context.addMessage(error.toString());
                }
            }
        }
        return value;
    }

    private String handleStringParam(Object value, String prefix,
                        String name, String shortName, boolean optional) {
        if (context.getCommandLineParams().contains(prefix + name)) {
            int index = context.getCommandLineParams().indexOf(prefix + name);
            try {
                value = context.getCommandLineParams().get(index + 1);
            } catch (Exception e) {
                context.setBadCommand(true);
                StringBuilder error = new StringBuilder()
                        .append(e);
                context.addMessage(error.toString());
            }
        } else {
            if (context.getCommandLineParams().contains("-" + shortName)) {
                int index = context.getCommandLineParams().indexOf("-" + shortName);
                try {
                    value = context.getCommandLineParams().get(index + 1);
                } catch (Exception e) {
                    context.setBadCommand(true);
                    StringBuilder error = new StringBuilder()
                            .append(e);
                    context.addMessage(error.toString());
                }
            } else {
                if (isPrimaryPresent()) {
                    value = getPrimary();
                } else {
                    value = "NOT FOUND";
                    if (!optional) {
                        context.setBadCommand(true);
                        StringBuilder error = new StringBuilder().append("Parameter ")
                                .append(name);
                                if (!shortName.equals("")) {
                                    error.append(" (or \"")
                                         .append(shortName)
                                         .append("\") ");
                                }
                                error.append(" is not present and is mandatory.")
                                     .append(" Retry the command with good arguments.");
                        context.addMessage(error.toString());
                    }
                }
            }
        }
        return (String) value;
    }

    private boolean isPrimaryPresent() {
        return true;
    }

    private String getPrimary() {
        return "primary";
    }
}
