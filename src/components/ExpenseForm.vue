<template>
  <form @submit.prevent="submitForm" class="expense-form">
    <input v-model="form.description" placeholder="Description" required class="form-input" />
    <input v-model.number="form.amount" type="number" step="0.01" min="0.01" placeholder="Amount" required class="form-input" />
    <select v-model="form.paidBy" required class="form-select">
      <option value="" disabled>Select payer</option>
      <option v-for="person in people" :key="person.id" :value="person.id">{{ person.name }}</option>
    </select>
    <div class="part-section">
      <h4 class="sec-title">Participants (equal split)</h4>
      <div v-for="(participant, index) in form.participants" :key="index" class="part-row">
        <select v-model="participant.personId" required class="form-select" @change="updateSharesEqualSplit">
          <option value="" disabled>Select person</option>
          <option v-for="person in people" :key="person.id" :value="person.id">{{ person.name }}</option>
        </select>
        <input :value="participant.share.toFixed(2)" readonly class="read-only" />
        <button type="button" class="btn-remove" @click="removeParticipant(index)">Remove</button>
      </div>
      <button type="button" class="btn-add" @click="addParticipant">Add Participant</button>
    </div>
    <button type="submit" class="btn-submit">Add Expense</button>
  </form>
</template>

<script>
export default {
  props: ['people'],
  data() {
    return {
      form: {
        description: '',
        amount: null,
        paidBy: '',
        participants: []
      }
    }
  },
  methods: {
    addParticipant() {
      this.form.participants.push({ personId: '', share: 0 });
      this.updateSharesEqualSplit();
    },
    removeParticipant(index) {
      this.form.participants.splice(index, 1);
      this.updateSharesEqualSplit();
    },
    updateSharesEqualSplit() {
      const validParticipants = this.form.participants.filter(p => p.personId);
      const splitCount = validParticipants.length;
      if (!this.form.amount || splitCount === 0) {
        validParticipants.forEach(p => p.share = 0);
        return;
      }
      const shareAmount = Number((this.form.amount / splitCount).toFixed(2));
      validParticipants.forEach(participant => {
        participant.share = shareAmount;
      });
    },
    submitForm() {
      this.updateSharesEqualSplit();
      const shareSum = this.form.participants.reduce((sum, p) => sum + p.share, 0);
      if (Math.abs(shareSum - this.form.amount) > 0.01) {
        alert('Sum of participant shares must equal the total amount');
        return;
      }
      this.$axios.post('/expenses', this.form)
        .then(() => {
          this.resetForm();
          this.$emit('saved');
        })
        .catch(err => alert(err.response?.data?.message || 'Error creating expense'));
    },
    resetForm() {
      this.form.description = '';
      this.form.amount = null;
      this.form.paidBy = '';
      this.form.participants = [];
    }
  },
  watch: {
    'form.amount': function() {
      this.updateSharesEqualSplit();
    },
    'form.paidBy': function() {
      this.updateSharesEqualSplit();
    }
  }
}
</script>

<style>
.expense-form {
  background: #f5f6fa;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 12px #a9a9bc55;
  margin-bottom: 24px;
}
.form-input, .form-select {
  margin-right: 10px;
  margin-bottom: 12px;
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #bbb;
  background: #fff;
  font-size: 1rem;
}
.read-only {
  background: #f0f4fa;
  border-radius: 6px;
  border: none;
  padding: 8px 10px;
  margin-right: 10px;
  width: 80px;
  text-align: right;
}
.part-section {
  margin-bottom: 20px;
}
.sec-title {
  margin-top: 14px;
  margin-bottom: 8px;
  color: #705caa;
}
.part-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.btn-remove {
  background: #e57373;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 7px 22px;
  cursor: pointer;
  font-weight: bold;
  margin-left: 10px;
  transition: background 0.2s;
}
.btn-remove:hover {
  background: #d32f2f;
}
.btn-add {
  background: #1e90ff;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 9px 24px;
  cursor: pointer;
  margin-top: 10px;
  margin-bottom: 10px;
  transition: background 0.2s;
  font-weight: bold;
  box-shadow: 0 2px 8px #a9c6e855;
}
.btn-add:hover {
  background: #187bcd;
}
.btn-submit {
  background: linear-gradient(89deg, #705caa 20%, #b39ddb 80%);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 12px 30px;
  cursor: pointer;
  font-size: 1.1rem;
  margin-top: 12px;
  font-weight: bold;
  box-shadow: 0 2px 8px #6c569d22;
  transition: background 0.2s;
}
.btn-submit:hover {
  background: linear-gradient(90deg, #b39ddb 20%, #705caa 80%);
}
</style>
