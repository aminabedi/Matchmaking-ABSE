package ase;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;

import java.util.ArrayList;
import java.util.List;

public class UserManagerAgent extends EnhancedAgent {

    UserGui userGui;
    List<User> users = new ArrayList<>();
    User currentUser;

    public UserManagerAgent() {
        addMockUsers();
    }

    private void addMockUsers() {
        users.add(new User("P1", "1", "provider"));
        users.add(new User("P2", "2", "provider"));
        users.add(new User("P3", "3", "provider"));

        users.add(new User("C1", "A", "customer"));
        users.add(new User("C2", "B", "customer"));
        users.add(new User("C3", "C", "customer"));
    }

    @Override
    protected void setup() {
        System.out.println("UserManagers-agent " + getAID().getName() + "is ready.");
        userGui = new UserGui(this);
        userGui.showGui();
        addBehaviour(new TickerBehaviour (this, Long.valueOf(10000)) {
        	protected void onTick() {
        		System.out.println("UserManagers-agent " + getAID().getName() + "is cycling.");
        	}
        });
    }

    @Override
    protected void takeDown() {
        userGui.dispose();
        System.out.println("UserManagers-agent " + getAID().getName() + "is terminating.");
    }

    public void login(String userName, String password, String role) {
//        addBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
                for (User user : users) {
                    if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                        System.out.println("Login Successfully " + user.getRole()+ ":" + User.PROVIDER);
                        switch(user.getRole()) {
                        case User.CUSTOMER:
                        	createAgent("Customer:" + user.getUsername(), "ase.CustomerAgent");
                        	break;
                        case User.PROVIDER:
                        	createAgent("Provider:" + user.getUsername(), "ase.ProviderAgent");
                        }
                        return;
                    }
                }
                userGui.showWrongCredential();
//            }
//        });
    }
    
}
