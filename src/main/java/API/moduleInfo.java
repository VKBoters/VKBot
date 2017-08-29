package API;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 */
/** @author uis */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface moduleInfo {
//	String dependencies();
	String dependencies() default "modprober";
	public String name() default "A module";
	public String version() default "9999";
	public byte build() default 0;
	boolean vcontrol() default true;
}