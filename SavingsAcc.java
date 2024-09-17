public class SavingsAcc extends Account {
    public SavingsAcc(int id, int accountNumber, double balance) {
        super(id, accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
