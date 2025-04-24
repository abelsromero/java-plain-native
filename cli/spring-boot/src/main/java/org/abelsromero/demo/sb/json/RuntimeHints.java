package org.abelsromero.demo.sb.json;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.util.ReflectionUtils;

public class RuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(org.springframework.aot.hint.RuntimeHints hints, ClassLoader classLoader) {
        registerRecord(Message.class, hints);
    }

    private static void registerRecord(Class<Message> clazz, org.springframework.aot.hint.RuntimeHints hints) {
        hints.reflection()
            .registerType(clazz)
            .registerConstructor(clazz.getConstructors()[0], ExecutableMode.INVOKE)
            .registerMethod(ReflectionUtils.findMethod(clazz, "message"), ExecutableMode.INVOKE);
    }

}
