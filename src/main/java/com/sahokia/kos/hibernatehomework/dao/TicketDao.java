package com.sahokia.kos.hibernatehomework.dao;

import com.sahokia.kos.hibernatehomework.model.Ticket;
import com.sahokia.kos.hibernatehomework.model.TicketType;
import com.sahokia.kos.hibernatehomework.providers.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TicketDao {

    public Ticket findById(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        session.getTransaction().commit();
        session.close();
        return ticket;
    }

    public void saveTicket(Ticket ticket) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteTicket(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        session.remove(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public void updateTicketType(int id, TicketType newType) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        ticket.setTicketType(newType);
        session.merge(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public List<Ticket> findTicketsByUserId(int userId) {
            Session session = SessionFactoryProvider.getSessionFactory().openSession();
            String hql = "FROM Ticket t WHERE t.user.id = :userId";
            Query<Ticket> query = session.createQuery(hql, Ticket.class);
            query.setParameter("userId", userId);
            session.close();
            return query.list();
    }
}
