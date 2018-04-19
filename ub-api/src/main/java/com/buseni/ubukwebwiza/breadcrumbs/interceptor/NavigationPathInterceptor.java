package com.buseni.ubukwebwiza.breadcrumbs.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.DefaultNavigationInfoProvider;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.NavigationEntry;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.NavigationInfoProvider;

/**
 * 
 * @author fahabumu
 *
 */
public class NavigationPathInterceptor extends HandlerInterceptorAdapter {

    public static final String NAVIGATION_PATH = "navigationPath";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession currentSession = request.getSession();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<? extends Object> entryClass = handlerMethod.getBean().getClass();
        
        if (entryClass.isAnnotationPresent(Navigation.class)) {            
            List<NavigationEntry> basePath =  new ArrayList<NavigationEntry>();                       
            generateNavigationEntry(entryClass, currentSession, basePath);
            Collections.reverse( basePath);
            currentSession.setAttribute(NAVIGATION_PATH, basePath);
        } else {
            clearNavigationPath(currentSession);
        }
    }

    private void clearNavigationPath(HttpSession session) {
        session.setAttribute(NAVIGATION_PATH, new ArrayList<NavigationEntry>());
    }

  
    
    private List<NavigationEntry> generateNavigationEntry(Class<? extends Object> entryClass, HttpSession session, List<NavigationEntry>  navigationPath) throws IllegalAccessException, InstantiationException {
        Navigation navigation = entryClass.getAnnotation(Navigation.class);
        NavigationInfoProvider infoProvider;
        if (navigation.infoProvider().length > 0) {
            infoProvider = navigation.infoProvider()[0].newInstance();
        } else if (navigation.name().length > 0 && navigation.url().length > 0) {
            infoProvider = new DefaultNavigationInfoProvider(navigation.name()[0], navigation.url()[0]);
        } else {
            throw new RuntimeException("Wrong navigation controller!");
        }

        NavigationEntry navigationEntry = new NavigationEntry();
        navigationEntry.setName(infoProvider.getName(session));
        navigationEntry.setUrl(infoProvider.getUrl(session));
        navigationEntry.setNavigationClass(entryClass);
        navigationPath.add(navigationEntry);
        if(navigation.parent().length > 0){
        generateNavigationEntry(navigation.parent()[0], session, navigationPath);}

        return navigationPath;
    }
}
