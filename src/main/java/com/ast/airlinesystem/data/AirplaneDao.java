package com.ast.airlinesystem.data;

import com.ast.airlinesystem.logic.AirplaneType;
import com.ast.airlinesystem.logic.Airplane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ast.airlinesystem.data.AirplaneTypeDao.toType;

public class AirplaneDao {

    DbConnection db = new DbConnection();
    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    public List getAirplanesList() {
        List<Airplane> list = new ArrayList<>();
        String sql = "select * from airplanes a, airplane_types x where x.at_id = a.airplane_type ";

        try{
            con = db.Connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Airplane airplane = new Airplane();
                airplane.setId(rs.getString(1));
                airplane.setType(toType(rs));
                list.add(airplane);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Airplane getAirplaneById(String id)  {
        Airplane plane  = new Airplane();

        String getStatement = "SELECT * from airplanes a, airplane_types x where a.id =\'"+id+"\'" +
                "and a.airplane_type = x.at_id";
        try{
            con = db.Connect();
            ps = con.prepareStatement(getStatement);
            rs = ps.executeQuery();
            while (rs.next()){
                try{
                    plane.setId(rs.getString(1));
                    plane.setType(toType(rs));
                }catch (Exception e){}

            }
        } catch (SQLException ex) {

        }

        return plane;
    }

    public int addAirplane(Airplane airplane) throws Exception{
        String insertStatement = "CALL PRC_INS_AVION (?,?)";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(insertStatement);
            ps.setString(1, airplane.getId());
            ps.setString(2, airplane.getType().getId());
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El tipo ya existe");
            }
        }catch (Exception e){

        }
        return count;
    }

    public int deleteAirplane(Airplane airplane) throws Exception{
        String deleteStatement = "delete from airplanes where id = ?";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(deleteStatement);
            ps.setString(1, airplane.getId());
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("El tipo no existe");
            }
        }catch (Exception e){

        }
        return count;
    }
}
