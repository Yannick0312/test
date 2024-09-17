import java.sql.Date;

public interface IPerson /* Interface */ {
    int getID();
    String getSurname();
    String getName();
    String getGender();
    Date getBirthday();

    void setID(int _id);
    void setSurname(String _surname);
    void setName(String _name);
    void setGender(String _gender);
    void setBirthday(Date date);
}
