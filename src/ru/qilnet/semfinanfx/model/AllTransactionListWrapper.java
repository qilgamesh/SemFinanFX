package ru.qilnet.semfinanfx.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Andrey Semenyuk
 */
@XmlRootElement(name = "SemFinanFX_Data")
public class AllTransactionListWrapper {

	private List<TransactionListWrapper> allTransactions;


	@XmlElement(name = "monthTransactions")
	public List<TransactionListWrapper> getTransactions() {
		return allTransactions;
	}

	public void setTransactions(List<TransactionListWrapper> transactions) {
		this.allTransactions = transactions;
	}

}
