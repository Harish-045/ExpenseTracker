import { useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import { addExpense } from "../services/expenseService";
import { toast } from "react-toastify";
function AddExpense() {

    const [expense, setExpense] = useState({
        title: "",
        amount: "",
        category: "",
        description: ""
    });

    const handleChange = (e) => {
        setExpense({
            ...expense,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await addExpense(expense);

            toast.success("Expense Added Successfully");

            setExpense({
                title: "",
                amount: "",
                category: "",
                description: ""
            });

        } catch (error) {

            toast.error("Failed to Add Expense");

            console.log(error);

        }
    };

return (
    <DashboardLayout>

        <div className="form-card">

            <div className="form-header">
                <h2>Add Expense</h2>
                <p>Add your daily expense details</p>
            </div>

      <form onSubmit={handleSubmit}>

          <div className="form-group">

              <label>Title</label>

              <input
                  type="text"
                  name="title"
                  placeholder="Enter expense title"
                  value={expense.title}
                  onChange={handleChange}
                  required
              />

          </div>

          <div className="form-group">

              <label>Amount</label>

              <input
                  type="number"
                  name="amount"
                  placeholder="Enter amount"
                  value={expense.amount}
                  onChange={handleChange}
                  required
              />

          </div>

          <div className="form-group">

              <label>Category</label>

              <select
                  name="category"
                  value={expense.category}
                  onChange={handleChange}
                  required
              >
                  <option value="">Select Category</option>
                  <option>Food</option>
                  <option>Travel</option>
                  <option>Shopping</option>
                  <option>Bills</option>
                  <option>Health</option>
                  <option>Entertainment</option>
                  <option>Others</option>
              </select>

          </div>

          <div className="form-group">

              <label>Description</label>

              <textarea
                  rows="4"
                  name="description"
                  placeholder="Enter description"
                  value={expense.description}
                  onChange={handleChange}
              />

          </div>

          <button
              type="submit"
              className="save-btn"
          >
              Save Expense
          </button>

      </form>

        </div>

    </DashboardLayout>
);
}

export default AddExpense;