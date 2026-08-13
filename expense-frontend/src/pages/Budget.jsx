import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../layouts/DashboardLayout";
import {
    getBudgets,
    getBudgetStatus,
    deleteBudget
} from "../services/budgetService";
import "../styles/budget.css";

function Budget() {

    const navigate = useNavigate();

    const [budgets, setBudgets] = useState([]);
    const [status, setStatus] = useState(null);

    useEffect(() => {

        loadData();

    }, []);

    const loadData = async () => {

        try {

            const budgetResponse = await getBudgets();

            const statusResponse = await getBudgetStatus();

            setBudgets(budgetResponse.data);

            setStatus(statusResponse.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this budget?")) return;

        try {

            await deleteBudget(id);

            loadData();

        }

        catch (error) {

            console.log(error);

        }

    };

    if (!status) {

        return (

            <DashboardLayout>

                <h2>Loading...</h2>

            </DashboardLayout>

        );

    }

    return (

        <DashboardLayout>

            <div className="budget-header">

                <h2>Budget</h2>

                <button
                    className="add-btn"
                    onClick={() => navigate("/add-budget")}
                >
                    + Add Budget
                </button>

            </div>

            {/* Summary */}

            <div className="budget-summary">

                <div className="summary-card">

                    <h4>Budget</h4>

                    <h2>₹ {status.budget}</h2>

                </div>

                <div className="summary-card">

                    <h4>Total Expense</h4>

                    <h2>₹ {status.spent}</h2>

                </div>

                <div className="summary-card">

                    <h4>Remaining</h4>

                    <h2>₹ {status.remaining}</h2>

                </div>

            </div>

            {/* Progress */}

            <div className="progress-card">

                <h3>Budget Usage</h3>

                <div className="progress">

                    <div
                        className="progress-fill"
                        style={{
                            width: `${Math.min(status.percentageUsed || 0, 100)}%`
                        }}
                    ></div>

                </div>

               <p>
                   {(status.percentageUsed ?? 0).toFixed(2)}% Used
               </p>

               {status.percentageUsed >= 80 &&
                status.percentageUsed < 100 && (

                   <div className="warning-box">

                       ⚠ You have used more than 80% of your budget

                   </div>

               )}

               {status.percentageUsed >= 100 && (

                   <div className="danger-box">

                       ❌ Budget Limit Exceeded

                   </div>

               )}

            </div>

            {/* Table */}

            <div className="table-card">

                <h3>Budget History</h3>

                <table className="expense-table">

                    <thead>

                    <tr>

                        <th>Amount</th>

                        <th>Month</th>

                        <th>Year</th>

                        <th>Actions</th>

                    </tr>

                    </thead>

                    <tbody>

                    {budgets.map((budget) => (

                        <tr key={budget.id}>

                            <td>₹ {budget.amount}</td>

                            <td>{budget.month}</td>

                            <td>{budget.year}</td>

                            <td>

                              <button
                                  className="edit-btn"
                                  onClick={() =>
                                      navigate(`/edit-budget/${budget.id}`)
                                  }
                              >
                                  Edit
                              </button>

                                <button
                                    className="delete-btn"
                                    onClick={() =>
                                        handleDelete(budget.id)
                                    }
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            </div>

        </DashboardLayout>

    );

}

export default Budget;