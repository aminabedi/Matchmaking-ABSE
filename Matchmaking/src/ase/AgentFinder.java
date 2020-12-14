//package ase;
//
//import jade.core.AID;
//import jade.core.Agent;
//import jade.domain.DFService;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.ServiceDescription;
//import jade.domain.FIPAException;
//
//public class AgentFinder {
//    public static final AID[] findAgentByService(Agent a, String serviceName) {
//        DFAgentDescription template = new DFAgentDescription();
//        ServiceDescription sd = new ServiceDescription();
//        sd.setType(serviceName);
//        template.addServices(sd);
//        try {
//            DFAgentDescription[] result = DFService.search(a, template);
//            AID[] aids = new AID[result.length];
//            for (int i = 0; i <result.length; i++) {
//                aids[i] = result[i].getName();
//            }
//
//            System.out.println("Found " + result.length + " potential recipients.");
//
//            return aids;
//        } catch (FIPAException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
