package he4rt.open.tibia.base.core.io;

import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AGameHandlerCode {
    RecvOpcodeEnum[] code();
}
