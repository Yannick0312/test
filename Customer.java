import java.sql.Date;

public class Customer implements IPerson {
    private int id;
    private String surname; 
    private String name;
    private String gender; 
    private Date birthday;
    private Account account;

    public Customer(int id, String surname, String name, String gender, Date birthday, Account account) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.account = account;
    }

    public Customer() {
    }

    //Getter & Setter
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

   @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID = ").append(getID())
          .append(", Surname = ").append(getSurname())
          .append(", Name = ").append(getName())
          .append(", Gender = ").append(getGender())
          .append(", Birthday = ").append(getBirthday());

        if (accounts != null && !accounts.isEmpty()) {
            sb.append(", Accounts: ");
            for (Account account : accounts) {
                sb.append("\n\t").append(account);
            }
        } else {
            sb.append(", No accounts available.");
        }

        return sb.toString();
    }

}
