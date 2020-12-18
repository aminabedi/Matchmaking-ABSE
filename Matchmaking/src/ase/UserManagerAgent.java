package ase;

import jade.core.behaviours.TickerBehaviour;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagerAgent extends EnhancedAgent {

    UserGui userGui;
    static List<Provider> providers = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();

    public UserManagerAgent() {
        addMockUsers();
    }

    public static Provider getProvider(String name) {
        for (Provider provider : providers) {
            if (provider.getUsername().equals(name)) {
                return provider;
            }
        }
        return null;
    }

    public static List<Provider> searchProvider(String text, List<Provider> providersList) {
        List<Provider> searchedProviders = new ArrayList<>();
        for (Provider provider : providersList) {
            if (provider.getUsername().contains(text) || provider.getSkill().contains(text)) {
                searchedProviders.add(provider);
            }
        }
        return searchedProviders;
    }

    private void addMockUsers() {
        providers.add(new Provider("P1", "1", "provider", "Java", 5));
        providers.add(new Provider("P2", "2", "provider", "PHP", 4));
        providers.add(new Provider("P3", "3", "provider", "C", 3));

        customers.add(new Customer("C1", "1", "customer", 2));
        customers.add(new Customer("C2", "2", "customer", 6));
        customers.add(new Customer("C3", "3", "customer", 8));
    }

    public void registerUser(String userName, String password, String role, String skill) {
        if (role.isEmpty()) {
            customers.add(new Customer(userName, password, role, 0));
        } else {
            providers.add(new Provider(userName, password, role, skill, 0));
        }
    }

    @Override
    protected void setup() {
        System.out.println("UserManagers-agent " + getAID().getName() + "is ready.");
        userGui = new UserGui(this);
        userGui.showGui();
        addBehaviour(new TickerBehaviour(this, Long.valueOf(10000)) {
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
        if (role.equals(User.CUSTOMER)) {
            for (User user : customers) {
                if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                    System.out.println("Login Successfully " + user.getRole());
                    createAgent("Customer:" + user.getUsername(), "ase.CustomerAgent");
                    return;
                }
            }
        } else {
            for (User user : providers) {
                if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                    System.out.println("Login Successfully " + user.getRole());
                    createAgent("Provider:" + user.getUsername(), "ase.ProviderAgent");
                    return;
                }
            }
        }
        userGui.showWrongCredential();
    }
}
