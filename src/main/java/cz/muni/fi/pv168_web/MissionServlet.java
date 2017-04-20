/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168_web;

import cz.muni.fi.pv168.IllegalEntityException;
import cz.muni.fi.pv168.Mission;
import cz.muni.fi.pv168.MissionManager;
import cz.muni.fi.pv168.ServiceFailureException;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author martin
 */
@WebServlet(MissionServlet.URL_MAPPING+"/*")
public class MissionServlet extends HttpServlet {
    
    private static final String LIST_JSP = "/index.jsp";
    public static final String URL_MAPPING = "/missions";
    private final static Logger log = LoggerFactory.getLogger(MissionServlet.class);
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        
        switch (action) {
            case "/add":
                String danger = request.getParameter("danger");
                String assignment = request.getParameter("assignment");
             
                if (assignment == null || assignment.length() == 0 || danger == null || danger.length() == 0) {
                    request.setAttribute("chyba", "Je nutne vyplnit vsetky hodnoty !");
                    log.debug("form data invalid");
                    showMissionList(request, response);
                    return;
                }
                
                try {
                    Mission mission = new Mission();
                    mission.setAssignment(assignment);
                    mission.setDanger(Integer.parseInt(danger));
                    getMissionManager().createMission(mission);
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot add mission", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    Mission mission = getMissionManager().findMissionById(id);
                    getMissionManager().deleteMission(mission);
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot delete mission", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                
                String myDanger = request.getParameter("danger");
                String myAssignment = request.getParameter("assignment");
                Long myId = Long.valueOf(request.getParameter("id"));
                if (myDanger == null || myDanger.length() == 0 || myAssignment == null || myAssignment.length() == 0) {
                    request.setAttribute("chyba", "Je nutne vyplnit vsetky hodnoty !");
                    log.debug("form data invalid");
                    showMissionList(request, response);
                    return;
                }
                
                try {
                    
                    
                    Mission myMission = new Mission();
                    
                    myMission.setId(myId);
                    myMission.setDanger(Integer.parseInt(myDanger));
                    myMission.setAssignment(myAssignment);
                    getMissionManager().updateMission(myMission);
                    
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot update mission", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
                
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }
    
    
    
    private MissionManager getMissionManager() {
        return (MissionManager) getServletContext().getAttribute("missionManager");
    }
    
    private void showMissionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            log.debug("showing table of missions");
            request.setAttribute("missions", getMissionManager().findAllMissions());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServiceFailureException e) {
            log.error("Cannot show missions", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("GET ...");
        showMissionList(request, response);
    }


}
