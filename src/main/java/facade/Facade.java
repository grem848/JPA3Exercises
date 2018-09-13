package facade;

import entity.Customer;
import entity.Employee;
import entity.Office;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public static void main(String[] args)
    {
        
        Facade facade = new Facade(Persistence.createEntityManagerFactory("pu"));

        //Create
        Employee c1 = new Employee(0, "Hansen", "Per", "x101", "test@mail.com", "Boss");
        System.out.println("Create Employee: " + facade.createEmployee(c1));

        //Get
        System.out.println("Find customer: " + facade.findCustomer(103));
        
        
        //Update
        Customer c2 = new Customer(0, "What", "Svensson", "Lars", "112", "Lyngby bag papkassen", "Lyngby", "Denmark");
        c2.setCustomerNumber(103);
        System.out.println("Update Customer: " + facade.updateCustomer(c2));

        //Delete
        Customer c3 = new Customer();
        c3.setCustomerNumber(112);
        System.out.println("Delete Customer: " + facade.deleteCustomer(c3));
    }

}
