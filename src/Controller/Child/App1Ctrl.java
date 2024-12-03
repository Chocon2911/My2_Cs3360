package Controller.Child;

import DataBase.Child.CustomerDb;
import DataBase.Child.ManagerDb;
import DataBase.Child.StaffDb;
import Obj.Data.Customer;
import Obj.Data.Manager;
import Obj.Data.Staff;
import Util.ObjUtil;

public class App1Ctrl
{
    //===========================================Login============================================
    public int login(String userName, String password)
    {
        Customer customer = CustomerDb.getInstance().queryCustomerByUserName(userName);
        if (customer != null)
        {
            if (customer.getPassword().equals(password)) // Login Customer
            {
                customer.setIsLogin(true);
                return 1;
            }
            else return 2;
        }

        Staff staff = StaffDb.getInstance().queryStaffByUserName(userName);
        if (staff != null)
        {
            if (staff.getPassword().equals(password)) return 3; // Login Staff
            else return 4;
        }

        Manager manager = ManagerDb.getInstance().queryManagerByUserName(userName);
        if (manager != null)
        {
            if (manager.getPassword().equals(password)) return 5; // Login Manager
            else return 6; 
        }

        return 0;
    }

    public String getCustomerId(String userName, String password)
    {
        Customer customer = CustomerDb.getInstance().queryCustomerByUserName(userName);
        if (customer == null) return null;
        else if (!customer.getPassword().equals(password)) return null;
        
        return customer.getId();
    }

    public String getStaffId(String userName, String password)
    {
        Staff staff = StaffDb.getInstance().queryStaffByUserName(userName);
        if (staff == null) return null;
        else if (!staff.getPassword().equals(password)) return null;
        
        return staff.getId();
    }

    public String getManagerId(String userName, String password)
    {
        Manager manager = ManagerDb.getInstance().queryManagerByUserName(userName);
        if (manager == null) 
        {
            System.out.println("getManagerId(): wrong userName: " + userName);
            return null;
        }
        else if (!manager.getPassword().equals(password))
        {
            System.out.println("getManagerId(): wrong password: " + password);
            return null;
        }
        
        return manager.getId();
    }

    //===========================================SignUp===========================================
    public int signUp(String name, String userName, String password)
    {
        String id = ObjUtil.getInstance().getRandomStr(10);
        Customer customer = new Customer(id, name, userName, password, false, 0);

        String e = CustomerDb.getInstance().insertCustomerData(customer);
        if (e == null) return 0;
        else if (e.contains("Customers.Id"))
        {
            System.out.println("signUp() Error: Id already exists");
            return this.signUp(name, userName, password);
        } 
        else if (e.contains("Customers.UserName")) return 1;

        return 0;
    }
}
