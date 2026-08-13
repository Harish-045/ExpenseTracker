import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import DashboardLayout from "../layouts/DashboardLayout";
import {
    getBudgetById,
    updateBudget
} from "../services/budgetService";
import "../styles/budget.css";
import { toast } from "react-toastify";

function EditBudget() {

    const { id } = useParams();

    const navigate = useNavigate();

    const [budget, setBudget] = useState({

        amount: "",

        month: "",

        year: ""

    });

    useEffect(() => {

        loadBudget();

    }, []);

    const loadBudget = async () => {

        try {

            const response =
                await getBudgetById(id);

            setBudget(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const handleChange = (e) => {

        setBudget({

            ...budget,

            [e.target.name]: e.target.value

        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await updateBudget(id, budget);

            toast.success("Budget Updated Successfully");

            navigate("/budget");

        }

        catch (error) {

            console.log(error);

        }

    };

    return (

        <DashboardLayout>

            <div className="form-card">

                <div className="form-header">

                    <h2>Edit Budget</h2>

                    <p>Update Monthly Budget</p>

                </div>

                <form onSubmit={handleSubmit}>

                    <div className="form-group">

                        <label>Budget Amount</label>

                        <input
                            type="number"
                            name="amount"
                            value={budget.amount}
                            onChange={handleChange}
                        />

                    </div>

                    <div className="form-group">

                        <label>Month</label>

                        <select
                            name="month"
                            value={budget.month}
                            onChange={handleChange}
                        >

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
                            value={budget.year}
                            onChange={handleChange}
                        />

                    </div>

                    <button
                        className="save-btn"
                    >
                        Update Budget
                    </button>

                </form>

            </div>

        </DashboardLayout>

    );

}

export default EditBudget;