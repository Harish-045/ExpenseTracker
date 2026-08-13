import { useState } from "react";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../layouts/DashboardLayout";
import { addBudget } from "../services/budgetService";
import "../styles/budget.css";
import { toast } from "react-toastify";

function AddBudget() {

    const navigate = useNavigate();

    const [budget, setBudget] = useState({
        amount: "",
        month: "",
        year: ""
    });

    const handleChange = (e) => {

        setBudget({

            ...budget,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await addBudget(budget);

            toast.success("Budget Added Successfully");

            navigate("/budget");

        }

        catch (error) {

            console.log(error);

            toast.error("Failed to Add Budget");

        }

    };

    return (

        <DashboardLayout>

            <div className="form-card">

                <div className="form-header">

                    <h2>Add Budget</h2>

                    <p>Create Monthly Budget</p>

                </div>

                <form onSubmit={handleSubmit}>

                    <div className="form-group">

                        <label>Budget Amount</label>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Enter Budget Amount"
                            value={budget.amount}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label>Month</label>

                        <select
                            name="month"
                            value={budget.month}
                            onChange={handleChange}
                            required
                        >

                            <option value="">Select Month</option>

                            <option>January</option>
                            <option>February</option>
                            <option>March</option>
                            <option>April</option>
                            <option>May</option>
                            <option>June</option>
                            <option>July</option>
                            <option>August</option>
                            <option>September</option>
                            <option>October</option>
                            <option>November</option>
                            <option>December</option>

                        </select>

                    </div>

                    <div className="form-group">

                        <label>Year</label>

                        <input
                            type="number"
                            name="year"
                            placeholder="Enter Year"
                            value={budget.year}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <button
                        type="submit"
                        className="save-btn"
                    >
                        Save Budget
                    </button>

                </form>

            </div>

        </DashboardLayout>

    );

}

export default AddBudget;