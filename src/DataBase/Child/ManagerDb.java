package DataBase.Child;

import DataBase.Base.AbstractDb;
import DataBase.Base.DbData;
import DataBase.Base.DbType;
import Obj.Data.Manager;
import Obj.Data.Shop;
import java.util.ArrayList;
import java.util.List;

public class ManagerDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createManagerTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Managers"
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
    public String insertManagerData(Manager manager)
    {
        String sql = "INSERT INTO Managers "
                + "(Id, Name, UserName, Password, ShopId) "
                + "VALUES (?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromManager(manager);
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(manager.getId());
            if (idE != null) return idE;

            String userNameE = new UserNameDb().insertUserName(manager.getUserName());
            if (userNameE != null) return userNameE;
        }

        return result;
    }
    
    //===========================================Query============================================
    public Manager queryManagerData(String id)
    {
        String sql = """
            SELECT * FROM Managers this WHERE Id = ?
            LEFT JOIN Shops ON this.ShopId = Shops.Id
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getManagerData(datas);
    }

    public Manager queryManagerPriData(String id)
    {
        String sql = "SELECT * FROM Managers this WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getManagerRowNames();
        List<DbType> rowTypes = this.getManagerRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getManagerPriData(datas.get(0), 0);
    }

    //===========================================Update===========================================
    public String updateManagerData(Manager manager)
    {
        String sql = "UPDATE Managers SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromManager(manager);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteManagerData(Manager manager)
    {
        String sql = "DELETE FROM Managers WHERE Id = ?";

        DbData idData = new DbData();
        idData.setValueStr(manager.getId());

        boolean result = this.deleteRow(url, sql, idData);
        if (result) 
        {
            new IdDb().deleteId(manager.getId());
            new UserNameDb().deleteUserName(manager.getUserName());
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    // Manager Pri
    public List<String> getManagerRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("ShopId");

        return rowNames;
    }

    public List<DbType> getManagerRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.TEXT);    // ShopId

        return rowTypes;
    }

    public Manager getManagerPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        String userName = data.get(begin + 2).getValueStr();
        String password = data.get(begin + 3).getValueStr();
        // String shopId = data.get(begin + 4).getValueStr();

        return new Manager(id, name, userName, password);
    }

    // Manager
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getManagerRowNames();
        for (String name : new ShopDb().getShopRowNames()) rowNames.add(name);
        
        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getManagerRowTypes();
        for (DbType type : new ShopDb().getShopRowTypes()) rowTypes.add(type);
        
        return rowTypes;
    }

    private Manager getManagerData(List<List<DbData>> data)
    {
        List<DbData> managerData = data.get(0);
        Manager manager = this.getManagerPriData(managerData, 0);
        if (manager.getId() == null) return null;

        Shop shop = null;
        for (List<DbData> shopData : data)
        {
            Shop newShop = new ShopDb().getShopPriData(shopData, 5);
            if (newShop.getId() == null) continue;
            shop = newShop;
            break;
        }

        manager.setShop(shop);
        return manager;
    }

    // Update - Insert
    private List<DbData> getDataFromManager(Manager manager)
    {
        DbData id = new DbData(manager.getId());
        DbData name = new DbData(manager.getName());
        DbData userName = new DbData(manager.getUserName());
        DbData password = new DbData(manager.getPassword());
        DbData shopId = new DbData(manager.getShop().getId());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(shopId);

        return data;
    }
}