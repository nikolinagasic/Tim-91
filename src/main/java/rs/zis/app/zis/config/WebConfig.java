package rs.zis.app.zis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SuppressWarnings("SpellCheckingInspection")
public class WebConfig implements WebMvcConfigurer {

    //CORS neka bude enable-ovan samo prilikom razvoja aplikacije
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000"); // navodim putanje kojima svako moze
                                                                                  // da pristupa
    }
}
