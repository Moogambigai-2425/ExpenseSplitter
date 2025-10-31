import { createRouter, createWebHistory } from 'vue-router'
import PeoplePage from '../views/PeoplePage.vue'
import ExpensesPage from '../views/ExpensesPage.vue'
import TotalsPage from '../views/TotalsPage.vue'

const routes = [
  { path: '/', redirect: '/dashboard/people' },
  { path: '/dashboard/people', component: PeoplePage },
  { path: '/dashboard/expenses', component: ExpensesPage },
  { path: '/dashboard/totals', component: TotalsPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
