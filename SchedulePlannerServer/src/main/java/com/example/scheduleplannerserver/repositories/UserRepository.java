package com.example.scheduleplannerserver.repositories;

import com.example.scheduleplannerserver.Database.DBCPDataSource;
import com.example.scheduleplannerserver.Database.DatabaseConnection;
import com.example.scheduleplannerserver.models.UserModel;

import java.sql.*;

public class UserRepository {
    public static UserModel getUser(String username) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from users where Username='" + username + "'")) {
            int id = rs.next() ? rs.getInt(1) : 0;
            //username not found
            if (id == 0) {
                DatabaseConnection.getConnection().close();
                return null;
            }
            UserModel foundUser = new UserModel(rs.getString(2), rs.getString(3), rs.getString(4));
            foundUser.setID(id);
            DBCPDataSource.getConnection().close();
            return foundUser;
        }
    }

    public static UserModel createUser(String username, String passwordHash, String salt) throws SQLException{
        Connection con =   DBCPDataSource.getConnection();
        UserModel userModel = new UserModel(username, passwordHash, salt);

        String QUERY = "insert into users(Username, PasswordHash, Salt) values(?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, salt);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt(1);
                userModel.setID(id);
            }
        }
        DBCPDataSource.getConnection().close();
        return userModel;
    }
}