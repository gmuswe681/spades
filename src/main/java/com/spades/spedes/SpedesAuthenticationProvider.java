package com.spades.spedes;

import java.sql.*;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SpedesAuthenticationProvider implements AuthenticationProvider {

    @Value( "${spring.datasource.url}" )
    private static String dbUrl;

    @Value( "${spring.datasource.username}" )
    private static String dbUser;

    @Value( "${spring.datasource.password}" )
    private static String dbPass;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user = ((UserDetails)authentication).getUsername();

        try(Connection c = getConnection())
        {
            String statement = "SELECT * FROM users WHERE username=?";
            PreparedStatement s = c.prepareStatement(statement);
            s.setString(1, user);
            ResultSet r = s.executeQuery();
            if(r.next())
            {
                String resultUsername = r.getString("username");
                //String resultPassword = r.getString("password");
                if(user.equals(resultUsername))
                {
                    authentication.setAuthenticated(true);
                }
            }

        }
        catch(SQLException e)
        {
            System.out.println("database exception occurred");
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
