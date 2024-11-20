package soup.cream.broccoli.survey.infra.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import soup.cream.broccoli.survey.common.exception.GlobalExceptionDetail
import soup.cream.broccoli.survey.common.response.ResponseError
import soup.cream.broccoli.survey.infra.exception.AuthExceptionHandleFilter
import soup.cream.broccoli.survey.infra.security.jwt.JwtAuthFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authExceptionFilter: AuthExceptionHandleFilter,
    private val authFilter: JwtAuthFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity, objectMapper: ObjectMapper): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler({ _, response, exception ->
                    exception.printStackTrace()
                    response.status = HttpServletResponse.SC_FORBIDDEN

                    objectMapper.writeValue(response.outputStream, ResponseError.ofRaw(GlobalExceptionDetail.ACCESS_DENIED))
                })
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/**").anonymous() // .permitAll()
                    .requestMatchers(HttpMethod.GET, "/survey", "/survey/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/reply").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(authExceptionFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun corsConfigurationSource() = UrlBasedCorsConfigurationSource()
        .apply {
            registerCorsConfiguration("/**",
                CorsConfiguration()
                    .apply { // kotlin style builder
                        addAllowedOriginPattern("*")
                        addAllowedHeader("*")
                        addAllowedMethod("*")
                        allowCredentials = true
                    }
            )
        }
}
