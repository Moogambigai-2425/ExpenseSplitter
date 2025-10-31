<template>
  <div>
    <h2 class="sec-title">Totals (Net Balance)</h2>
    <ul class="totals-list">
      <li v-for="(balance, personId) in totals" :key="personId" :class="{ positive: balance >= 0, negative: balance < 0 }">
        {{ getPersonName(personId) }}: ₹{{ balance.toFixed(2) }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      totals: {},
      people: []
    }
  },
  methods: {
    fetchTotals() {
      this.$axios.get('/expenses/totals').then(res => {
        this.totals = res.data
      })
    },
    fetchPeople() {
      this.$axios.get('/people').then(res => {
        this.people = res.data
      })
    },
    getPersonName(id) {
      const person = this.people.find(p => p.id === id)
      return person ? person.name : id
    }
  },
  created() {
    this.fetchTotals()
    this.fetchPeople()
  }
}
</script>

<style>
.sec-title {
  margin: 12px 0;
  color: #483d8b;
}
.totals-list {
  list-style: none;
  padding-left: 0;
  margin-top: 16px;
}
.totals-list li {
  font-size: 1.2rem;
  margin-bottom: 8px;
  font-weight: bold;
  padding: 8px 12px;
  border-radius: 6px;
  width: fit-content;
}
.positive {
  background-color: #d4edda;
  color: #155724;
}
.negative {
  background-color: #f8d7da;
  color: #721c24;
}
</style>
