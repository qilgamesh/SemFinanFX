package ru.qilnet.semfinanfx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of transactions. This is used for saving the
 * list of transactions to XML.
 *
 * @author Andrey Semenyuk
 */
@XmlRootElement(name = "transactions")
public class TransactionListWrapper {

	private List<Transaction> transactions;

	@XmlElement(name = "transaction")
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
