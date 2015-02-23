package be.lukin.poeditor.models;

import java.util.List;

public class Contributor {
    public static class Project{
        public String id;
        public String name;
        public String type;

        @Override
        public String toString() {
            return "Project{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
    
    public String name;
    public String email;
    public List<Project> projects;

    @Override
    public String toString() {
        return "Contributor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", projects=" + projects +
                '}';
    }
}
