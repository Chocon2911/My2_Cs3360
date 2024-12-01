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
        String sql = """
            SELECT * 
            FROM Staffs this
            WHERE Id = ?
            LEFT JOIN Shops ON this.ShopId = Shops.Id
            LEFT JOIN CustomerRequests ON this.Id = CustomerRequests.StaffId         
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getStaffData(datas);
    }

    public Staff queryStaffPriData(String id)
    {
        String sql = "SELECT * FROM Staffs this WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getStaffRowNames();
        List<DbType> rowTypes = this.getStaffRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getStaffPriData(datas.get(0), 0);
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
    // ===Query===
    // Staff Pri
    public List<String> getStaffRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
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
        rowTypes.add(DbType.TEXT);    // ShopId

        return rowTypes;
    }

    public Staff getStaffPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        String userName = data.get(begin + 2).getValueStr();
        String password = data.get(begin + 3).getValueStr();
        // String shopId = data.get(begin + 4).getValueStr();

        return new Staff(id, name, userName, password);
    }

    // Staff
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getStaffRowNames();
        for (String name : new ManagerDb().getManagerRowNames()) rowNames.add(name);
        for (String name : new StaffDb().getStaffRowNames()) rowNames.add(name);
        for (String name : new CustomerDb().getCustomerRowNames()) rowNames.add(name);
        for (String name : new ItemDb().getItemRowNames()) rowNames.add(name);
        for (String name : new CustomerRequestDb().getCustomerRequestRowNames()) rowNames.add(name);

        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getStaffRowTypes();
        for (DbType type : new ManagerDb().getManagerRowTypes()) rowTypes.add(type);
        for (DbType type : new StaffDb().getStaffRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerDb().getCustomerRowTypes()) rowTypes.add(type);
        for (DbType type : new ItemDb().getItemsRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerRequestDb().getCustomerRequestRowTypes()) rowTypes.add(type);

        return rowTypes;
    }

    private Staff getStaffData(List<List<DbData>> datas)
    {
        List<DbData> staffData = datas.get(0);
        Staff staff = this.getStaffPriData(staffData, 0);
        if (staff.getId() == null) return null;

        List<Shop> shops = new ArrayList<>();
        for (List<DbData> shopData : datas)
        {
            Shop newShop = new ShopDb().getShopPriData(shopData, 5);
            if (newShop.getId() == null) continue;
            shops.add(newShop);
        }

        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest newCustomerRequest = new CustomerRequestDb().getCustomerRequestPriData(customerRequestData, 16);
            if (newCustomerRequest.getId() == null) continue;
            customerRequests.add(newCustomerRequest);
        }

        staff.setShop(shops.get(0));
        staff.setCustomerRequests(customerRequests);
        return staff;
    }

    // ===Update - Insert===
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
