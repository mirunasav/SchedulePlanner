package com.example.scheduleplannerserver.Authentication;

import com.example.scheduleplannerserver.Utils.CredentialsMessages;
import com.example.scheduleplannerserver.jpa.models.CredentialsModel;
import com.example.scheduleplannerserver.jpa.models.UserModel;
import com.example.scheduleplannerserver.jpa.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

public class AuthenticationService {
    //allows access in the app
    public static CredentialsMessages areCredentialsValid (CredentialsModel credentials){
        //is username valid?
        try{
            UserModel user = UserRepository.getUser(credentials.getUsername());
            if(user == null)//no such user
                return CredentialsMessages.NO_SUCH_USER;

            //check password validity
            String hashedPassword = BCrypt.hashpw(credentials.getPassword(), user.getSalt());
            if(!hashedPassword.equals(user.getPasswordHash())){
                return CredentialsMessages.INCORRECT_PASSWORD;
            }
            return CredentialsMessages.VALID_CREDENTIALS;
        }
        catch(SQLException e){
            e.printStackTrace();
            return CredentialsMessages.DEFAULT;
        }

    }
}
