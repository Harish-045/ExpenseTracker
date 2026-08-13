import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import ExpenseList from "./pages/ExpenseList";
import AddExpense from "./pages/AddExpense";
import EditExpense from "./pages/EditExpense";
import Budget from "./pages/Budget";
import AddBudget from "./pages/AddBudget";
import EditBudget from "./pages/EditBudget";
import Profile from "./pages/Profile";
import SplitExpense from "./pages/SplitExpense";
import MySplits from "./pages/MySplits";


import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (


      <Routes>

        {/* Public Routes */}

        <Route path="/" element={<Login />} />


        <Route path="/register" element={<Register />} />

           <Route path="/forgot-password" element={<ForgotPassword />} />

               <Route
                path="/reset-password"
                 element={<ResetPassword />}
                  />
        {/* Protected Routes */}


        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/expenses"
          element={
            <ProtectedRoute>
              <ExpenseList />
            </ProtectedRoute>
          }
        />
        <Route path="/split-expense" element={<SplitExpense />} />

        <Route path="/my-splits" element={<MySplits />} />
        <Route
            path="/edit-budget/:id"
            element={<EditBudget />}
        />
        <Route
            path="/profile"
            element={<Profile />}
        />
        <Route
          path="/add-expense"
          element={
            <ProtectedRoute>
              <AddExpense />
            </ProtectedRoute>
          }
        />

        <Route
          path="/edit-expense/:id"
          element={
            <ProtectedRoute>
              <EditExpense />
            </ProtectedRoute>
          }
        />

        <Route
          path="/budget"
          element={
            <ProtectedRoute>
              <Budget />
            </ProtectedRoute>
          }
        />
        <Route
            path="/add-budget"
            element={<AddBudget />}
        />

      </Routes>


  );
}

export default App;
