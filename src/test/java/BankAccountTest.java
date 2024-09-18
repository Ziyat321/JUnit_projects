import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    @Test
    void shouldBeBlockedAfterBlockIsCalled(){
        BankAccount account = new BankAccount("a", "b");
        account.block();
        assertTrue(account.isBlocked());
    }

    @Test
    void shouldReturnFirstNameThenSecondName(){
        BankAccount account = new BankAccount("a", "b");
        assertArrayEquals(new String[]{"a", "b"}, account.getFullName());
    }

    @Test
    void shouldReturnNullAmountWhenNotActive(){
        BankAccount account = new BankAccount("a", "b");
        String expectedMessage = "Счёт не активирован.";


        IllegalStateException ex = assertThrows(IllegalStateException.class, account::getAmount);

        assertEquals(expectedMessage, ex.getMessage());
        assertNull(account.getCurrency());
    }

    @Test
    void shouldNotBeBlockedWhenCreated() {
        BankAccount account = new BankAccount("a", "b");
        assertFalse(account.isBlocked());
    }

    @Test
    public void shouldReturnZeroAmountAfterActivation() {
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        assertEquals(0, account.getAmount());
        assertEquals("KZT", account.getCurrency());
    }

    @Test
    public void shouldNotWithdrawIfNotActivated() {
        BankAccount account = new BankAccount("a", "b");
        String expectedMsg = "Счёт не активирован.";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.withdraw(10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldWithdrawIfActivated() {
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.deposit(20);
        int expectedAmount = 10;
        int withdrawAmount = 10;

        account.withdraw(withdrawAmount);

        assertEquals(expectedAmount, account.getAmount());
    }

    @Test
    public void shouldNotWithdrawIfAmountIsInsufficient() {
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.deposit(20);
        int withdrawAmount = 40;

        assertThrows(InsufficientFundsException.class, () -> account.withdraw(withdrawAmount));
    }

    @Test
    public void shouldNotWithdrawIfBlocked(){
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.block();
        String expectedMsg = "Счёт заблокирован.";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.withdraw(10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAccountNotActivated(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов не активирован.";
        otherAccount.activate("KZT");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfOtherAccountNotActivated(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов не активирован.";
        account.activate("KZT");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAccountBlocked(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов заблокирован.";
        account.block();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfOtherAccountBlocked(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов заблокирован.";
        otherAccount.block();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAmountIsInsufficient(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");

        account.activate("KZT");
        otherAccount.activate("KZT");

        assertThrows(InsufficientFundsException.class,
                () -> account.transfer(otherAccount, 10));
    }

    @Test
    public void shouldTransferIfAmountIsSufficient(){
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        int amount = 20;
        int transferAmount = 10;
        int expectedAmount = 10;

        account.activate("KZT");
        otherAccount.activate("KZT");
        account.deposit(amount);
        account.transfer(otherAccount, transferAmount);

        assertEquals(expectedAmount, account.getAmount());
    }
}