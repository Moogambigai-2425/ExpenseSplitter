<template>
  <div>
    <h2 class="sec-title">People</h2>
    <form @submit.prevent="addPerson" class="add-person-form">
      <input v-model="newPerson.name" placeholder="Name" required maxlength="100" />
      <input v-model="newPerson.email" type="email" placeholder="Email (optional)" />
      <button type="submit" class="add-btn">Add</button>
    </form>
    <ul class="people-list">
      <li v-for="person in people" :key="person.id">
        {{ person.name }} <span v-if="person.email">({{ person.email }})</span>
        <button class="edit-btn" @click="editPerson(person)">Edit</button>
        <button class="del-btn" @click="deletePerson(person.id)">Delete</button>
      </li>
    </ul>

    <div v-if="editingPerson" class="edit-person-modal">
      <h3>Edit Person</h3>
      <form @submit.prevent="updatePerson">
        <input v-model="editingPerson.name" placeholder="Name" required maxlength="100" />
        <input v-model="editingPerson.email" type="email" placeholder="Email (optional)" />
        <button type="submit" class="save-btn">Save</button>
        <button type="button" class="cancel-btn" @click="cancelEdit">Cancel</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      people: [],
      newPerson: { name: '', email: '' },
      editingPerson: null
    }
  },
  methods: {
    fetchPeople() {
      this.$axios.get('/people').then(res => {
        this.people = res.data
      })
    },
    addPerson() {
      this.$axios.post('/people', this.newPerson)
        .then(() => {
          this.newPerson = { name: '', email: '' }
          this.fetchPeople()
        })
        .catch(err => alert(err.response?.data?.message || 'Error adding person'))
    },
    editPerson(person) {
  // Make a deep copy of the person to avoid direct mutations
  this.editingPerson = {...person}
},
updatePerson() {
  this.$axios.put(`/people/${this.editingPerson.id}`, this.editingPerson)
    .then(() => {
      this.editingPerson = null // Hide edit form
      this.fetchPeople()        // Refresh list
    })
    .catch(err => {
      const msg = err.response?.data?.message || 'Error updating person'
      alert(msg)
      // Optionally keep edit form open for fixes
    })
},
cancelEdit() {
  this.editingPerson = null
},

    deletePerson(id) {
      if (confirm('Delete this person?')) {
        this.$axios.delete(`/people/${id}`).then(() => this.fetchPeople())
      }
    }
  },
  created() {
    this.fetchPeople()
  }
}
</script>

<style>
.sec-title {
  margin: 12px 0;
  color: #483d8b;
}
.add-person-form input {
  margin-right: 6px;
  padding: 6px 10px;
  border-radius: 4px;
  border: 1px solid #aaa;
}
.add-btn {
  background: #6b8e23;
  border: none;
  color: white;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}
.add-btn:hover { background: #556b2f; }
.people-list {
  margin-top: 16px;
  list-style: none;
  padding-left: 0;
}
.people-list li {
  margin-bottom: 10px;
  font-size: 1.1rem;
}
.edit-btn, .del-btn {
  margin-left: 8px;
  padding: 3px 8px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  color: white;
}
.edit-btn {
  background-color: #1e90ff;
}
.edit-btn:hover {
  background-color: #187bcd;
}
.del-btn {
  background-color: #e57373;
}
.del-btn:hover {
  background-color: #d32f2f;
}
.edit-person-modal {
  margin-top: 24px;
  background: #f0f0ff;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px #aaa;
}
.edit-person-modal input {
  margin-right: 6px;
  padding: 6px 10px;
  border-radius: 4px;
  border: 1px solid #aaa;
}
.save-btn {
  background: #228b22;
  border: none;
  color: white;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 8px;
}
.save-btn:hover {
  background: #1a6a1a;
}
.cancel-btn {
  background: #999;
  border: none;
  color: white;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
}
.cancel-btn:hover {
  background: #666;
}
</style>
