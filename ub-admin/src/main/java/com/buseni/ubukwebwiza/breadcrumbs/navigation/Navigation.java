package com.buseni.ubukwebwiza.breadcrumbs.navigation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Fabrice
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Navigation {
    Class<? extends Object>[] parent() default {};
    String[] url() default {};
    String[] name() default {};
    Class<? extends NavigationInfoProvider>[] infoProvider() default {};
}
