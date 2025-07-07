package com.easypay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			.authorizeHttpRequests(authorize -> authorize
					
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					//user 
					.requestMatchers("/api/user/signup").permitAll()
					.requestMatchers("/api/user/token").permitAll()
					.requestMatchers("/api/user/details").permitAll()
					
					//jobtitle
					.requestMatchers("/api/jobtitle/add").hasAuthority("HR MANAGER")
					.requestMatchers("/api/jobtitle/get-all").hasAuthority("HR MANAGER")
					.requestMatchers("/api/jobtitle/get-one/{id}").hasAuthority("HR MANAGER")
					.requestMatchers("/api/jobtitle/update/{id}").hasAuthority("HR MANAGER")
					.requestMatchers("/api/jobtitle/delete/{id}").hasAuthority("HR MANAGER")
					
					//employee
					.requestMatchers("/api/employee/add/{departmentId}/{jobTitleId}").hasAuthority("HR MANAGER")
					.requestMatchers("/api/employee/get-all").hasAnyAuthority("HR MANAGER","SUPERVISOR")
					.requestMatchers("/api/employee/get-one").permitAll()
					.requestMatchers("/api/employee/get-one/{employeeId}").hasAnyAuthority("HR MANAGER","SUPERVISOR")
					.requestMatchers("/api/employee/update/{employeeId}").hasAuthority("EMPLOYEE")
					.requestMatchers("/api/employee/upload/profile-pic").hasAuthority("EMPLOYEE")
					.requestMatchers("/api/employee/dashboard/{employeeId}").hasAuthority("EMPLOYEE")
					
					//benefit
					.requestMatchers("/api/benefit/add").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/benefit/get-all").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/benefit/update/{id}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/benefit/delete/{id}").hasAuthority("PAYROLL PROCESSOR")
					
					//employee-benefits
					.requestMatchers("/api/employee-benefits/assign/{employeeId}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/employee-benefits/get-all/{employeeId}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/employee-benefits/unassign/{employeeId}/{benefitId}").hasAuthority("PAYROLL PROCESSOR")
					
					//payroll policy
					.requestMatchers("/api/payrollpolicy/add").hasAuthority("HR MANAGER")
					.requestMatchers("/api/payrollpolicy/get-all").hasAuthority("HR MANAGER")
					.requestMatchers("/api/payrollpolicy/get-one").hasAuthority("HR MANAGER")
					.requestMatchers("/api/payrollpolicy/update/{id}").hasAuthority("HR MANAGER")
					.requestMatchers("/api/payrollpolicy/delete/{id}").hasAuthority("HR MANAGER")
					
					//leave
					.requestMatchers("/api/leave/add/{employeeId}").hasAuthority("EMPLOYEE")
					.requestMatchers("/api/leave/get-one/{leaveId}").permitAll()
					.requestMatchers("/api/leave/get-all").hasAuthority("SUPERVISOR")
					.requestMatchers("/api/leave/{leaveId}/status").hasAuthority("SUPERVISOR")
					.requestMatchers("/api/leave/get-all/{employeeId}").permitAll()
					
					//timesheets
					.requestMatchers("/api/timesheet/add/{employeeId}").hasAuthority("EMPLOYEE")
					.requestMatchers("/api/timesheet/get-all/{employeeId}").hasAnyAuthority("EMPLOYEE","SUPERVISOR")
					.requestMatchers("/api/timesheet/get-by-weekstart/{employeeId}").hasAnyAuthority("EMPLOYEE","SUPERVISOR")
					
					//payroll
					.requestMatchers("/api/payroll/add/{employeeId}").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/payroll/get-all").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/payroll/get-all-filtered").hasAuthority("PAYROLL PROCESSOR")
					.requestMatchers("/api/payroll/get-one/{payrollId}").hasAnyAuthority("EMPLOYEE","PAYROLL PROCESSOR")
					.requestMatchers("/api/payroll/get-all/{employeeId}").permitAll()
					
					.requestMatchers("/api/supervisor/add").hasAuthority("HR MANAGER")
					.requestMatchers("/api/supervisor/get-all").hasAuthority("HR MANAGER")
					
					//hr manager
					.requestMatchers("/api/hrmanager/upload/profile-pic").authenticated()
					.requestMatchers("/api/hrmanager/get-one").authenticated()
					.requestMatchers("/api/hrmanager/update/{hrId}").authenticated()
					.requestMatchers("/api/hr-manager/dashboard/summary").authenticated()
					
					//processor
					.requestMatchers("/api/payroll-processor/upload/profile-pic").authenticated()
					.requestMatchers("/api/payroll-processor/get-one").authenticated()
					.requestMatchers("/api/payroll-processor/update/{processorId}").authenticated()
					.requestMatchers("/api/payroll-processor/dashboard").authenticated()
					
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
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:5173"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
	    //config.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}


}
