package com.ast.airlinesystem.data;

import com.ast.airlinesystem.logic.AirplaneType;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneTypeDao {

    DbConnection db = new DbConnection();
    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    public List typesList(){
        List<AirplaneType> list = new ArrayList<>();
        String sql = "select * from airplane_types";
        try{
            con = db.Connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                AirplaneType at = new AirplaneType();
                at.setId(rs.getString(1));
                at.setYear(Integer.parseInt(rs.getString(2)));
                at.setModel(rs.getString(3));
                at.setBrand(rs.getString(4));
                at.setPassengersQuantity(Integer.parseInt(rs.getString(5)));
                at.setRowsNumber(Integer.parseInt(rs.getString(6)));
                at.setColumnsNumber(Integer.parseInt(rs.getString(7)));
                list.add(at);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addType (AirplaneType type) throws  Exception{
        String insertStatement = "CALL PRC_INS_AVIONTYPE (?,?,?,?,?,?,?)";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(insertStatement);
            ps.setString(1, type.getId());
            ps.setString(2,Integer.toString(type.getYear()));
            ps.setString(3,type.getModel());
            ps.setString(4,type.getBrand());
            ps.setString(5,Integer.toString(type.getPassengersQuantity()));
            ps.setString(6,Integer.toString(type.getRowsNumber()));
            ps.setString(7,Integer.toString(type.getColumnsNumber()));
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El tipo ya existe");
            }

        }
        catch (Exception e){

        }
        return count;
    }

    public int deleteType (AirplaneType type) throws  Exception{
        String insertStatement = "delete from airplane_types where at_id = ?";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(insertStatement);
            ps.setString(1, type.getId());
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El tipo no existe");
            }

        }
        catch (Exception e){

        }
        return count;
    }

    public static AirplaneType toType(ResultSet rs) throws Exception{
        try{
            AirplaneType at = new AirplaneType();
            at.setId(rs.getString("at_id"));
            at.setYear(Integer.parseInt(rs.getString("year")));
            at.setModel(rs.getString("model"));
            at.setBrand(rs.getString("brand"));
            at.setPassengersQuantity(Integer.parseInt(rs.getString("passengers_quantity")));
            at.setRowsNumber(Integer.parseInt(rs.getString("rows_number")));
            at.setColumnsNumber(Integer.parseInt(rs.getString("columns_number")));
            return at;
        }
        catch (Exception e){
            return null;
        }
    }

    public AirplaneType getTypeById(String id) throws Exception{
        AirplaneType at = new AirplaneType();
        String sql = "SELECT * FROM AIRPLANE_TYPES  WHERE AT_ID = \'"+id+"\'";
        try{
            con = db.Connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                at.setId(rs.getString("at_id"));
                at.setYear(Integer.parseInt(rs.getString("year")));
                at.setModel(rs.getString("model"));
                at.setBrand(rs.getString("brand"));
                at.setPassengersQuantity(Integer.parseInt(rs.getString("passengers_quantity")));
                at.setRowsNumber(Integer.parseInt(rs.getString("rows_number")));
                at.setColumnsNumber(Integer.parseInt(rs.getString("columns_number")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return at;
    }

}
