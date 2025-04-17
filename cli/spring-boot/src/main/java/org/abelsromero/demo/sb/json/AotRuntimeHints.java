package org.abelsromero.demo.sb.json;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.util.ReflectionUtils;

public class AotRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        registerRecord(Message.class, hints);
    }

    private static void registerRecord(Class<Message> clazz, RuntimeHints hints) {
        hints.reflection()
            .registerType(clazz)
            .registerConstructor(clazz.getConstructors()[0], ExecutableMode.INVOKE)
            .registerMethod(ReflectionUtils.findMethod(clazz, "message"), ExecutableMode.INVOKE);
    }

}
