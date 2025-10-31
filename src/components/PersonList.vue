<template>
  <div>
    <h2>People</h2>
    <PersonForm @saved="fetchPeople" />
    <ul>
      <li v-for="person in people" :key="person.id">
        {{ person.name }} ({{ person.email }})
        <button @click="editPerson(person)">Edit</button>
        <button @click="deletePerson(person.id)">Delete</button>
      </li>
    </ul>
    <PersonForm v-if="editingPerson" :person="editingPerson" @saved="onPersonUpdated" @cancel="cancelEdit" />
  </div>
</template>

<script>
import PersonForm from './PersonForm.vue'

export default {
  components: { PersonForm },
  data() {
    return {
      people: [],
      editingPerson: null
    }
  },
  methods: {
    fetchPeople() {
      this.$axios.get('/people').then(res => {
        this.people = res.data
      })
    },
    deletePerson(id) {
      if (confirm('Delete this person?')) {
        this.$axios.delete(`/people/${id}`).then(() => this.fetchPeople())
      }
    },
    editPerson(person) {
      this.editingPerson = { ...person }
    },
    onPersonUpdated() {
      this.editingPerson = null
      this.fetchPeople()
    },
    cancelEdit() {
      this.editingPerson = null
    }
  },
  created() {
    this.fetchPeople()
  }
}
</script>
