package it.unibo.oop.lab.exception2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * JUnit test to test {@link StrictBankAccount}.
 * 
 */
public class TestStrictBankAccount {
	
	private static final double TRANSACTION_FEE = 0.1;
	private static final double MANAGEMENT_FEE = 5;
	

	/**
	 * Used to test Exceptions on {@link StrictBankAccount}.
	 */
	@Test
	public void testBankOperations() {

		/*
		 * 1) Creare due StrictBankAccountImpl assegnati a due AccountHolder a scelta,
		 * con ammontare iniziale pari a 10000 e nMaxATMTransactions=10
		 * 
		 * 2) Effetture un numero di operazioni a piacere per verificare che le
		 * eccezioni create vengano lanciate soltanto quando opportuno, cioè in presenza
		 * di un id utente errato, oppure al superamento del numero di operazioni ATM
		 * gratuite.
		 */
		final AccountHolder accountHolder1 = new AccountHolder("Lino", "Banfi", 1);
		final AccountHolder accountHolder2 = new AccountHolder("Gennaro", "Gattuso", 2);

		final StrictBankAccount bankAccount1 = new StrictBankAccount(1, 10000, 10);
		final StrictBankAccount bankAccount2 = new StrictBankAccount(2, 10000, 10);

		/*
		 * Check the accounts setups 1) Check if the balance is 10000 2) Check if the
		 * number of transactions done
		 */
		assertEquals(10000, bankAccount1.getBalance(), 0.1);
		assertEquals(10000, bankAccount2.getBalance(), 0.1);

		assertEquals(0, bankAccount1.getNTransactions(), 0);
		assertEquals(0, bankAccount2.getNTransactions(), 0);

		/*
		 * Check the deposit operation 1) Deposit 100 euro in the bankAccount1 with the
		 * correct id 2) Deposit 100 euro in the bankAccount2 with a wrong user id
		 */
		try {
			bankAccount1.deposit(1, 100);

			assertEquals(10100, bankAccount1.getBalance(), 0.1);
			assertEquals(1, bankAccount1.getNTransactions(), 0);

		} catch (WrongAccountHolderException e) {
			fail("Errors while checking the account holder");
		}

		try {
			bankAccount2.deposit(1, 100);

			fail("I shouldn't get here. I can't deposit money using another user id ");

		} catch (WrongAccountHolderException e) {

			assertNotNull(e.getMessage());
			assertEquals(10000, bankAccount2.getBalance(), 0.1);
			assertEquals(0, bankAccount2.getNTransactions(), 0);
		}

		/*
		 * Check the withdraw operation 1) Withdraw 1000 euro in the bankAccount1 with
		 * the correct id 2) Withdraw 100000 euro in the bankAcount1 with the correct id
		 * 3) Withdraw 100 euro in the bankAccount1 with a wrong user id
		 */
		try {
			bankAccount1.withdraw(1, 1000);

			assertEquals(9100, bankAccount1.getBalance(), 0.1);
			assertEquals(2, bankAccount1.getNTransactions(), 0);

		} catch (WrongAccountHolderException e) {
			fail("Errors while checking the account holder");
		} catch (NotEnoughFoundsException e) {
			fail("Errors while checking the balance of the bankAccount");
		}

		try {
			bankAccount1.withdraw(1, 100000);

			fail("I'm requesting too much money from the bank account");

		} catch (WrongAccountHolderException e) {
			fail("Errors while checking the account holder");
		} catch (NotEnoughFoundsException e) {
			assertNotNull(e.getMessage());
			assertEquals(9100, bankAccount1.getBalance(), 0.1);
			assertEquals(2, bankAccount1.getNTransactions(), 0);
		}

		try {

			bankAccount1.withdraw(2, 100);
			fail("I shouldn't get here. I can't withdraw money using another user id");

		} catch (WrongAccountHolderException e) {

			assertNotNull(e.getMessage());
			assertEquals(9100, bankAccount1.getBalance(), 0.1);
			assertEquals(2, bankAccount1.getNTransactions(), 0);

		} catch (NotEnoughFoundsException e) {
			fail("Errors while checking the balance of the bankAccount");
		}

		/*
		 * Operations from the ATM After making 8 operations with the bankAccount2 try
		 * to withdraw and deposit form the ATM
		 */
		for (int i = 0; i < 9; i++) {
			bankAccount2.deposit(2, 1);
		}

		assertEquals(9, bankAccount2.getNTransactions(), 0);

		try {

			bankAccount2.depositFromATM(2, 1000);

			assertEquals(11008, bankAccount2.getBalance(), 0.1);
			assertEquals(10, bankAccount2.getNTransactions(), 0);

		} catch (WrongAccountHolderException e) {
			fail("Errors while checking the account holder");
		}

		try {

			bankAccount2.depositFromATM(2, 1000);

			fail("I shouldn't get here. Operations limit reached !!");

		} catch (WrongAccountHolderException e) {
			fail("Errors while checking the account holder");
		} catch (TransactionsOverQuotaException e) {
			assertNotNull(e.getMessage());
		}

		try {
			final double initialBalance = bankAccount1.getBalance();
			final int initialTransactionsCount = bankAccount1.getNTransactions();

			bankAccount1.depositFromATM(1, 1000);

			assertEquals(initialBalance + 1000, bankAccount1.getBalance(), 1);
			assertEquals(initialTransactionsCount + 1, bankAccount1.getNTransactions(), 0);

		} catch (WrongAccountHolderException e) {
			assertNotNull(e.getMessage());
		} catch (NotEnoughFoundsException e) {
			fail("Error while checking the account founds");
		} catch (TransactionsOverQuotaException e) {
			fail("Errors while checking the transaction quota");
		}

		try {

			bankAccount1.withdrawFromATM(2, 1000);

			fail("I shouldn't get here. I can't withdraw money using another user id");

		} catch (WrongAccountHolderException e) {
			assertNotNull(e.getMessage());
		} catch (NotEnoughFoundsException e) {
			fail("Error while checking the account founds");
		} catch (TransactionsOverQuotaException e) {
			fail("Errors while checking the transaction quota");
		}
		
		
		/*
		 * Test the computeManagementFees method with the two accounts
		 * 1) Test a normal account fee retraction operation with both the bank accounts
		 * 2) Test the account fee operation with a wrong user id
		 */
		final double feeAmountAccount1 = MANAGEMENT_FEE + (bankAccount1.getNTransactions() * TRANSACTION_FEE);
		final double initialBalanceAccount1 = bankAccount1.getBalance();
		final double feeAmountAccount2 = MANAGEMENT_FEE + (bankAccount2.getNTransactions() * TRANSACTION_FEE);
		final double initialBalanceAccount2 = bankAccount2.getBalance();
		
		try {
			
			bankAccount1.computeManagementFees(1);
			bankAccount2.computeManagementFees(2);
			
			assertEquals(initialBalanceAccount1 - feeAmountAccount1 , bankAccount1.getBalance(), 1);
			assertEquals(initialBalanceAccount2 -feeAmountAccount2 , bankAccount2.getBalance(), 1);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			
			bankAccount1.computeManagementFees(2);
			
			fail("I shouldn't get here : I'm applying the fees with another user id ");
			
		} catch (WrongAccountHolderException e) {
			assertNotNull(e.getMessage());
		} catch (NotEnoughFoundsException e) {
			fail("Error while checking the account founds");
		} catch (TransactionsOverQuotaException e) {
			fail("Errors while checking the transaction quota");
		}
		
		bankAccount1.withdraw(1, bankAccount1.getBalance());
		
		try {
			
			bankAccount1.computeManagementFees(1);
			
			fail("I shouldn't get here : I haven't  ");
			
		} catch (WrongAccountHolderException e) {
			assertNotNull(e.getMessage());
		} catch (NotEnoughFoundsException e) {
			fail("Error while checking the account founds");
		} catch (TransactionsOverQuotaException e) {
			fail("Errors while checking the transaction quota");
		}
		
	}
}
