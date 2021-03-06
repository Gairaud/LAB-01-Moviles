package com.ast.airlinesystem.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import com.ast.airlinesystem.logic.Country;
import com.ast.airlinesystem.logic.Model;
import com.ast.airlinesystem.logic.User;
import jakarta.servlet.http.*;
import com.google.gson.*;


public class countryServlet extends HttpServlet {
    private String message;
    private final Gson gsonObject = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //String action = request.getParameter("action");
        switch (request.getServletPath()) {

            case "/get-countries":{

                try {
                    List<Country> countryList = Model.instance().getCountries();
                    String allCountries = gsonObject.toJson(countryList);
                    PrintWriter out = response.getWriter();
                    out.print(allCountries);
                    out.flush();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;

                }
                break;
            }

            case "/get-country":{
                try {
                    String param = request.getParameter("countryid");
                    Country country = Model.instance().getCountry(param);
                    String Country = gsonObject.toJson(country);
                    PrintWriter out = response.getWriter();
                    out.print(Country);
                    out.flush();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;

                }
                break;
            }

            case "/add-country":{
                try {
                Country country = new Country();
                country.setId(request.getParameter("id"));
                country.setName(request.getParameter("name"));

                Model.instance().addCountry(country);
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;

                }
                break;
            }
            case "/upd-country":{
                try {
                Country country = new Country();
                country.setId(request.getParameter("id"));
                country.setName(request.getParameter("name"));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
