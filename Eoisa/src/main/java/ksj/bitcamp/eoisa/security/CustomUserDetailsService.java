package ksj.bitcamp.eoisa.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ksj.bitcamp.eoisa.dto.SignDTO;

public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private SqlSession sqlSession;
	
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    
    public CustomUserDetailsService() {}
	  
    public CustomUserDetailsService(SqlSessionTemplate sqlSession) {
    	this.sqlSession = sqlSession;
	}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {
    	SignDTO user = sqlSession.selectOne("ksj.bitcamp.eoisa.dto.SignDTO.userdetails", username);
    	if(user == null) throw new UsernameNotFoundException(username);
    	if(user.getEnabled() == 0) throw new DisabledException(username);
    	List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
 	    gas.add(new SimpleGrantedAuthority(user.getAuthority()));
 	    return new CustomUserDetails(user.getUsername()
 	    		,user.getPassword()
 	    		,enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
 	    		,gas
 	    		,user.getNickname()
 	    		,user.getProfile_pic()
 	    		,user.getPlatform()
         );
    }
}