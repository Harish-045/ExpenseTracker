# Expense Tracker

A full-stack **Expense Tracker** application built using **Spring Boot, React, MySQL, Docker, and Nginx**.

The application allows users to manage their daily expenses, upload and view receipts, manage budgets, split expenses, manage their profile, and view expense analytics through an interactive dashboard.

## GitHub Repository

**Repository:** https://github.com/Harish-045/ExpenseTracker

## Live Application

**Frontend:**  
http://13.234.67.184

**Backend API:**  
http://13.234.67.184:8080/swagger-ui/index.html

---

# Features

## Authentication

- User registration
- User login
- JWT-based authentication
- Protected routes
- Secure password handling

## Expense Management

- Add expenses
- Edit expenses
- Delete expenses
- Categorize expenses
- Search expenses
- Filter expenses
- Sort expenses
- Upload receipt images
- View uploaded receipts

## Budget Management

- Create monthly budgets
- Edit budgets
- Delete budgets
- Track expenses against budgets
- Display budget usage

## Dashboard

- Total expense summary
- Category-wise expense chart
- Monthly expense analysis
- Recent expenses
- Interactive charts

## Expense Splitting

- Split expenses between users
- Manage split expenses
- View personal split expenses

## Profile Management

- View user profile
- Update profile information
- Upload profile photo

## User Interface

- Responsive design
- Toast notifications
- Clean dashboard
- Easy navigation
- Dark/light theme support

---

# Tech Stack

## Frontend

- React
- React Router
- Axios
- Recharts
- React Toastify
- Vite
- HTML
- CSS

## Backend

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- REST APIs

## Database

- MySQL 8

## DevOps

- Docker
- Docker Compose
- Nginx
- AWS EC2

---

# Project Architecture

The project follows a full-stack architecture:


                    User
                     |
                     v
              React Frontend
                     |
                  Axios
                     |
                     v
              Nginx Server
                     |
                     v
           Spring Boot Backend
           
           
#Project Structure           
ExpenseTracker/
│
├── expense-backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/expense/
│   │       └── resources/
│   │
│   ├── uploads/
│   ├── Dockerfile
│   ├── pom.xml
│   └── ...
│
├── expense-frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   └── styles/
│   │
│   ├── public/
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   └── ...
│
├── docker-compose.yml
└── README.md
           
                     |
              REST API / JWT
                     |
                     v
                  MySQL
