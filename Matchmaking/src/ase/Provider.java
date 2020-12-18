package ase;

import java.util.ArrayList;
import java.util.List;

public class Provider extends User {

    private String skill;
    private List<Project> currentProjects = new ArrayList<>();
    private List<Project> doneProjects = new ArrayList<>();

    Provider(String username, String password, String role, String skill, int rating) {
        super(username, password, role, rating);
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

    public String getInfo(){
        String text =  "Name: " + getUsername() + ", ";
        text += "Role: " + getRole() + ", ";
        text += "Skill: " + getSkill() + ", ";
        text += "Completed Project No: " + getDoneProjectNumber() + ",";
        text += "Rating:" + getRating();
        return text;
    }
}
