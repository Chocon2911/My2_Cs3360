package DataBase.Child;

import DataBase.Base.*;
import java.util.*;

public class UserNameDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static UserNameDb instance;

    //=========================================Singleton==========================================
    public static UserNameDb getInstance()
    {
        if (instance == null) instance = new UserNameDb();
        return instance;
    }

    //========================================CreateTable=========================================
    public boolean createUserNameTable()
    {
        String executeLine = "CREATE TABLE IF NOT EXISTS userNames "
        + "(GlobalUserName TEXT PRIMARY KEY)";
        return this.createTable(url, executeLine);
    }

    //===========================================Insert===========================================
    public String insertUserName(String userName)
    {
        String sql = "INSERT INTO userNames (GlobalUserName) VALUES (?)";
        List<DbData> data = new ArrayList<>();
        data.add(new DbData(userName));

        System.out.println("===insert UserName===");
        return this.insertData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteUserName(String userName)
    {
        String sql = "DELETE FROM userNames WHERE GlobalUserName = ?";
        DbData userNameData = new DbData(userName);
        return this.deleteRow(url, sql, userNameData);
    }
}
