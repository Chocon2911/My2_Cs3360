package DataBase.Child;

import DataBase.Base.AbstractDb;
import DataBase.Base.DbData;
import java.util.ArrayList;
import java.util.List;

public class IdDb extends AbstractDb
{
    //========================================CreateTable=========================================
    public boolean createIdTable()
    {
        String executeLine = "CREATE TABLE IF NOT EXISTS ids "
        + "(GlobalId TEXT PRIMARY KEY)";
        return this.createTable(url, executeLine);
    }

    //===========================================Insert===========================================
    public String insertId(String id)
    {
        String sql = "INSERT INTO ids (GlobalId) VALUES (?)";
        List<DbData> data = new ArrayList<>();
        data.add(new DbData(id));
        return this.insertData(url, sql, data);
    }


    //===========================================Delete===========================================
    public boolean deleteId(String id)
    {
        String sql = "DELETE FROM ids WHERE UserName = ?";
        DbData userNameData = new DbData(id);
        return this.deleteRow(url, sql, userNameData);
    }
}
