public class CheckingAcc extends Account {
    public CheckingAcc(int id, int accountNumber, double balance) {
        super(id, accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "Checking Account";
    }
}
