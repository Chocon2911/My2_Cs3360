package DataBase.Child;

import DataBase.Base.AbstractDb;
import DataBase.Base.DbData;
import DataBase.Base.DbType;
import Obj.Data.CustomerRequest;
import Obj.Data.Shop;
import Obj.Data.Staff;
import java.util.*;

public class StaffDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static StaffDb instance;

    //=========================================Singleton==========================================
    public static StaffDb getInstance()
    {
        if (instance == null) instance = new StaffDb();
        return instance;
    }

    //========================================Create Table========================================
    public boolean createStaffTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Staffs"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
                + "IsLogin INTEGER, "
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
                + "(Id, Name, UserName, Password, IsLogin, ShopId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromStaff(staff);

        System.out.println("===insert Staff===");
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
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryStaffRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        Staff staff = this.getStaffData(datas.get(0));
        
        // Shop
        String shopId = datas.get(0).get(4).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // List<CustomerRequest>
        queryValue = "HandledStaffId";
        datas = new CustomerRequestDb().queryCustomerRequestRawDatas(queryData, queryValue);
        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest customerRequest = new CustomerRequestDb().getCustomerRequestData(customerRequestData);
            customerRequests.add(customerRequest);
        }

        staff.setShop(shop);
        staff.setCustomerRequests(customerRequests);
        return staff;
    }

    public Staff queryStaffByUserName(String userName)
    {
        DbData queryData = new DbData(userName);
        String queryValue = "UserName";
        List<List<DbData>> datas = this.queryStaffRawDatas(queryData, queryValue);
        if (datas.isEmpty()) 
        {
            System.out.println("queryStaffByUserName(): No Staff with UserName: " + userName);
            return null;
        }

        return this.queryStaffData(datas.get(0).get(0).getValueStr());
    }

    // Private Info
    public Staff queryStaffPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryStaffRawDatas(queryData, queryValue);

        return this.getStaffData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryStaffRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Staffs this WHERE + " + queryValue + " = ?";
        List<String> rowNames = this.getStaffRowNames();
        List<DbType> rowTypes = this.getStaffRowTypes();

        System.out.println("===query Staff===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateStaffData(Staff staff)
    {
        String sql = """
            UPDATE Staffs SET
            Name = ?, UserName = ?, Password = ?, IsLogin = ?, ShopId = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromStaff(staff);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update Staff===");
        return this.updateData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteStaffData(String id, String userName)
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
    // ===Query===
    public List<String> getStaffRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("IsLogin");
        rowNames.add("ShopId");

        return rowNames;
    }

    public List<DbType> getStaffRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.INTEGER); // IsLogin
        rowTypes.add(DbType.TEXT);    // ShopId

        return rowTypes;
    }

    public Staff getStaffData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        // String shopId = data.get(5).getValueStr();

        return new Staff(id, name, userName, password, isLogin);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromStaff(Staff staff)
    {
        DbData id = new DbData(staff.getId());
        DbData name = new DbData(staff.getName());
        DbData userName = new DbData(staff.getUserName());
        DbData password = new DbData(staff.getPassword());
        DbData isLogin = new DbData(staff.getIsLogin() ? 1 : 0);
        DbData shopId = new DbData("NULL");
        if (staff.getShop() != null)
        {
            shopId = new DbData(staff.getShop().getId());
        }

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(isLogin);
        data.add(shopId);

        return data;
    }
}
