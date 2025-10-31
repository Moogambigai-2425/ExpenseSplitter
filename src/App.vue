<template>
  <div class="dashboard-bg">
    <h1 class="main-title">Expense Splitter</h1>
    <nav class="tab-nav">
      <router-link to="/dashboard/people" class="tab" active-class="active-tab">People</router-link>
      <router-link to="/dashboard/expenses" class="tab" active-class="active-tab">Expenses</router-link>
      <router-link to="/dashboard/totals" class="tab" active-class="active-tab">Totals</router-link>
    </nav>
    <div class="tab-content">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  // No need to import/register PersonList, ExpenseList, TotalsPanel here
}
</script>

<style>
.dashboard-bg {
  background: linear-gradient(120deg, #b3c2ef 0%, #ece9f7 100%);
  min-height: 100vh;
  padding: 0 0 40px 0;
}
.main-title {
  text-align: center;
  margin: 25px;
  color: #383e6c;
}
.tab-nav {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
  border-radius: 8px;
  background: #6b5b95;
  padding: 6px 0;
}
.tab {
  color: #fff;
  font-size: 1.3rem;
  margin: 0 20px;
  padding: 10px 18px;
  border-radius: 6px;
  text-decoration: none;
  transition: background 0.3s;
}
.tab:hover, .active-tab {
  background: #b8a9c9;
  color: #222;
}
.tab-content {
  margin:auto;
  max-width: 700px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 6px 24px #786fa650;
  padding:32px 36px;
}
</style>
