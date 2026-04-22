//package wishlist.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import wishlist.interceptor.LoginInterceptor;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns(
//                // Static resources (Azure-safe)
//                "/css/**",
//                "/js/**",
//                "/images/**",
//                "/static/**",
//                "/webjars/**",
//                "/**/*.css",
//                "/**/*.js",
//                "/**/*.png",
//                "/**/*.jpg",
//                "/**/*.jpeg",
//                "/**/*.svg",
//                "/favicon.ico",
//
//                // Public pages
//                "/",
//                "/aboutus",
//                "/public/**",
//
//                // Auth
//                "/auth/**",
//
//                // Registration
//                "/member/register",
//                "/member/save",
//
//                // Error pages
//                "/error/**"
//        );
////                .excludePathPatterns("/**/*.css", "/**/*.png", "/public/**", "/member/register", "/member/save", "/auth/login", "/", "/aboutus"
////                        ,"/member/member-login", "/member/member-registration");
//
//
//        //        registry.addInterceptor(new LoginInterceptor())
////                .addPathPatterns("/**")
////                .excludePathPatterns(
////                        // Static resources
////                        "/css/**",
////                        "/js/**",
////                        "/images/**",
////                        "/favicon.ico",
////
////                        // Public pages
////                        "/",
////                        "/aboutus",
////                        "/public/**",
////
////                        // Auth (GET + POST)
////                        "/auth/**",
////
////                        // Registration
////                        "/member/register",
////                        "/member/save",
////
////                        // Error pages
////                        "/error/**"
////                );
//}
//
//}
