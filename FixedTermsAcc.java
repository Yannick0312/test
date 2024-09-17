public class FixedTermsAcc extends Account {
    public FixedTermsAcc(int id, int accountNumber, double balance) {
        super(id, accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "Fixed-Terms Account";
    }
}
