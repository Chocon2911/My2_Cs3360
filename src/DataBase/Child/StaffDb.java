package DataBase.Child;

import DataBase.Base.AbstractDb;
import DataBase.Base.DbData;
import DataBase.Base.DbType;
import Obj.Data.Shop;
import Obj.Data.Staff;

import java.util.*;

public class StaffDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createStaffTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Staffs"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
                + "ShopId TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId), "
                + "FOREIGN KEY (UserName) REFERENCES userNames (GlobalUserName)"
                + ");";
        
        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertStaffData(Staff staff)
    {
        String sql = "INSERT INTO Staffs "
                + "(Id, Name, UserName, Password, ShopId) "
                + "VALUES (?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromStaff(staff);
        String result = this.insertData(url, sql, data);
        if (result == null)
        {
            String idE = new IdDb().insertId(staff.getId());
            if (idE != null) return idE;

            String userNameE = new UserNameDb().insertUserName(staff.getUserName());
            if (userNameE != null) return userNameE;
        }

        return result;
    }

    //===========================================Query============================================
    public Staff queryStaffData(String id)
    {
        DbData queryData = new DbData(id);
        String sql = "SELECT * FROM Staffs WHERE Id = ?";
        List<String> rowNames = this.getStaffRowNames();
        List<DbType> rowTypes = this.getStaffRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getStaff(datas.get(0));
    }

    public List<Staff> queryStaffsByShopId(String shopId)
    {
        DbData queryData = new DbData(shopId);
        String sql = "SELECT * FROM Staffs WHERE ShopId = ?";
        List<String> rowNames = this.getStaffRowNames();
        List<DbType> rowTypes = this.getStaffRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (!datas.isEmpty()) {
        } else {
            return null;
        }

        List<Staff> staffs;
        staffs = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            staffs.add(this.getStaff(datas.get(i)));
        }

        return staffs;
    }
    //===========================================Update===========================================
    public String updateStaffData(Staff staff)
    {
        String sql = "UPDATE Staffs SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromStaff(staff);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteStaffData(String id)
    {
        String sql = "DELETE FROM Staffs WHERE Id = ?";
        DbData idData = new DbData(id);
        boolean result = this.deleteRow(url, sql, idData);
        if (result)
        {
            new IdDb().deleteId(id);
            new UserNameDb().deleteUserName(id);
        }

        return result;
    }

    //===========================================Other============================================
    // Query
    private List<String> getStaffRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("ShopId");

        return rowNames;
    }

    private List<DbType> getStaffRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.TEXT);    // ShopId

        return rowTypes;
    }

    private Staff getStaff(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        String shopId = data.get(4).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        return new Staff(id, name, userName, password, shop);
    }

    // Update - Insert
    private List<DbData> getDataFromStaff(Staff staff)
    {
        DbData id = new DbData(staff.getId());
        DbData name = new DbData(staff.getName());
        DbData userName = new DbData(staff.getUserName());
        DbData password = new DbData(staff.getPassword());
        DbData shopId = new DbData(staff.getShop().getId());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(shopId);

        return data;
    }
}
