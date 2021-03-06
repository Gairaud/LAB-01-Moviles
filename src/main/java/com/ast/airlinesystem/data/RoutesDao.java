package com.ast.airlinesystem.data;

import com.ast.airlinesystem.logic.Model;
import com.ast.airlinesystem.logic.Routes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoutesDao {
    DbConnection db = new DbConnection();
    PreparedStatement ps;
    ResultSet rs;
    Connection con;


    public List RouteList(){
        List<Routes> list = new ArrayList<>();
        String sql = "SELECT * FROM Routes";
        try{
            con = db.Connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Routes at = new Routes();
                at.setId(rs.getString(1));
                at.setOrigin(rs.getString(2));
                at.setDestination(rs.getString(3));
                list.add(at);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int addRoute (Routes route) throws  Exception{
        String insertStatement = "CALL PRC_INS_ROUTE(?,?,?)";
        int count = 0;
        try{
            con = db.Connect();
            ps = con.prepareStatement(insertStatement);
            ps.setString(1, route.getId());
            ps.setString(2, route.getOrigin());
            ps.setString(3, route.getDestination());
            count = ps.executeUpdate();
            if(count == 0){
                throw new Exception("La ruta ya existe");
            }

        }
        catch (Exception e){

        }
        return count;
    }

    public Routes getRoute(String id){
        Routes route = new Routes();
        String getStatement = "SELECT * FROM routes where r_id =\'"+id+"\'";
        try{
            con = db.Connect();
            ps = con.prepareStatement(getStatement);
            rs = ps.executeQuery();
            while (rs.next()){
                try{
                    route.setId(rs.getString(1));
                    route.setOrigin(rs.getString(2));
                    route.setDestination(rs.getString(3));
                }catch (Exception e){}

            }
        } catch (SQLException ex) {

        }
        return route;
    }

    public void updateRoute(Routes route){

        String updateStatement = "CALL prc_upd_route(?,?,?)";
        try{
            con = db.Connect();
            ps = con.prepareStatement(updateStatement);
            ps.setString(1,route.getId());
            ps.setString(2,route.getOrigin());
            ps.setString(3,route.getDestination());
            ps.executeUpdate(updateStatement);

        }
        catch (Exception e){

        }

    }
}
