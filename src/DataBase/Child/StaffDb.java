package DataBase.Child;

import DataBase.Base.AbstractDataBase;
import DataBase.Base.DataBaseData;
import DataBase.Base.DataBaseType;
import Obj.Main.Shop;
import Obj.Main.Staff;
import java.util.*;

public class StaffDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createStaffTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Staffs"
                + "("
                + "Id TEXT PRIMARY KEY UNIQUE NOT NULL, "
                + "Name TEXT NOT NULL, "
                + "UserName TEXT NOT NULL, "
                + "Password TEXT NOT NULL, "
                + "IsLogin INTEGER NOT NULL, "
                + "ShopId TEXT"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public boolean insertStaffData(Staff staff)
    {
        String sql = "INSERT INTO Staffs "
                + "(Id, Name, UserName, Password, IsLogin, ShopId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromStaff(staff);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public Staff queryStaffData(String id)
    {
        DataBaseData queryData = new DataBaseData(id);
        String sql = "SELECT * FROM Staffs WHERE Id = ?";
        List<String> rowNames = this.getStaffRowNames();
        List<DataBaseType> rowTypes = this.getStaffRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getStaff(datas.get(0));
    }

    public List<Staff> queryStaffsByShopId(String shopId)
    {
        DataBaseData queryData = new DataBaseData(shopId);
        String sql = "SELECT * FROM Staffs WHERE ShopId = ?";
        List<String> rowNames = this.getStaffRowNames();
        List<DataBaseType> rowTypes = this.getStaffRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<Staff> staffs;
        staffs = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            staffs.add(this.getStaff(datas.get(i)));
        }

        return staffs;
    }
    //===========================================Update===========================================
    public boolean updateStaffData(Staff staff)
    {
        String sql = "UPDATE Staffs SET * WHERE Id = ?";

        List<DataBaseData> data = this.getDataFromStaff(staff);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteStaffData(String id)
    {
        String sql = "DELETE FROM Staffs WHERE Id = ?";
        DataBaseData idData = new DataBaseData(id);
        return this.deleteRow(url, sql, idData);
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
        rowNames.add("IsLogin");
        rowNames.add("ShopId");

        return rowNames;
    }

    private List<DataBaseType> getStaffRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);    // Id
        rowTypes.add(DataBaseType.TEXT);    // Name
        rowTypes.add(DataBaseType.TEXT);    // UserName
        rowTypes.add(DataBaseType.TEXT);    // Password
        rowTypes.add(DataBaseType.INTEGER); // IsLogin
        rowTypes.add(DataBaseType.TEXT);    // ShopId

        return rowTypes;
    }

    private Staff getStaff(List<DataBaseData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        String shopId = data.get(5).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        return new Staff(id, name, userName, password, isLogin, shop);
    }

    // Update - Insert
    private List<DataBaseData> getDataFromStaff(Staff staff)
    {
        DataBaseData id = new DataBaseData(staff.getId());
        DataBaseData name = new DataBaseData(staff.getName());
        DataBaseData userName = new DataBaseData(staff.getUserName());
        DataBaseData password = new DataBaseData(staff.getPassword());
        DataBaseData isLogin = new DataBaseData(staff.getIsLogin() ? 1 : 0);
        DataBaseData shopId = new DataBaseData(staff.getShop().getId());

        List<DataBaseData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(isLogin);
        data.add(shopId);

        return data;
    }
}
