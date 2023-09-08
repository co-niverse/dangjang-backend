package com.coniverse.dangjang.support.annotation;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.coniverse.dangjang.domain.user.entity.enums.Role;

/**
 * custom security context factory for test
 *
 * @author TEO
 * @see WithDangjangUser
 * @since 1.0.0
 */
public class WithDangjangUserSecurityContextFactory implements WithSecurityContextFactory<WithDangjangUser> {
	@Override
	public SecurityContext createSecurityContext(WithDangjangUser dangjangUser) {
		Role role = dangjangUser.role();
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role.toString());
		User principal = new User("11111111", "", authorities);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}
}
