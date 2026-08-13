import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import DashboardLayout from "../layouts/DashboardLayout";
import {
  getExpenseById,
  updateExpense,
} from "../services/expenseService";
import { toast } from "react-toastify";

function EditExpense() {

  const { id } = useParams();

  const navigate = useNavigate();

  const [expense, setExpense] = useState({
    title: "",
    amount: "",
    category: "",
    description: "",
  });

  useEffect(() => {
    loadExpense();
  }, []);

  const loadExpense = async () => {
    try {

      const response = await getExpenseById(id);

      setExpense(response.data);

    } catch (error) {

      console.log(error);

    }
  };

  const handleChange = (e) => {
    setExpense({
      ...expense,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    try {

      await updateExpense(id, expense);

      toast.success("Expense Updated Successfully");

      navigate("/expenses");

    } catch (error) {

      toast.error("Failed to Update Expense");

      console.log(error);

    }
  };

  return (
    <DashboardLayout>

      <div className="form-card">

        <h2>Edit Expense</h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="title"
            value={expense.title}
            onChange={handleChange}
            placeholder="Expense Title"
            required
          />

          <input
            type="number"
            name="amount"
            value={expense.amount}
            onChange={handleChange}
            placeholder="Amount"
            required
          />

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

          <textarea
            name="description"
            rows="4"
            value={expense.description}
            onChange={handleChange}
            placeholder="Description"
          />

          <button type="submit">

            Update Expense

          </button>

        </form>

      </div>

    </DashboardLayout>
  );
}

export default EditExpense;