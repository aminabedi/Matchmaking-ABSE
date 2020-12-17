package ase;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.util.HashSet;
import java.util.Set;

public class EnhancedAgent extends Agent {
    protected Set<AID> searchForService(String serviceName) {
        System.out.println("Ehanced agent launched");
        Set<AID> foundAgents = new HashSet<>();
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName.toLowerCase());
        dfd.addServices(sd);

        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(Long.valueOf(-1));

        try {
            DFAgentDescription[] results = DFService.search(this, dfd, sc);
            for (DFAgentDescription result : results) {
                foundAgents.add(result.getName());
            }
            return foundAgents;
        } catch (FIPAException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void takeDown() {
        System.out.println("Taking down" + getLocalName());
        try {
            DFService.deregister(this);
        } catch (Exception ex) {
        }
    }


    protected void register(String serviceName) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType(serviceName.toLowerCase());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
    }

    protected void createAgent(String name, String className) {
        AID agentID = new AID(name, AID.ISLOCALNAME);
        AgentContainer controller = getContainerController();
        try {
            AgentController agent = controller.createNewAgent(name, className, null);
            agent.start();
            System.out.println("+++ Created: " + agentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void killAgent(String name) {
        AID agentID = new AID(name, AID.ISLOCALNAME);
        AgentContainer controller = getContainerController();
        try {
            AgentController agent = controller.getAgent(name);
            agent.kill();
            System.out.println("+++ Killed: " + agentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}