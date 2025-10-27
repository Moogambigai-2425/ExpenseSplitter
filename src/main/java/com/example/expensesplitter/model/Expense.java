package com.example.expensesplitter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    @NotBlank(message = "Description required")
    private String description;

    @NotNull(message = "Amount required")
    @DecimalMin(value = "0.01", message = "Amount must be > 0")
    private BigDecimal amount;

    @NotBlank(message = "paidBy person id required")
    private String paidBy; // person id

    private Instant date = Instant.now();

    private List<ParticipantShare> participants;

    // constructors, getters, setters

    public static class ParticipantShare {
        @NotBlank
        private String personId;
        @NotNull
        private BigDecimal share;
        // getters/setters
		public String getPersonId() {
			return personId;
		}
		public void setPersonId(String personId) {
			this.personId = personId;
		}
		public BigDecimal getShare() {
			return share;
		}
		public void setShare(BigDecimal share) {
			this.share = share;
		}
        
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<ParticipantShare> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantShare> participants) {
		this.participants = participants;
	}
    
}
