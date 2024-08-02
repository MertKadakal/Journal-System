import java.util.ArrayList;

public class Author {
    public String id;
    public String name;
    public String university;
    public String department;
    public String email;
    public ArrayList<String> articles;

    public Author(String id, String name, String university, String department, String email) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.department = department;
        this.email = email;
    }
}
