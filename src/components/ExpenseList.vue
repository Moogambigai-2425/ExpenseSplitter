<template>
  <div>
    <h2>Expenses</h2>
    <ExpenseForm :people="people" @saved="fetchExpenses" />
    <ul>
  <li v-for="expense in expenses" :key="expense.id">
    <strong>{{ expense.description }}</strong> - {{ expense.amount }} - Paid by: <span style="color:green;">{{ getPersonName(expense.paidBy) }}</span>
    <ul>
      <li v-for="part in expense.participants" :key="part.personId">
        <template v-if="part.personId === expense.paidBy">
          <span style="color:green;">{{ getPersonName(part.personId) }} paid {{ part.share.toFixed(2) }}</span>
        </template>
        <template v-else>
          <span style="color:red;">{{ getPersonName(part.personId) }} owes {{ part.share.toFixed(2) }}</span>
        </template>
      </li>
    </ul>
    <button @click="deleteExpense(expense.id)">Delete</button>
  </li>
</ul>

  </div>
</template>

<script>
import ExpenseForm from './ExpenseForm.vue'

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
      if (confirm('Delete this expense?')) {
        this.$axios.delete(`/expenses/${id}`).then(() => this.fetchExpenses())
      }
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
