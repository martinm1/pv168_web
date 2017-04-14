package cz.muni.fi.pv168_web;

import cz.muni.fi.pv168.Agent;
import cz.muni.fi.pv168.AgentManager;
import cz.muni.fi.pv168.AgentManagerImpl;
import cz.muni.fi.pv168.Mission;
import cz.muni.fi.pv168.MissionManagerImpl;
import cz.muni.fi.pv168.Main;
import cz.muni.fi.pv168.MissionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class StartListener implements ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(StartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        log.info("webová aplikace inicializována");
        ServletContext servletContext = ev.getServletContext();
        DataSource dataSource = Main.createMemoryDatabase();
        
        AgentManager agentManager = new AgentManagerImpl();
        agentManager.setDataSource(dataSource);
        MissionManager missionManager = new MissionManagerImpl();
        missionManager.setDataSource(dataSource);
        
        servletContext.setAttribute("missionManager", agentManager);
        servletContext.setAttribute("agentManager", missionManager);
        log.info("vytvořeny manažery a uloženy do atributů servletContextu");
    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        log.info("aplikace končí");
    }
}
