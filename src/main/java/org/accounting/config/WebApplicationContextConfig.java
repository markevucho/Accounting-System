package org.accounting.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationContextConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses(){

        return new Class<?>[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses(){

        return new Class<?>[]{SpringContextConfig.class};
    }

    @Override
    protected String[] getServletMappings(){

        return new String[]{"/"};
    }


}
