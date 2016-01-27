package ru.qilnet.semfinanfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.qilnet.semfinanfx.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * @author Andrey Semenyuk
 */
@XmlRootElement(name = "transactions")
public class TransactionListWrapper {

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlAttribute(name = "date")
	private LocalDate localDate;
	private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

	@XmlElement(name = "transaction")
	public ObservableList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ObservableList<Transaction> transactions) {
		localDate = LocalDate.now();
		this.transactions = transactions;
	}

}
