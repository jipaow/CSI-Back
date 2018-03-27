package security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import io.jsonwebtoken.Claims;

public class JWTRoleAuthenticationFilter extends JWTAuthenticationFilter {
	
	private List<String> acceptedRoles = new ArrayList<String>();
	
	public JWTRoleAuthenticationFilter(String... roles) {
		// Build the list of accepted roles
		for(int i=0; i<roles.length ; i++){
			acceptedRoles.add(roles[i]);
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Extract all the token claims
		Claims claims = extractTokenClaims(request);
		
		// Check the validity of the token
		if((claims != null) && checkTokenValidity(claims)) {
			Object roleObj = claims.get("role");
			if(roleObj instanceof String)
			{
				String role = (String) roleObj;
				
				// If the role is in the list of the accepted roles => validate the filter
				if(acceptedRoles.contains(role))
					chain.doFilter(request, response);
			}
		}
	}
}
