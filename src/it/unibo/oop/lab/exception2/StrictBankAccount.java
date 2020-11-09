package it.unibo.oop.lab.exception2;

/**
 * Class modeling a BankAccount with strict policies: getting money is allowed
 * only with enough founds, and there are also a limited number of free ATM
 * transaction (this number is provided as a input in the constructor).
 * 
 */
public class StrictBankAccount implements BankAccount {

    private final int usrID;
    private double balance;
    private int nTransactions;
    private final int nMaxATMTransactions;
    private static final double ATM_TRANSACTION_FEE = 1;
    private static final double MANAGEMENT_FEE = 5;
    private static final double TRANSACTION_FEE = 0.1;

    /**
     * 
     * @param usrID
     *            user id
     * @param balance
     *            initial balance
     * @param nMaxATMTransactions
     *            max no of ATM transactions allowed
     */
    public StrictBankAccount(final int usrID, final double balance, final int nMaxATMTransactions) {
        this.usrID = usrID;
        this.balance = balance;
        this.nMaxATMTransactions = nMaxATMTransactions;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void deposit(final int usrID, final double amount) {
        	this.checkUser(usrID);
            this.balance += amount;
            incTransactions();
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void withdraw(final int usrID, final double amount) {
    	this.checkUser(usrID);
    	this.isWithdrawAllowed(amount);
        this.balance -= amount;
        incTransactions();
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void depositFromATM(final int usrID, final double amount) {
    	this.checkTransactionLimit();
        this.deposit(usrID, amount - StrictBankAccount.ATM_TRANSACTION_FEE);
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void withdrawFromATM(final int usrID, final double amount) {
    	this.checkTransactionLimit();
        this.withdraw(usrID, amount + StrictBankAccount.ATM_TRANSACTION_FEE);   
    }

    /**
     * 
     * {@inheritDoc}
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public int getNTransactions() {
        return nTransactions;
    }

    /**
     * 
     * @param usrID id of the user related to these fees
     */
    public void computeManagementFees(final int usrID) {
        final double feeAmount = MANAGEMENT_FEE + (nTransactions * StrictBankAccount.TRANSACTION_FEE);
        this.checkUser(usrID);
        this.isWithdrawAllowed(feeAmount);
        balance -= MANAGEMENT_FEE + nTransactions * StrictBankAccount.TRANSACTION_FEE;
        nTransactions = 0;
        
    }

    private void checkUser(final int id) {
    	if(this.usrID != id) {
    		throw new WrongAccountHolderException();
    	}
    }

    private void isWithdrawAllowed(final double amount) {
        if(this.balance < amount) {
        	throw new NotEnoughFoundsException();
        }
    }
    
    private void checkTransactionLimit() {
    	if (this.nTransactions >= this.nMaxATMTransactions) {
    		throw new TransactionsOverQuotaException();
    	}
    }

    private void incTransactions() {
        nTransactions++;
    }
}
