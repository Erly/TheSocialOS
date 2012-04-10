package net.thesocialos.server.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.objectify.condition.If;

/**
 * <p>Flags a field of a POJO entity that should be completely ignored by Objectify.  It will not be saved
 * and it will not be loaded.  This is equivalent to the JPA @Transient annotation.</p>
 * 
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Ignore
{
	Class<? extends If<?, ?>>[] value() default { com.googlecode.objectify.condition.Always.class };
}