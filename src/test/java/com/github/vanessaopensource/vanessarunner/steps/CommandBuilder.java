package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.google.common.base.Joiner;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandBuilder {

    private final String functionName;
    private final Map<String, String> parameters = new HashMap<>();

    public CommandBuilder(VRunner step) {
        functionName = step.getDescriptor().getFunctionName();
        processClazz(step, step.getClass());
    }

    public String build() {

        if(parameters.isEmpty()) {
            return String.format("%s()", functionName);
        } else {
            return String.format("%s %s", functionName,
                    Joiner.on(", ").withKeyValueSeparator(": ").join(parameters));
        }
    }

    public String buildScript() {
        var template = "node { env.LOGOS_LEVEL='INFO'; %s }";
        return String.format(template,  build());
    }

    private void processClazz(VRunner step, Class<?> clazz) {
        var fields = clazz.getDeclaredFields();

        Arrays.stream(fields)
               .filter(x-> x.getAnnotation(DataBoundSetter.class) != null)
               .forEach(x -> {
                   try {
                       x.setAccessible(true);
                       var fieldName = x.getName();
                       var fieldValue = x.get(step);
                       addField(fieldName, fieldValue);
                   } catch (IllegalAccessException e) {
                       throw new RuntimeException(e);
                   }
               });

        var superclass = clazz.getSuperclass();
        if (superclass != null) processClazz(step, superclass);
    }

    private void addField(String fieldName, @CheckForNull Object fieldValue) {

        if (fieldValue == null) {
            return;
        }

        if (fieldValue instanceof String) {
            addField(fieldName, (String) fieldValue);
        } else if (fieldValue instanceof Boolean) {
            addField(fieldName, (Boolean) fieldValue);
        } else if (fieldValue instanceof Integer) {
            addField(fieldName, (Integer) fieldValue);
        } else {
            addField(fieldName, fieldValue.toString());
        }
    }

    private void addField(String fieldName, String fieldValue) {
        parameters.put(fieldName, String.format("'%s'", fieldValue));
    }

    private void addField(String fieldName, Boolean fieldValue) {
        parameters.put(fieldName, String.format("%b", fieldValue));
    }

    private void addField(String fieldName, Integer fieldValue) {
        parameters.put(fieldName, String.format("%d", fieldValue));
    }
}
