## 💰 Expense Splitter — Full Stack Application
### Overview

Expense Splitter is a simple full-stack web application that helps users manage shared expenses among a group of people.

It allows users to:

* Add participants (people)

* Record shared expenses

Automatically calculate each person’s balance (who owes and who gets back money)

### 🧩 Tech Stack

| Layer        | Technology           | Description |
|---------------|----------------------|--------------|
| **Frontend**  | Vue 3 + Vite         | User Interface for adding people & expenses |
| **Backend**   | Spring Boot (Java 17+) | REST API that handles all expense logic |
| **Database**  | MongoDB              | Stores participants and expense records |
| **Tools**     | Postman, GitHub      | Used for API testing & version control |

## ⚙️ Features Implemented

✅ Add / View / Update / Delete Participants

✅ Add / View / Update / Delete Expenses

✅ Automatic Expense Split Calculation

✅ RESTful API with proper HTTP status codes

✅ MongoDB database integration

✅ CORS enabled for Vue frontend (http://localhost:8081)

✅ Input validation using jakarta.validation

✅ Basic unit testing with JUnit and Mockito


## Business Logic

When a new expense is added:

* The person who paid is recorded (paidBy).

* Each participant’s share is stored (personId + share amount).

* The backend service (ExpenseService) computes each person’s net balance: total_paid - total_share = net_balance

    * Positive balance → person should receive money.

    * Negative balance → person owes money.

## ⚠️ Technical Challenges Faced

Problem	Reason	Solution - JSON Mapping Error	Frontend sent participants as strings instead of full objects	Fixed by adjusting frontend payload format

## 🚀 How to Run the Project

### Backend (Spring Boot)

Open the expensesplitter folder in Eclipse / IntelliJ

Start MongoDB service locally (mongodb://localhost:27017/expense_splitter)

Run the project → it starts at http://localhost:8080

Test in Postman:

* GET → /api/people

* POST → /api/expenses

### Frontend (Vue 3)

Open the src folder in VS Code

### Install dependencies:

npm install

### Run the project:

npm run dev

Open in browser → http://localhost:8081
