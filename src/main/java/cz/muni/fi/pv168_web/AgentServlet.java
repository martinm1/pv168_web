/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168_web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.pv168.Agent;
import cz.muni.fi.pv168.AgentManager;
import cz.muni.fi.pv168.ServiceFailureException;
import cz.muni.fi.pv168.IllegalEntityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author martin
 */
//@WebServlet(name = "AgentServlet", urlPatterns = {"/AgentServlet"})
@WebServlet(AgentServlet.URL_MAPPING + "/*")
public class AgentServlet extends HttpServlet {
    private static final String LIST_JSP = "/list.jsp";
    public static final String URL_MAPPING = "/agents";
    
    private final static Logger log = LoggerFactory.getLogger(AgentServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showAgentsList(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/add":
                //getting POST parameters from form
                String name = request.getParameter("name");
                String workingSince = request.getParameter("workingSince");
                String compromised = request.getParameter("compromised");
                //form data validity check
                if (name == null || name.length() == 0 || workingSince == null || workingSince.length() == 0 || compromised == null || compromised.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                if (!compromised.equals("true") && !compromised.equals("false")) {
                    request.setAttribute("chyba", "Compromised musí být true nebo false !");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                LocalDateTime dateWorkingSinceAdd;
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    dateWorkingSinceAdd = LocalDateTime.parse(workingSince, formatter);
                }catch(DateTimeParseException e){
                    request.setAttribute("chyba", "Chybně vyplněné datum ! Správný formát má například tohle datum: 1984-01-14 10:40");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                //form data processing - storing to database
                try {
                    Agent agent = new Agent();
                    //agent.setId(null);
                    agent.setName(name);
                    agent.setWorkingSince(dateWorkingSinceAdd);
                    agent.setCompromised(Boolean.parseBoolean(compromised));
                    getAgentManager().createAgent(agent);
                    
                    //redirect-after-POST protects from multiple submission
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot add agent", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getAgentManager().deleteAgent(getAgentManager().findAgentById(id));
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot delete agent", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                //TODO
                String myname = request.getParameter("name");
                String myworkingSince = request.getParameter("workingSince");
                String mycompromised = request.getParameter("compromised");
                Long myid = Long.valueOf(request.getParameter("id"));
                if (myname == null || myname.length() == 0 || myworkingSince == null || myworkingSince.length() == 0 || mycompromised == null || mycompromised.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                if (!mycompromised.equals("true") && !mycompromised.equals("false")) {
                    request.setAttribute("chyba", "Compromised musí být true nebo false !");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                LocalDateTime dateWorkingSince;
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    dateWorkingSince = LocalDateTime.parse(myworkingSince, formatter);
                }catch(DateTimeParseException e){
                    request.setAttribute("chyba", "Chybně vyplněné datum ! Správný formát má například tohle datum: 1984-01-14 10:40");
                    log.debug("form data invalid");
                    showAgentsList(request, response);
                    return;
                }
                try {
                    
                    
                    Agent myagent = new Agent();
                    //agent.setId(null);
                    myagent.setId(myid);
                    myagent.setName(myname);
                    myagent.setWorkingSince(dateWorkingSince);
                    myagent.setCompromised(Boolean.parseBoolean(mycompromised));
                    getAgentManager().updateAgent(myagent);
                    
                    //redirect-after-POST protects from multiple submission
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (IllegalEntityException|ServiceFailureException e) {
                    log.error("Cannot update agent", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
                
                //return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }
    
    /**
     * Gets AgentManager from ServletContext, where it was stored by {@link StartListener}.
     *
     * @return AgentManager instance
     */
    private AgentManager getAgentManager() {
        return (AgentManager) getServletContext().getAttribute("agentManager");
    }

    /**
     * Stores the list of agents to request attribute "agents" and forwards to the JSP to display it.
     */
    private void showAgentsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            log.debug("showing table of agents");
            request.setAttribute("agents", getAgentManager().findAllAgents());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (IllegalEntityException|ServiceFailureException e) {
            log.error("Cannot show agents", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
