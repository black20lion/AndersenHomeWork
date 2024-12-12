package com.sahokia.kos.hibernatehomework.dao;

import com.sahokia.kos.hibernatehomework.model.Customer;
import com.sahokia.kos.hibernatehomework.model.Ticket;
import com.sahokia.kos.hibernatehomework.providers.SessionFactoryProvider;
import org.hibernate.Session;

import java.util.List;

public class CustomerDao {

    public Customer findById(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.getTransaction().commit();
        session.close();
        return customer;
    }

    public void saveCustomer(Customer customer) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteCustomer(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.remove(customer);
        session.getTransaction().commit();
        session.close();
    }

    public void updateCustomerAndTickets(Customer customer, List<Ticket> tickets) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(customer);
        for (Ticket ticket : tickets) {
            ticket.setCustomer(customer);
            session.merge(ticket);
        }
        session.getTransaction().commit();
        session.close();
    }
}

