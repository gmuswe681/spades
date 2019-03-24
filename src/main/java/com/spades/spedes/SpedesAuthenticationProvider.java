package com.spades.spedes;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SpedesAuthenticationProvider implements AuthenticationProvider {

    @Value( "${spring.datasource.url}" )
    private String dbUrl;

    @Value( "${spring.datasource.username}" )
    private String dbUser;

    @Value( "${spring.datasource.password}" )
    private String dbPass;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user = authentication.getName();
        String password = (String) authentication.getCredentials();

        try(Connection c = getConnection())
        {
            String statement = "SELECT * FROM users WHERE username=?";
            PreparedStatement s = c.prepareStatement(statement);
            s.setString(1, user);
            ResultSet r = s.executeQuery();
            if(r.next())
            {
                String resultUsername = r.getString("username");
                String resultPassword = r.getString("password");

                if(user == null || password == null)
                {
                    throw new BadCredentialsException("Bad Credentials");
                }
                
                if(user.equals(resultUsername) && password.equals(resultPassword))
                {
                    return new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials(), new ArrayList<GrantedAuthority>());
                }
            }

        }
        catch(SQLException e)
        {
            System.out.println("database exception occurred");
        }

        throw new BadCredentialsException("Bad Credentials");
        //return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
