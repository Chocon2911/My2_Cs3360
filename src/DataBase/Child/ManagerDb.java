package DataBase.Child;

import DataBase.Base.AbstractDataBase;
import DataBase.Base.DataBaseData;
import DataBase.Base.DataBaseType;
import Obj.Main.Manager;
import Obj.Main.Shop;
import java.util.ArrayList;
import java.util.List;

public class ManagerDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createManagerTable()
    {
        String sql = "CREAT TABLE IF NOT EXISTS Managers"
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
    public boolean insertManagerData(Manager manager)
    {
        String sql = "INSERT INTO Managers "
                + "(Id, Name, UserName, Password, IsLogin, ShopId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromManager(manager);
        return this.insertData(url, sql, data);
    }
    
    //===========================================Query============================================
    public Manager queryManagerData(String id)
    {
        DataBaseData queryData = new DataBaseData(id);
        String sql = "SELECT * FROM Managers WHERE Id = ?";
        List<String> rowNames = this.getManagerRowNames();
        List<DataBaseType> rowTypes = this.getManagerRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getManager(datas.get(0));
    }

    public List<Manager> queryManagersByShopId(String shopId)
    {
        String sql = "SELECT * FROM Managers WHERE ShopId = ?";
        DataBaseData queryData = new DataBaseData(shopId);
        List<String> rowNames = this.getManagerRowNames();
        List<DataBaseType> rowTypes = this.getManagerRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<Manager> managers = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            Manager manager = this.getManager(datas.get(i));
            managers.add(manager);
        }
        
        return managers;
    }

    //===========================================Update===========================================
    public boolean updateManagerData(Manager manager)
    {
        String sql = "UPDATE Managers SET * WHERE Id = ?";

        List<DataBaseData> data = this.getDataFromManager(manager);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteManagerData(String id)
    {
        String sql = "DELETE FROM Managers WHERE Id = ?";

        DataBaseData idData = new DataBaseData();
        idData.setValueStr(id);

        return this.deleteRow(url, sql, idData);
    }

    //===========================================Other============================================
    // Query
    private List<String> getManagerRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("IsLogin");
        rowNames.add("ShopId");

        return rowNames;
    }

    private List<DataBaseType> getManagerRowTypes()
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

    private Manager getManager(List<DataBaseData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        String shopId = data.get(5).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        return new Manager(id, name, userName, password, isLogin, shop);
    }

    // Update - Insert
    private List<DataBaseData> getDataFromManager(Manager manager)
    {
        DataBaseData id = new DataBaseData(manager.getId());
        DataBaseData name = new DataBaseData(manager.getName());
        DataBaseData userName = new DataBaseData(manager.getUserName());
        DataBaseData password = new DataBaseData(manager.getPassword());
        DataBaseData isLogin = new DataBaseData(manager.getIsLogin() ? 1 : 0);
        DataBaseData shopId = new DataBaseData(manager.getShop().getId());

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
