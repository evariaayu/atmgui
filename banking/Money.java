/* * ATM Example system - file Money.java * * copyright (c) 2001 - Russell C. Bjork * */ package banking;/** Representation for money amounts */public class Money{            /** Instance variable: this amount represented as a number of cents      */    private long cents;         /** Constructor     *     *  @param dollars whole dollar amount     */    public Money(int dollars)    {        this(dollars, 0);    }        /** Constructor     *     *  @param dollars dollar part of amount     *  @param cents cents part of amount     */    public Money(int dollars, int cents)    {        this.cents = 100L * dollars + cents;    }        /** Copy constructor     *     *  @param toCopy the Money object to copy     */    public Money(Money toCopy)    {        this.cents = toCopy.cents;    }        /** Create a string representation of this amount     *     *  @return string representation of this amount     */    public String toString()    {        //System.out.println("masuk ke dalam String toString() di class Money, cents "+cents/100);        return "Rp" + cents/100; //+            // (cents %100 >= 10  ? "." + cents % 100 : ".0" + cents % 100);    }        /** Add an amount of money to this     *     *  @param amountToAdd the amount to add     */    public void add(Money amountToAdd)    {        //System.out.println("masuk ke dalam add Money di class Money");        this.cents += amountToAdd.cents;    }        /** Subtract an amount of money from this     *     *  @param amountToSubtract the amount to subtract     *     *  Precondition: amount must be <= this amount     */    public void subtract(Money amountToSubtract)    {        //System.out.println("masuk ke dalam subtract Money di class Money");        this.cents -= amountToSubtract.cents;    }        /** Compare this to another amount     *     *  @param compareTo the amount to compare to     *  @return true if this amount is <= compareTo amount     */    public boolean lessEqual(Money compareTo)    {        return this.cents <= compareTo.cents;    }        public long subtractLong (Money amountToSubtract)    {        long duitpertama = this.cents -= amountToSubtract.cents;        //System.out.println("this.cents "+this.cents);        long duit = duitpertama/100;        //System.out.println("Money long subtract "+duit);        return duit;    }    public long addLong(Money amountToAdd)    {        //System.out.println("masuk ke dalam add Money di class Money");        long duitpertama = this.cents += amountToAdd.cents;        long duit = duitpertama/100;        //System.out.println("Money long add "+duit);        return duit;    }}