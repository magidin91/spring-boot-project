package org.example.sweater.config;

import org.example.sweater.util.RedirectInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/* получаем переменную из пропертис */
	@Value("${upload.path}")
	private String uploadPath;

	/**
	 * Здесь указываются перенаправления для статических ресурсов
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/* запросы по урл "/img/**" будут перенаправляться на "file://" + uploadPath + "/" */
		registry.addResourceHandler("/img/**")
				.addResourceLocations("file://" + uploadPath + "/");
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
	}
	/**
	 * Здесь указываются контроллеры, в которых нет логики, для которых нужно вернуть только шаблон по заданному пути.
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
	    /* по пути: "/login" - возвращаем шаблон login */
		registry.addViewController("/login").setViewName("login");
	}

	/**
	 * Добавляет перехватчик для турболинкс
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RedirectInterceptor());
	}
}