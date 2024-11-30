package DataBase.Base;

import Util.ObjUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDb extends ObjUtil
{
    protected static String url = "src/DataBase/ShopDataBase.db";
    
    //===========================================Other============================================
    private boolean setPreParedStatement(PreparedStatement preStatement, DbData data, int index)
    {
        try
        {
            if (data.getDataBaseType() == DbType.TEXT)
            {
                System.out.println(data.getValueStr());
                preStatement.setString(index, data.getValueStr());
            }
            else if (data.getDataBaseType() == DbType.INTEGER)
            {
                System.out.println(data.getValueInt());
                preStatement.setInt(index, data.getValueInt());
            }
            else if (data.getDataBaseType() == DbType.FLOAT)
            {
                System.out.println(data.getValueFloat());
                preStatement.setFloat(index, data.getValueFloat());
            }
            else if (data.getDataBaseType() == DbType.BLOB)
            {
                System.out.println(data.getValueBlob());
                preStatement.setBlob(index, data.getValueBlob());
            }
            else
            {
                System.out.println("DataBaseType is null, Order: " + index);
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("setPreparedStatement ERROR: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }
    
    private boolean setDataBaseData(ResultSet resultSet, DbType type, DbData data, String name)
    {
        try
        {
            if (type == DbType.TEXT)
            {
                System.out.println(data.getValueStr());
                data.setValueStr(resultSet.getString(name));
            }
            else if (type == DbType.INTEGER)
            {
                System.out.println(data.getValueInt());
                data.setValueInt(resultSet.getInt(name));
            }
            else if (type == DbType.FLOAT)
            {
                System.out.println(data.getValueFloat());
                data.setValueFloat(resultSet.getFloat(name));
            }
            else if (type == DbType.BLOB)
            {
                System.out.println(data.getValueBlob());
                data.setValueBlob(resultSet.getBlob(name));
            }
            else
            {
                System.out.println("DataBaseType is null");
                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            System.out.println("setDataBaseData ERROR: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //=========================================Connection=========================================
    protected Connection getConnection(String url)
    {
        String dataBaseUrl = "jdbc:sqlite:" + url;
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
    protected String insertData(String url, String executeLine, List<DbData> data)
    {
        Connection conn = getConnection(url);
        if (conn == null) throw new RuntimeException("Connection is null");
        
        try (PreparedStatement preStatement = conn.prepareStatement(executeLine))
        {
            for (int i = 0; i < data.size(); i++)
            {
                if (!this.setPreParedStatement(preStatement, data.get(i), i + 1))
                {
                    throw new RuntimeException("setPreParedStatement error");
                }
            }

            preStatement.execute();
            return null;
        }
        catch (Exception e)
        {
            System.out.println("inserData() ERROR: " + e.getMessage());
            return e.getMessage();
        }
    }

    //===========================================Query============================================
    protected List<List<DbData>> queryDatas(String url, String executeLine, DbData queryData, List<String> rowNames, List<DbType> rowTypes)
    {
        Connection conn = getConnection(url);
        if (conn == null) return null;

        try (PreparedStatement preStatement = conn.prepareStatement(executeLine))
        {
            if (!this.setPreParedStatement(preStatement, queryData, 1)) return null;

            ResultSet resultSet = preStatement.executeQuery();
            List<List<DbData>> datas = new ArrayList<>();
            while (resultSet.next())
            {
                List<DbData> data = new ArrayList<>();
                for (int i = 0; i < rowNames.size(); i++)
                {
                    DbData newData = new DbData();
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
    protected String updateData(String url, String execute, DbData whereData, List<DbData> datas)
    {
        Connection conn = getConnection(url);
        if (conn == null) throw new RuntimeException("Connection is null");

        try (PreparedStatement preStatement = conn.prepareStatement(execute))
        {
            if (!this.setPreParedStatement(preStatement, whereData, 1))
            {
                throw new RuntimeException("setPreParedStatement error");
            }
            for (int i = 0; i < datas.size(); i++)
            {
                if (!this.setPreParedStatement(preStatement, datas.get(i), i + 2)) 
                {
                    throw new RuntimeException("setPreParedStatement error");
                }
            }

            preStatement.executeUpdate();
            return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    //===========================================Delete===========================================
    protected boolean deleteRow(String url, String execute, DbData data)
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
