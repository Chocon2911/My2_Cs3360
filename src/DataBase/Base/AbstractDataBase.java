package DataBase.Base;

import Util.ObjUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataBase extends ObjUtil
{
    protected static String url = "/DataBase/ShopDataBase.db";
    
    //===========================================Other============================================
    private boolean setPreParedStatement(PreparedStatement preStatement, DataBaseData data, int index)
    {
        try
        {
            if (data.getDataBaseType() == DataBaseType.TEXT)
            {
                preStatement.setString(index, data.getValueStr());
            }
            else if (data.getDataBaseType() == DataBaseType.INTEGER)
            {
                preStatement.setInt(index, data.getValueInt());
            }
            else if (data.getDataBaseType() == DataBaseType.FLOAT)
            {
                preStatement.setFloat(index, data.getValueFloat());
            }
            else if (data.getDataBaseType() == DataBaseType.BLOB)
            {
                preStatement.setBlob(index, data.getValueBlob());
            }
            else
            {
                System.out.println("DataBaseType is null");
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }
    
    private boolean setDataBaseData(ResultSet resultSet, DataBaseType type, DataBaseData data, String name)
    {
        try
        {
            if (type == DataBaseType.TEXT)
            {
                data.setValueStr(resultSet.getString(name));
            }
            else if (type == DataBaseType.INTEGER)
            {
                data.setValueInt(resultSet.getInt(name));
            }
            else if (type == DataBaseType.FLOAT)
            {
                data.setValueFloat(resultSet.getFloat(name));
            }
            else if (type == DataBaseType.BLOB)
            {
                data.setValueBlob(resultSet.getBlob(name));
            }
            else
            {
                System.out.println("DataBaseType is null");
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    //=========================================Connection=========================================
    protected Connection getConnection(String url)
    {
        String dataBaseUrl = "jdbc:sqlite:." + url;
        try
        {
            Connection conn = DriverManager.getConnection(dataBaseUrl);
            if (conn == null)
            {
                System.out.println("Connection is null");
            }

            return conn;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //========================================Create Table========================================
    protected boolean createTable(String url, String executeLine)
    {
        Connection conn = getConnection(url);
        if (conn == null) return false;

        try (Statement statement = conn.createStatement())
        {
            statement.execute(executeLine);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //===========================================Insert===========================================
    protected boolean insertData(String url, String executeLine, List<DataBaseData> data)
    {
        Connection conn = getConnection(url);
        if (conn == null) return false;
        
        try (PreparedStatement preStatement = conn.prepareStatement(executeLine))
        {
            for (int i = 0; i < data.size(); i++)
            {
                if (!this.setPreParedStatement(preStatement, data.get(i), i + 1)) return false;
            }

            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //===========================================Query============================================
    protected List<List<DataBaseData>> queryDatas(String url, String executeLine, DataBaseData queryData, List<String> rowNames, List<DataBaseType> rowTypes)
    {
        Connection conn = getConnection(url);
        if (conn == null) return null;

        try (PreparedStatement preStatement = conn.prepareStatement(executeLine))
        {
            if (!this.setPreParedStatement(preStatement, queryData, 1)) return null;

            ResultSet resultSet = preStatement.executeQuery(executeLine);
            List<List<DataBaseData>> datas = new ArrayList<>();
            while (resultSet.next())
            {
                List<DataBaseData> data = new ArrayList<>();
                for (int i = 0; i < rowNames.size(); i++)
                {
                    DataBaseData newData = new DataBaseData();
                    if (!this.setDataBaseData(resultSet, rowTypes.get(i), newData, rowNames.get(i))) 
                    {
                        return null;
                    }

                    data.add(newData);
                }
                
                datas.add(data);
            }
            return datas;
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //===========================================Update===========================================
    protected boolean updateData(String url, String execute, DataBaseData whereData, List<DataBaseData> datas)
    {
        Connection conn = getConnection(url);
        if (conn == null) return false;

        try (PreparedStatement preStatement = conn.prepareStatement(execute))
        {
            if (!this.setPreParedStatement(preStatement, whereData, 1)) return false;
            for (int i = 0; i < datas.size(); i++)
            {
                if (!this.setPreParedStatement(preStatement, datas.get(i), i + 2)) return false;
            }

            preStatement.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    //===========================================Delete===========================================
    protected boolean deleteRow(String url, String execute, DataBaseData data)
    {
        Connection conn = getConnection(url);
        if (conn == null) return false;

        try (PreparedStatement preStatement = conn.prepareStatement(execute))
        {
            if (!this.setPreParedStatement(preStatement, data, 1)) return false;
            
            preStatement.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
