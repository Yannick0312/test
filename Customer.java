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

    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ID = ").append(getID())
      .append(", Surname = ").append(getSurname())
      .append(", Name = ").append(getName())
      .append(", Gender = ").append(getGender())
      .append(", Birthday = ").append(getBirthday());

    if (account != null) {
        sb.append(", AccountID = ").append(account.getID())
          .append(", Accountnumber = ").append(account.getAccountNumber())
          .append(", Accountbalance = ").append(account.getBalance())
          .append(", Accounttype = ").append(account.getAccountType());
    } else {
        sb.append(", No account information available.");
    }

    return sb.toString();
}

}
