<template>
  <div>
    <h2 class="sec-title">Expenses</h2>
    <ExpenseForm :people="people" @saved="fetchExpenses" />
    <ul class="expense-list">
      <li v-for="expense in expenses" :key="expense.id">
        <span class="exp-label">
          <strong>{{ expense.description }}</strong> — ₹{{ expense.amount }} 
          <span class="paidBy">Paid by: <span class="paid-person">{{ getPersonName(expense.paidBy) }}</span></span>
        </span>
        <ul class="participant-list">
          <li v-for="part in expense.participants" :key="part.personId">
            <span v-if="part.personId === expense.paidBy" class="text-green">
              {{ getPersonName(part.personId) }} paid ₹{{ part.share.toFixed(2) }}
            </span>
            <span v-else class="text-red">
              {{ getPersonName(part.personId) }} owes ₹{{ part.share.toFixed(2) }}
            </span>
          </li>
        </ul>
        <button class="del-btn" @click="deleteExpense(expense.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
import ExpenseForm from '../components/ExpenseForm.vue'
export default {
  components: { ExpenseForm },
  data() {
    return {
      expenses: [],
      people: []
    }
  },
  methods: {
    fetchExpenses() {
      this.$axios.get('/expenses').then(res => {
        this.expenses = res.data
      })
    },
    fetchPeople() {
      this.$axios.get('/people').then(res => {
        this.people = res.data
      })
    },
    deleteExpense(id) {
      this.$axios.delete(`/expenses/${id}`).then(() => this.fetchExpenses())
    },
    getPersonName(id) {
      const person = this.people.find(p => p.id === id)
      return person ? person.name : 'Unknown'
    }
  },
  created() {
    this.fetchExpenses()
    this.fetchPeople()
  }
}
</script>

<style>
.sec-title { margin:12px 0; color:#483d8b;}
.exp-label { font-size: 1.13rem; }
.paidBy { color: #7e7e7e;}
.paid-person { color: #228b22;}
.text-green { color: #228b22; font-weight: 500;}
.text-red { color: #b22222;}
.expense-list { margin-top:16px;}
.participant-list { margin-bottom: 8px; margin-top:3px;}
.del-btn {
  background: #e57373;
  color: #fff;
  border:none;
  border-radius:4px;
  padding:4px 12px;
  cursor:pointer;
  transition: background 0.2s;
}
.del-btn:hover { background: #d32f2f;}
</style>
