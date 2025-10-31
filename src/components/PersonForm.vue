<template>
  <form @submit.prevent="submitForm">
    <input v-model="form.name" placeholder="Name" required maxlength="100" />
    <input v-model="form.email" type="email" placeholder="Email (optional)" />
    <button type="submit">{{ person ? 'Update' : 'Add' }}</button>
    <button v-if="person" @click.prevent="$emit('cancel')">Cancel</button>
  </form>
</template>

<script>
export default {
  props: ['person'],
  data() {
    return {
      form: {
        name: this.person?.name || '',
        email: this.person?.email || ''
      }
    }
  },
  methods: {
    submitForm() {
      const req = this.person
        ? this.$axios.put(`/people/${this.person.id}`, this.form)
        : this.$axios.post('/people', this.form)
      req.then(() => this.$emit('saved'))
    }
  }
}
</script>
