package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/**
	 * パスワード暗号化処理
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * セキュリティの各種設定
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// ログイン設定
		.formLogin(login -> login
			    .loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログインページの指定
				.failureUrl("/login?error") // ログイン失敗時の遷移先
				.usernameParameter("userId") // ログインページのユーザーID
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/user/list", true) // ログイン成功後の遷移先
		// ログアウト設定
		).logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト処理のパス
//				.logoutUrl("/logout") // ログアウトページのパス
				.logoutSuccessUrl("/login?logout") // ログアウト成功後の遷移先
		).authorizeHttpRequests(authz -> authz
				// ログイン不要ページの設定
				.requestMatchers("/webjars/**").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/js/**").permitAll()
				.requestMatchers("/img/**").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/user/signup").permitAll()
				.requestMatchers("/user/signup/rest").permitAll()
				// アドミン権限が必要なページの設定
				.requestMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
				//H2用設定
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
			    .anyRequest().authenticated()
		).csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
		).headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())
		);
		return http.build();
	}
}