package atm.transaction;
import atm.ATM;
import atm.Session;
import atm.physical.*;
import banking.AccountInformation;
import banking.Card;
import banking.Message;
import banking.Money;
import banking.Status;
import banking.Receipt;


/**
 * Representation for a savings transaction
 * 
 * @author Kania, Fikri, Eva 
 * @version 0.3
 */
public class Savings extends Transaction
{
    /** Account to saving to
     */ 
    private int to;
    
    /** Amount of money to saving
     */
    private Money amount;
    /**
     * Constructor for objects of class Savings
     */
    public Savings(ATM atm, Session session, Card card, int pin)
    {
        super(atm, session, card, pin);
    }

    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled
    {
        to = atm.getCustomerConsole().readMenuChoice(
            "Account to saving to",
            AccountInformation.ACCOUNT_NAMES);

        amount = atm.getCustomerConsole().readAmount("Amount to saving");
        
        return new Message(Message.INITIATE_SAVING,
                           card, pin, serialNumber, -1, to, amount);
    }
    
    protected Receipt completeTransaction() throws CustomerConsole.Cancelled
    {
        atm.getEnvelopeAcceptor().acceptEnvelope();
        Status status = atm.getNetworkToBank().sendMessage(
            new Message(Message.COMPLETE_SAVING,
                        card, pin, serialNumber, -1, to, amount), 
            balances);
            
        return new Receipt(this.atm, this.card, this, this.balances) {
            {
                detailsPortion = new String[2];
                detailsPortion[0] = "SAVING TO: " + 
                                    AccountInformation.ACCOUNT_ABBREVIATIONS[to];
                detailsPortion[1] = "AMOUNT: " + amount.toString();
            }
        };
    }
}
