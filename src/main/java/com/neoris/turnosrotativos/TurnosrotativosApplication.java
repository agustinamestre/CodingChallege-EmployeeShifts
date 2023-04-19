package com.neoris.turnosrotativos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//*************
//QUEDARON INCOMPLETOS LOS ULTIMOS 4 PUNTOS DE CREAR JORNADA YA QUE ME QUEDE SIN TIEMPO PARA REALIZARLOS.
//*************
//hay un poco de español e ingles mezclado, no llegue a pasar todo a ingles, mil disculpas por eso.

@SpringBootApplication
public class TurnosrotativosApplication {

	static Logger logger = LogManager.getLogger(TurnosrotativosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TurnosrotativosApplication.class, args);
		logger.info("===========================================");
		logger.info("============    APP RUNNING    ============");
		logger.info("===========================================");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/*").allowedOrigins("*");
//			}
//		};
//	}

	@Configuration
	public class CorsConfiguration {

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedOrigins("http://localhost:4200")
							.allowedMethods("GET", "POST", "PUT", "DELETE");
				}
			};
		}
	}
}
