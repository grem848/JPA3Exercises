package querytester;

import entity.Customer;
import entity.Employee;
import facade.Facade;
import javax.persistence.Persistence;

public class QueryRunner
{

    public static void main(String[] args)
    {
        Facade facade = new Facade(Persistence.createEntityManagerFactory("pu"));

//        //Create
//        Employee c1 = new Employee(0, "Hansen", "Per", "x101", "test@mail.com", "Boss");
//        System.out.println("Create Employee: " + facade.createEmployee(c1));
//
//        //Get
//        System.out.println("Find customer: " + facade.findCustomer(103));
//        
//        
//        //Update
//        Customer c2 = new Customer(0, "What", "Svensson", "Lars", "112", "Lyngby bag papkassen", "Lyngby", "Denmark");
//        c2.setCustomerNumber(103);
//        System.out.println("Update Customer: " + facade.updateCustomer(c2));
//
//        //Delete
//        Customer c3 = new Customer();
//        c3.setCustomerNumber(112);
//        System.out.println("Delete Customer: " + facade.deleteCustomer(c3));
        facade.getEmployeeCount(); // default 23
        System.out.println("---");
        facade.getAllEmployees();
        System.out.println("---");
        facade.getCustomerInCity("NYC");
        System.out.println("---");
        facade.getEmployeeMaxCustomers();
        System.out.println("---");
        facade.getOrdersOnHold();
        System.out.println("---");
        facade.getOrdersOnHold(144);
        System.out.println("---");
        facade.getCustomerNamesSorted();
    }

}
