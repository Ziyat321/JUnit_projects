import exceptions.InsufficientFundsException;
import exceptions.NegativeAmountException;

public class BankAccount {

    private boolean isBlocked = false;
    private Integer amount;
    private String currency;

    private final String firstName;
    private final String secondName;

    public BankAccount(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void block() {
        this.isBlocked = true;
    }

    public void activate(String currency) {
        this.amount = 0;
        this.currency = currency;
    }

    public void deposit(int amount) {
        if (isBlocked) throw new IllegalStateException("Счёт заблокирован.");
        if (currency != null) {
            if (amount > 0) {
                this.amount += amount;
            } else throw new NegativeAmountException("Невозможно пополнить счет отрицательным значением.");
        } else throw new IllegalStateException("Счёт не активирован.");
    }

    public void withdraw(int amount) {
        if (isBlocked) throw new IllegalStateException("Счёт заблокирован.");
        if (currency != null) {
            if (amount > 0) {
                if (amount < this.amount) {
                    this.amount -= amount;
                } else throw new InsufficientFundsException();
            } else throw new NegativeAmountException("Невозможно снять отрицательное значение.");
        } else throw new IllegalStateException("Счёт не активирован.");
    }

    public void transfer(BankAccount otherAccount, int amount) {
        if (isBlocked || otherAccount.isBlocked) throw new IllegalStateException("Один из счетов заблокирован.");
        if (currency != null && otherAccount.getCurrency() != null) {
            if (amount > 0) {
                if (amount < this.amount) {
                    this.amount -= amount;
                    otherAccount.deposit(amount);
                } else throw new InsufficientFundsException();
            } else throw new NegativeAmountException("Невозможно снять отрицательное значение.");
        } else throw new IllegalStateException("Один из счетов не активирован.");
    }

    public Integer getAmount() {
        if (amount == null) {
            throw new IllegalStateException("Счёт не активирован.");
        }
        return this.amount;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String[] getFullName() {
        return new String[]{firstName, secondName};
    }
}
