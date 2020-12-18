package ase;

import java.util.List;

public class Provider extends User {

    private String skill;
    private List<Project> currentProjects;
    private List<Project> doneProjects;

    Provider(String username, String password, String role, String skill) {
        super(username, password, role);
        this.skill = skill;
    }

    public String getSkill() {
        return this.skill;
    }

    public void addProject(Project project) {
        this.currentProjects.add(project);
    }

    public int getDoneProjectNumber() {
        return doneProjects.size();
    }
}
