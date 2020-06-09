package mcp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;

@Documented
@Nonnull
@TypeQualifierDefault({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodsReturnNonnullByDefault {}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\mcp\MethodsReturnNonnullByDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */