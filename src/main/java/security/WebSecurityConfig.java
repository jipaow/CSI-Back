//package security;
//
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	  @Override
//	  protected void configure(HttpSecurity http) throws Exception {
//		  http.csrf().disable().authorizeRequests()
//		  	.antMatchers("csi/suspects").permitAll()
//	  		.and()
//	  		.addFilterBefore(new JWTRoleAuthenticationFilter("ChefPolice","lieutnant", "detective","docteur","inspecteur","agent"),  UsernamePasswordAuthenticationFilter.class)
//	  		.authorizeRequests().antMatchers("csi/suspect").permitAll()
//	  		.and()
//	  		.addFilterBefore(new JWTRoleAuthenticationFilter("ChefPolice","lieutnant", "detective","docteur","inspecteur"),  UsernamePasswordAuthenticationFilter.class)
//	  	    .authorizeRequests().antMatchers("csi/suspect/link").permitAll()
//	  		.and()
//	  		.addFilterBefore(new JWTRoleAuthenticationFilter("ChefPolice","lieutnant", "detective","docteur","inspecteur"),  UsernamePasswordAuthenticationFilter.class)
//	  		.authorizeRequests().antMatchers("csi/enquetes").permitAll()
//	  		.and()
//	  		.addFilterBefore(new JWTRoleAuthenticationFilter("ChefPolice","lieutnant", "detective","docteur","inspecteur","agent"),  UsernamePasswordAuthenticationFilter.class)
//	  		.authorizeRequests().antMatchers("csi/enquete").permitAll()
//	  		.and()
//	  		.addFilterBefore(new JWTRoleAuthenticationFilter("ChefPolice","lieutnant", "detective","docteur","inspecteur"),  UsernamePasswordAuthenticationFilter.class);
//		     
//
//	  }
//	  
//	  @Override
//		public void configure(WebSecurity web) throws Exception {
//			web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//		}
//}
