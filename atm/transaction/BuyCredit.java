package atm.transaction;
import atm.ATM;
import atm.Session;
import atm.physical.*;
import banking.Balances;
import banking.Card;
import banking.Message;
import banking.Status;
import banking.Receipt;
import banking.Money;
import banking.AccountInformation;

public class BuyCredit extends Transaction
{
    /**
     * Account yang dikurangi duitnya
     */
    private int from;
    /**
     * Jumlah uang yang dikurangi
     */
    private Money amount;
    
    /**
     * Nama Operator
     */
    private int operator;
    /**
     * Nomor hp 
     */
    private String nomor;
    private String operatorKartu;
    
    /**
     * Constructor for objects of class BuyCredit
     */
    public BuyCredit(ATM atm, Session session, Card card, int pin)
    {
        super(atm, session, card, pin);
    }
    
    
    /** Transaksi untuk membeli pulsa
     * 
     * 
     */
    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled
    {        
        String [] operatorOptions = { "Telkomsel", "Indosat", "XL", "3"};
        operator = atm.getCustomerConsole().readMenuChoice(
                    "Please select the Operator", operatorOptions);
        operatorKartu =operatorOptions[operator];
        //System.out.println("Operator "+ operatorOptions[operator]);
        /*
         * membaca input nomor handphone customer
         */
        nomor = atm.getCustomerConsole().phoneNumber("Insert the Phone Number");

        /*
         * membaca input customer untuk memilih menu
         */
        from = atm.getCustomerConsole().readMenuChoice(
            "Account to withdraw from",
            AccountInformation.ACCOUNT_NAMES);
            
        /*
         * Untuk ditampilkan di layar
         */    
        String [] amountOptions =  { "Rp 25000", "Rp 50000", "Rp 100000", "Rp 150000" };
        Money [] amountValues = {
                                    new Money(25000), new Money(50000),
                                    new Money(100000), new Money(150000)
                                };
        String amountMessage ="";
        boolean validAmount = false;
        
        while(!validAmount)
        {
            amount = amountValues [
                        atm.getCustomerConsole().readMenuChoice(
                        amountMessage + "Amount of cash to buy credits", amountOptions)];
            validAmount = atm.getCashDispenser().checkCashOnHand(amount);
            
            if(!validAmount)
            {
                amountMessage = "Insufficient cash available\n";
            }
            
        }
        
        return new Message(Message.BUYCREDIT, card, pin, serialNumber,
                            from, -1, amount);
    }
    
    protected Receipt completeTransaction()
    {
        //atm.getCashDispenser().dispenseCash(amount);
        
        return new Receipt(this.atm, this.card, this, this.balances)
        {
            {
                detailsPortion= new String[2];
                detailsPortion[0] = "BUY CREDIT FROM: "+
                                    AccountInformation.ACCOUNT_ABBREVIATIONS[from]
                                    +"\nOPERATOR: "+ operatorKartu ;
                detailsPortion[1] = "PHONE NUMBER: "+ nomor +"\nAMOUNT: " + amount.toString();
            }
        };

    }
}
