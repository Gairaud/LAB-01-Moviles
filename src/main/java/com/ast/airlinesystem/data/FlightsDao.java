package com.ast.airlinesystem.data;

import com.ast.airlinesystem.logic.Airplane;
import com.ast.airlinesystem.logic.Flight;
import com.ast.airlinesystem.logic.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightsDao {
    
    DbConnection db = new DbConnection();
    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    public List<Flight> flightsList(){
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM FLIGHTS f";
        try{
            con = db.Connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Flight f = new Flight();
                f.setId(Integer.parseInt(rs.getNString(1)));
                f.setRoute(Model.instance().getRoute(rs.getString(2)));
                f.setDepartureDate(rs.getString(3));
                f.setReturnDate(rs.getString(4));
                f.setprice(Integer.parseInt(rs.getString(5)));
                f.setAvailableSeats(Integer.parseInt(rs.getString(6)));
                f.setPlane(Model.instance().getAirplaneById(rs.getString(7)));
                list.add(f);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addFlight (Flight flight) throws Exception{
        String insertStatement = "CALL PRC_INS_FLIGHTS(?,?,?,?,?,?)";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(insertStatement);
            ps.setString(1, flight.getRoute().getId());
            ps.setString(2, flight.getDepartureDate());
            ps.setString(3, flight.getReturnDate());
            ps.setString(4, Integer.toString(flight.getprice()));
            ps.setString(5, Integer.toString(flight.getAvailableSeats()));
            ps.setString(6, flight.getPlane().getId());
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El vuelo ya existe");
            }

        }
        catch (Exception e){

        }
        return count;
    }

    public static Flight toFlight(ResultSet rs) throws Exception {

        try{
            Flight f = new Flight();

                f.setId(Integer.parseInt(rs.getString("f_id")));
                f.setRoute(Model.instance().getRoute(rs.getString("ruta")));
                f.setDepartureDate(rs.getString("departure_date"));
                f.setReturnDate(rs.getString("return_date"));
                f.setprice(Integer.parseInt(rs.getString("price")));
                f.setAvailableSeats(Integer.parseInt(rs.getString("available_seats")));
     
                return f;
            
        } catch (SQLException ex) {
                return null;
        }
    }

    public int deleteF(Flight f) throws Exception{
        String deleteStatement = "delete from flights where f_id = ?";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(deleteStatement);
            ps.setString(1, String.valueOf(f.getId()));
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El tipo no existe");
            }
        }catch (Exception e){

        }
        return count;
    }
}

