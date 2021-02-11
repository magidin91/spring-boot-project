package org.example.sweater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * В классе указываются контроллеры, в которых нет логики, для которых нужно вернуть только шаблон по заданному пути
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
	    /* по пути: "/login" - возвращаем шаблон login */
		registry.addViewController("/login").setViewName("login");
	}
}