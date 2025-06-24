package issues8;

public class UserModel {
    private int id;
    private String name;
    private String age;
    private int countNumber;

    public UserModel(int id, String name, String age, int countNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.countNumber = countNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }
}
