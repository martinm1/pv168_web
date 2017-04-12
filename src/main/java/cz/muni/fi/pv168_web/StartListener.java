/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168_web;

import cz.muni.fi.pv168.AgentManager;
import cz.muni.fi.pv168.AgentManagerImpl;
import cz.muni.fi.pv168.MissionManagerImpl;
import cz.muni.fi.pv168.Main;
import cz.muni.fi.pv168.MissionManager;
import cz.muni.fi.pv168.SpyOrganizationManager;
import cz.muni.fi.pv168.SpyOrganizationManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 *
 * @author martin
 */
@WebListener
public class StartListener implements ServletContextListener {
    
     private final static Logger log = LoggerFactory.getLogger(StartListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent ev){
        log.info("webová aplikace inicializována");
        ServletContext servletContext = ev.getServletContext();
        DataSource dataSource = Main.createMemoryDatabase();
        
        MissionManager missionManager = new MissionManagerImpl();
        missionManager.setDataSource(dataSource);
        
        AgentManager agentManager = new AgentManagerImpl();
        agentManager.setDataSource(dataSource);
        
        SpyOrganizationManager spyOrganizationManager = new SpyOrganizationManagerImpl();
        spyOrganizationManager.setDataSource(dataSource);

        
        servletContext.setAttribute("agentManager", agentManager);
        servletContext.setAttribute("missionManager", missionManager);
        servletContext.setAttribute("spyOrganizationManager", spyOrganizationManager);
        log.info("vytvořeny manažery a uloženy do atributů servletContextu");
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        log.info("aplikace končí");
    }
   
    
}
