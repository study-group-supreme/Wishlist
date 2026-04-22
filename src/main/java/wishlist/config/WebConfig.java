package wishlist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wishlist.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns()
                .excludePathPatterns("/**/*.css", "/**/*.png", "/public/**", "/auth/login", "/member/register", "/member/save",
                        // Static resources
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico",

                        // Public pages
                        "/",
                        "/aboutus",
                        "/public/**",

                        // Auth (GET + POST)
                        "/auth/**",

                        // Registration
                        "/member/register",
                        "/member/save",

                        // Error pages
                        "/error/**");
    }
}
