package facade;

import entity.CMOrder;
import entity.Customer;
import entity.Employee;
import entity.Office;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Facade
{

    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public Employee createEmployee(Employee emp)
    {
        EntityManager em = getEntityManager();
        try
        {
            em.getTransaction().begin();

            Office office = new Office("1");
            emp.setOffice(office);
            em.persist(emp);

            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return emp;
    }

    public Customer updateCustomer(Customer cust)
    {

        EntityManager em = getEntityManager();

        try
        {
            em.getTransaction().begin();
            Customer c = em.find(Customer.class, cust.getCustomerNumber());
            if (c != null)
            {
                em.merge(cust);
            }
            em.getTransaction().commit();
            return c;
        } finally
        {
            em.close();
        }
    }

    public Customer findCustomer(int id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Customer.class, id);

        } finally
        {
            em.close();
        }
    }

    public Customer deleteCustomer(Customer customer)
    {
        EntityManager em = getEntityManager();

        try
        {
            em.getTransaction().begin();
            Customer p = em.find(Customer.class, customer.getCustomerNumber());
            if (p != null)
            {
                em.remove(p);
            }
            em.getTransaction().commit();
            return p;
        } finally
        {
            em.close();
        }
    }

    public long getEmployeeCount()
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("SELECT COUNT(c) FROM Employee c");
        long result = (long) q.getSingleResult();
        System.out.println("Number of employees: " + result);
        return result;

    }

    public List<Employee> getAllEmployees()
    {
        EntityManager em = getEntityManager();

        TypedQuery<Employee> qt = em.createNamedQuery("Employee.findAll", Employee.class);
        List<Employee> employeeList = qt.getResultList();
        System.out.println("List of Employees: \n" + employeeList);
        return employeeList;

    }

    public void getCustomerInCity(String city)
    {
        EntityManager em = getEntityManager();

        TypedQuery<Customer> q = em.createNamedQuery("Customer.findByCity", Customer.class);
        q.setParameter("city", city);
        List<Customer> customerList = q.getResultList();
        System.out.println("List of Customers in city: " + city);
        System.out.println(customerList);

    }

    public void getEmployeeMaxCustomers() // DOESNT WORK
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select c from Employee c where c.employeeNumber=(SELECT MAX(c.employee.employeeNumber) from Customer c)");

        System.out.println(q.getResultList());
        Employee e = (Employee) q.getSingleResult();
        System.out.println("Employee with max customers: " + e.toString() + " ");
    }

    public void getOrdersOnHold()
    {
        EntityManager em = getEntityManager();

        TypedQuery<CMOrder> q = em.createNamedQuery("CMOrder.findByStatus", CMOrder.class);
        q.setParameter("status", "On Hold");
        List<CMOrder> orderList = q.getResultList();
        System.out.println("List of orders on hold: ");
        System.out.println(orderList);

    }

    public void getOrdersOnHold(int customerNumber)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select c from CMOrder c where c.status ='On Hold' AND c.customer.customerNumber = :customer").setParameter("customer", customerNumber);
        System.out.println("Orders on hold for " + customerNumber + ": \n" + q.getResultList());
    }

    public List<String> getCustomerNamesSorted() // HOW DO I RETURN A STRING LIST???????
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("SELECT DISTINCT c.customerName from Customer c ORDER BY c.customerName");
        List<Customer> list = q.getResultList();
        System.out.println("Names sorted: " + list);
        
        return null;
    }
}
