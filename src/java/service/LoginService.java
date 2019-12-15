package service;

import Util.Util;
import exception.ApiException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginService {
    
    private Connection con;

    public String authentication(String strJson){
        JSONObject json;
        JSONObject jsonResponse = new JSONObject();
        String userId, password;
        try {
            json = new JSONObject(strJson);
            userId = json.getString("userId");
            password = json.getString("password");
            
            String sql = "select id, name, address, gender, token from affiliate where id like '" + userId + "' and hash_password like '" + password + "'";
            System.out.println("loginUser" + sql);
            this.con = Util.openConnection(this.con);
            Statement st = this.con.createStatement(1004, 1007);
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                jsonResponse.put("id", rs.getString("id"));
                jsonResponse.put("name", rs.getString("name"));
                jsonResponse.put("address", rs.getString("address"));
                jsonResponse.put("gender", rs.getString("gender"));
                jsonResponse.put("token", rs.getString("token"));
            }
        }  catch (SQLException | JSONException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(jsonResponse.toString());
        return jsonResponse.toString();
    }
    
}
