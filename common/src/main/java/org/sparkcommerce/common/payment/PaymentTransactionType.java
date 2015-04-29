/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.payment;

import org.sparkcommerce.common.SparkEnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The PaymentTransactionType is used to represent the types of operations that could occur on the within the same payment.
 * In the Spark core framework, these types appear on the org.sparkcommerce.core.payment.domain.PaymentTransaction.
 *
 * @see {@link #AUTHORIZE}
 * @see {@link #CAPTURE}
 * @see {@link #AUTHORIZE_AND_CAPTURE}
 * @see {@link #SETTLED}
 * @see {@link #REFUND}
 * @see {@link #VOID}
 * @see {@link #REVERSE_AUTH}
 * @see {@link #UNCONFIRMED}
 *
 * @author Anand Dasari
 * 
 */
public class PaymentTransactionType implements Serializable, SparkEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, PaymentTransactionType> TYPES = new LinkedHashMap<String, PaymentTransactionType>();

    /**
     * Funds have been authorized for capture. This might appear as a 'pending' transaction on a customer's credit
     * card statement
     */
    public static final PaymentTransactionType AUTHORIZE = new PaymentTransactionType("AUTHORIZE", "Authorize");
    
    /**
     * Funds have been charged/submitted/debited from the customer and payment is complete. Can <b>ONLY</b> occur after an
     * amount has ben {@link #AUTHORIZE}d.
     */
    public static final PaymentTransactionType CAPTURE = new PaymentTransactionType("CAPTURE", "Capture");
    
    /**
     * <p>Funds have been captured/authorized all at once. While this might be the simplest to
     * implement from an order management perspective, the recommended approach is to {@link #AUTHORIZE} and then {@link #CAPTURE}
     * in separate transactions and at separate times. For instance, an {@link AUTHORIZE} would happen once the {@link Order}
     * has completed checkout but then a {@link CAPTURE} would happen once the {@link Order} has shipped.</p>
     *
     * <p>NOTE: Many Gateways like to refer to this as also a SALE transaction.</p>
     * 
     * <p>This should be treated the exact same as a {@link #CAPTURE}.</p>
     */
    public static final PaymentTransactionType AUTHORIZE_AND_CAPTURE = new PaymentTransactionType("AUTHORIZE_AND_CAPTURE", "Authorize and Capture");
   
    /**
     * Can <b>ONLY</b> occur after a payment has been {@link #CAPTURE}d. This represents a payment that has been balanced by
     * the payment provider. This represents more finality than a {@link #CAPTURE}. Some payment providers might not explicitly
     * expose the details of settled transactions which are usually done in batches at the end of the day.
     */
    public static final PaymentTransactionType SETTLED = new PaymentTransactionType("SETTLED", "Settled");
    
    /**
     * Funds have been refunded/credited. This can <b>ONLY</b> occur after funds have been {@link #CAPTURE}d or
     * {@link #SETTLED}. This should only be used when money goes back to a customer.
     */
    public static final PaymentTransactionType REFUND = new PaymentTransactionType("REFUND", "Refund");
    
    /**
     * Void can happen after a CAPTURE but before it has been SETTLED. Payment transactions are usually settled in batches
     * at the end of the day. This basically performs the same action as a REFUND, although the transaction might not
     * hit the customer's card.
     */
    public static final PaymentTransactionType VOID = new PaymentTransactionType("VOID", "Void");
    
    /**
     * The reverse of {@link #AUTHORIZE}. This can <b>ONLY</b> occur <b>AFTER</b> funds have been
     * {@link #AUTHORIZE}d but <b>BEFORE</b> funds have been {@link #CAPTURE}d.
     */
    public static final PaymentTransactionType REVERSE_AUTH = new PaymentTransactionType("REVERSE_AUTH", "Reverse Auth");

    /**
     * <p>This occurs for Payment Types like PayPal Express Checkout where a transaction must be confirmed at a later stage. A transaction is confirmed if the gateway
     * has actually communicated something to hit against the user's card. There might be instances where payments have not
     * been confirmed at the moment that those payments have actually been added to the order. For instance, there might be
     * a scenario where it is desired to show a 'confirmation' page to the user before actually hitting 'submit' and
     * completing the checkout workflow that actually takes funds away from the user account (this is also the desired case
     * with gift cards and account credits). When the user adds all of the payments to their order, all of those payments
     * may not have been confirmed by the gateway but they should be on checkout.</p>
     * 
     * <p>Unconfirmed transactions are confirmed in the checkout workflow via the {@link ValidateAndConfirmPaymentActivity}.</p>
     */
    public static final PaymentTransactionType UNCONFIRMED = new PaymentTransactionType("UNCONFIRMED", "Not Confirmed");


    public static PaymentTransactionType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private String friendlyType;

    public PaymentTransactionType() {
        // do nothing
    }

    public PaymentTransactionType(String type, String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

   @Override
     public String getType() {
        return type;
    }

   @Override
     public String getFriendlyType() {
        return friendlyType;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)){
            TYPES.put(type, this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        PaymentTransactionType other = (PaymentTransactionType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
