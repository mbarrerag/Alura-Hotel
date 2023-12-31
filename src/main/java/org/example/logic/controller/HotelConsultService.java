package org.example.logic.controller;
import org.example.views.SearchReservations;
import org.example.logic.dao.DaoHuesped;
import org.example.logic.dao.DaoReservation;
import org.example.logic.entitites.Huesped;
import org.example.logic.entitites.Reserve;
import org.example.logic.utils.JPAUtils;
import org.example.views.SearchReservations;

import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HotelConsultService {



    public static void loadDataReserve() {

        DefaultTableModel modelo = (DefaultTableModel) SearchReservations.tbReservas.getModel();
        modelo.setRowCount(0);

        EntityManager em = JPAUtils.getEntityManager();
        DaoReservation reservaDao = new DaoReservation(em);
        List<Reserve> reserves = reservaDao.getAllReservas();

        for (Reserve reserve : reserves) {

            System.out.println(reserve.getReservationNumber());
            Object[] fila = new Object[]{
                    reserve.getCheckIn(),
                    reserve.getCheckOut(),
                    reserve.getBookingValue(),
                    reserve.getPaymentMethod(),
                    reserve.getReservationNumber(),


            };
            modelo.addRow(fila);
        }
    }

    public static void loadDataHuesped() {

        DefaultTableModel modelo = (DefaultTableModel) SearchReservations.tbHuespedes.getModel();
        modelo.setRowCount(0);

        EntityManager em = JPAUtils.getEntityManager();
        DaoHuesped huespedDao = new DaoHuesped(em);
        List<Huesped> huespeds = huespedDao.getAllHuespedes();

        for (Huesped huesped : huespeds) {
            List<Reserve> reserves = huesped.getReserves();
            long numreservas=reserves.size();
            Object[] fila = new Object[]{
                    huesped.getId(),
                    huesped.getName(),
                    huesped.getSurename(),
                    huesped.getBirthdate(),
                    huesped.getNationality(),
                    huesped.getCellphone(),
                    huesped.getId(),
            };
            modelo.addRow(fila);

        }
    }


    public static void UpdateReserve() throws ParseException {

        EntityManager em = JPAUtils.getEntityManager();
        DaoReservation reserveDao = new DaoReservation(em);
        int selectedRow = SearchReservations.tbReservas.getSelectedRow();

        if (selectedRow != -1) {

            Long idReserve = Long.valueOf(SearchReservations.tbReservas.getSelectedRow()) + 1;
            System.out.println(idReserve);
            Reserve reserve = reserveDao.getById(idReserve);

           // reserve.setCheckIn((Date) SearchReservations.tbReservas.getValueAt(selectedRow, 1));

            BigDecimal timestampValue = (BigDecimal) SearchReservations.tbReservas.getValueAt(selectedRow, 2);
            Date date = new Date(timestampValue.longValue());
            reserve.setCheckOut(date);



            String strBooking = (String) SearchReservations.tbReservas.getValueAt(selectedRow, 2);
            BigDecimal bookingValue = new BigDecimal(strBooking);
            reserve.setBookingValue(bookingValue);


            reserve.setPaymentMethod((String) SearchReservations.tbReservas.getValueAt(selectedRow, 3));

            reserveDao.update(reserve);
            em.refresh(reserve);
            loadDataReserve();

        }
    }

    public static void updateHuesped() {

        EntityManager em = JPAUtils.getEntityManager();

        DaoHuesped huespedDao = new DaoHuesped(em);
        int selectedRow = SearchReservations.tbHuespedes.getSelectedRow();
        if (selectedRow != -1) {
            Long idHuesped = (Long) SearchReservations.tbHuespedes.getValueAt(selectedRow, 0);
            Huesped huesped = huespedDao.getById(idHuesped);

            // Resto del código para actualizar el huésped...
            huesped.setName((String)SearchReservations.tbHuespedes.getValueAt(selectedRow,1));
            huesped.setSurename((String)SearchReservations.tbHuespedes.getValueAt(selectedRow,2));
            huesped.setBirthdate((Date) SearchReservations.tbHuespedes.getValueAt(selectedRow,3));
            huesped.setNationality((String) SearchReservations.tbHuespedes.getValueAt(selectedRow,4));
            huesped.setCellphone((String) SearchReservations.tbHuespedes.getValueAt(selectedRow,5));

            huespedDao.update(huesped);
            // Actualizar la tabla después de la edición
            loadDataHuesped();
        }
    }

    public static void deletedReserve() {
        EntityManager em = JPAUtils.getEntityManager();
        DaoReservation reserveDao = new DaoReservation(em);
        int selectedRow = SearchReservations.tbReservas.getSelectedRow();
        if (selectedRow != -1) {
            Long idReserve = Long.valueOf(SearchReservations.tbReservas.getSelectedRow()) + 1;
            Reserve reserve = reserveDao.getById(idReserve);
            reserveDao.deleted(reserve.getId());
            loadDataReserve();
        }

    }

    public static void deletedHusped() {

        EntityManager em = JPAUtils.getEntityManager();
        DaoHuesped huespedDao = new DaoHuesped(em);
        int selectedRow = SearchReservations.tbHuespedes.getSelectedRow();
        if (selectedRow != -1) {
            Long idHuesped = (Long) SearchReservations.tbHuespedes.getValueAt(selectedRow, 0);
            Huesped huesped = huespedDao.getById(idHuesped);
            System.out.println(huesped.getName());
            huespedDao.deleted(huesped.getId());
            loadDataHuesped();
        }
    }



    public static void getByCellphone( String cellphone) {
        EntityManager em = JPAUtils.getEntityManager();
        DaoHuesped huespedDao = new DaoHuesped(em);
        Huesped huesped = huespedDao.getByCellphone(cellphone);
        DefaultTableModel modelo = (DefaultTableModel) SearchReservations.tbHuespedes.getModel();
        modelo.setRowCount(0);
        Object[] fila = new Object[]{
                huesped.getId(),
                huesped.getName(),
                huesped.getSurename(),
                huesped.getBirthdate(),
                huesped.getNationality(),
                huesped.getCellphone(),

        };
        modelo.addRow(fila);


    }

 }


