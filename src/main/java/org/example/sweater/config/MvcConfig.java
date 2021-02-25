package org.example.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * В классе указываются контроллеры, в которых нет логики, для которых нужно вернуть только шаблон по заданному пути
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/* получаем переменную из пропертис */
	@Value("${upload.path}")
	private String uploadPath;

	@Override
	/* запросы по урл "/img/**" будут перенаправляться на "file://" + uploadPath + "/"*/
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**")
				.addResourceLocations("file://" + uploadPath + "/");
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
	}

	public void addViewControllers(ViewControllerRegistry registry) {
	    /* по пути: "/login" - возвращаем шаблон login */
		registry.addViewController("/login").setViewName("login");
	}
}