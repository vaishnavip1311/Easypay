package com.easypay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			.authorizeHttpRequests(authorize -> authorize
					
					//user 
					.requestMatchers("/api/user/add").permitAll()
					.requestMatchers("/api/user/token").authenticated()
					.requestMatchers("/api/user/details").authenticated()
					
					// jobtitle
					.requestMatchers("/api/jobtitle/add").hasAuthority("HR MANAGER")
					.requestMatchers("/api/jobtitle/get-all").hasAuthority("HR MANAGER")
					
					//employee
					.requestMatchers("/api/employee/add/{departmentId}/{jobTitleId}").hasAuthority("HR MANAGER")
					.requestMatchers("/api/employee/get-all").hasAnyAuthority("HR MANAGER","SUPERVISOR")
					.requestMatchers("/api/employee/get-one").authenticated()
					.requestMatchers("/api/employee/get-one/{employeeId}").hasAnyAuthority("HR MANAGER","SUPERVISOR")
					
					//benefit
					.requestMatchers("/api/benefit/add").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/benefit/get-all").hasAuthority("PAYROLL PROCESSOR")
					
					//employee-benefits
					.requestMatchers("/api/employee-benefits/assign/{employeeId}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/employee-benefits/get-all/{employeeId}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/employee-benefits/unassign/{employeeId}/{benefitId}").hasAuthority("PAYROLL PROCESSOR")
					
					//payroll policy
					.requestMatchers("/api/payrollpolicy/add").hasAuthority("HR MANAGER")
					
					//leave
					.requestMatchers("/api/leave/add/{employeeId}").hasAuthority("EMPLOYEE")
					.requestMatchers("/api/leave/get-one/{leaveId}").hasAnyAuthority("EMPLOYEE","SUPERVISOR")
					.requestMatchers("/api/leave/get-all").hasAuthority("SUPERVISOR")
					.requestMatchers("/api/leave/{leaveId}/status").hasAuthority("SUPERVISOR")
					
					
					.requestMatchers("/api/supervisor/add").hasAuthority("HR MANAGER")
					.requestMatchers("/api/supervisor/get-all").hasAuthority("HR MANAGER")
					
					
					
				.anyRequest().authenticated()  
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) 
		 .httpBasic(Customizer.withDefaults()); //<- this activated http basic technique
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {  //<- Bean saves this object in spring's context
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) 
			throws Exception {
		  return auth.getAuthenticationManager();
	 }

}
