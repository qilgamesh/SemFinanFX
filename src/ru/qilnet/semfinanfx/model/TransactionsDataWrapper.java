package ru.qilnet.semfinanfx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Model class for all Transactions
 *
 * @author Andrey Semenyuk
 */
@XmlRootElement(name = "TransactionsData")
public class TransactionsDataWrapper {

	private List<MonthTransactions> transactionsData;

	@XmlElement(name = "Transactions")
	public List<MonthTransactions> getTransactionsData() {
		return transactionsData;
	}

	public void setTransactionsData(List<MonthTransactions> lmt) {
		this.transactionsData = lmt;
	}

}

