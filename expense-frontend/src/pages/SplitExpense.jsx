import { useState } from "react";
import { toast } from "react-toastify";
import DashboardLayout from "../layouts/DashboardLayout";
import { createSplit } from "../services/splitService";
import "../styles/expense.css";

function SplitExpense() {

    const [formData, setFormData] = useState({
        description: "",
        totalAmount: "",
        friendEmail: ""
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await createSplit(formData);

            toast.success("Expense split successfully");

            setFormData({
                description: "",
                totalAmount: "",
                friendEmail: ""
            });

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to split expense"
            );
        }
    };

    return (

        <DashboardLayout>

            <div className="form-card">

                <div className="form-header">

                    <h2>Split Expense</h2>

                    <p>Split an expense equally with a friend</p>

                </div>

                <form onSubmit={handleSubmit}>

                    <div className="form-group">

                        <label>Description</label>

                        <input
                            type="text"
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label>Total Amount</label>

                        <input
                            type="number"
                            name="totalAmount"
                            value={formData.totalAmount}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label>Friend Email</label>

                        <input
                            type="email"
                            name="friendEmail"
                            value={formData.friendEmail}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <button
                        type="submit"
                        className="save-btn"
                    >
                        Split Expense
                    </button>

                </form>

            </div>

        </DashboardLayout>
    );
}

export default SplitExpense;