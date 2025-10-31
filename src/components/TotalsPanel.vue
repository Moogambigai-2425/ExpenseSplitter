<template>
  <div>
    <h2>Totals (Net Balance)</h2>
    <ul>
      <li v-for="(balance, personId) in totals" :key="personId">
        {{ getPersonName(personId) }}: {{ balance.toFixed(2) }}
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
