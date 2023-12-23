package org.example.logic.dao;

import org.example.logic.entitites.Huesped;
import org.example.logic.entitites.Reserve;
import org.example.logic.utils.JPAUtils;

import javax.persistence.EntityManager;

public class DaoReservation {

    private EntityManager em;
    public DaoReservation(EntityManager em){
        this.em=em;
    }
    public void save(Reserve reserve){
        this.em.persist(reserve);
    }

    public static void main(String[] args) {
      EntityManager em = JPAUtils.getEntityManager();
      em.getTransaction().begin();

      try{
          Huesped huesped = new Huesped();
          huesped.setName("Juan");
          huesped.setCellphone("123456789");
          huesped.setBirthdate(null);
          Reserve reserve = new Reserve();
          reserve.setHuesped(huesped);



          DaoReservation daoReservation = new DaoReservation(em);
            daoReservation.save(reserve);
            em.getTransaction().commit();
            em.close();

      }catch (Exception e){
          em.getTransaction().rollback();
          e.printStackTrace();
          e.printStackTrace();
      }
    }

}
