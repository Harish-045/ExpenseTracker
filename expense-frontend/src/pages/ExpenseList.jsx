import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import {
    getExpensePage,
    deleteExpense,
    searchExpense,
    getExpenseByCategory,
    sortExpense,
    exportExpenses,
    uploadReceipt
} from "../services/expenseService";
import { useNavigate } from "react-router-dom";

import Loader from "../components/common/Loader";
import "../styles/expense.css";
import { toast } from "react-toastify";

function ExpenseList() {

    const [page, setPage] = useState(0);
    const [size] = useState(5);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(true);

    const [expenses, setExpenses] = useState([]);
    const [keyword, setKeyword] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        loadExpenses();
    }, [page]);

 const loadExpenses = async () => {

     setLoading(true);

     try {

         const response = await getExpensePage(page, size);

         setExpenses(response.data.content);

         setTotalPages(response.data.totalPages);

     } catch (error) {

         console.log(error);

     } finally {

         setLoading(false);

     }

 };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this expense?")) return;

        try {

            await deleteExpense(id);

            loadExpenses();

        } catch (error) {

            console.log(error);

        }

    };
    const handleExport = async () => {

        try {

            const response = await exportExpenses();

            const url = window.URL.createObjectURL(
                new Blob([response.data])
            );

            const link = document.createElement("a");

            link.href = url;

            link.setAttribute(
                "download",
                "expenses.xlsx"
            );

            document.body.appendChild(link);

            link.click();

            link.remove();

            toast.success("Excel file downloaded");

        } catch (error) {

            console.log(error);

            toast.error("Export failed");

        }
    };

    const handleSearch = async () => {

        if (keyword.trim() === "") {

            loadExpenses();

            return;

        }

        try {

            const response = await searchExpense(keyword);

            setExpenses(response.data);

        } catch (error) {

            console.log(error);

        }

    };

    const handleCategory = async (category) => {

        if (category === "") {

            loadExpenses();

            return;

        }

        try {

            const response = await getExpenseByCategory(category);

            setExpenses(response.data);

        } catch (error) {

            console.log(error);

        }

    };
     const handleReceiptUpload = async (id, file) => {

         if (!file) return;

         const formData = new FormData();

         formData.append("file", file);

         try {

             await uploadReceipt(id, formData);

             toast.success("Receipt uploaded successfully");

             loadExpenses(); // refresh table

         } catch (error) {

             console.log(error);

             toast.error("Failed to upload receipt");

         }
     };
    const handleSort = async (field) => {

        if (field === "") {

            loadExpenses();

            return;

        }

        try {

            const response = await sortExpense(field);

            setExpenses(response.data);

        } catch (error) {

            console.log(error);

        }

    };
    if (loading) {

        return (
            <DashboardLayout>
                <Loader />
            </DashboardLayout>
        );

    }

    return (

        <DashboardLayout>

            <div className="expense-container">

                {/* Header */}

                <div className="expense-header">

                    <h2>Expense List</h2>

                    <div className="header-actions">

                        <button
                            className="export-btn"
                            onClick={handleExport}
                        >
                            Export Excel
                        </button>

                        <button
                            className="add-btn"
                            onClick={() => navigate("/add-expense")}
                        >
                            + Add Expense
                        </button>

                    </div>

                </div>
                {/* Toolbar */}

                <div className="expense-toolbar">

                    <input
                        type="text"
                        placeholder="Search expense..."
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                    />

                    <button onClick={handleSearch}>
                        Search
                    </button>

                    <select
                        onChange={(e) => handleCategory(e.target.value)}
                    >
                        <option value="">All Categories</option>
                        <option>Food</option>
                        <option>Travel</option>
                        <option>Shopping</option>
                        <option>Bills</option>
                        <option>Health</option>
                        <option>Entertainment</option>
                        <option>Others</option>
                    </select>

                    <select
                        onChange={(e) => handleSort(e.target.value)}
                    >
                        <option value="">Sort By</option>
                        <option value="title">Title</option>
                        <option value="amount">Amount</option>
                        <option value="category">Category</option>
                        <option value="date">Date</option>
                    </select>

                </div>

                {/* Table */}

                <table className="expense-table">

                    <thead>

                    <tr>

                        <th>Title</th>
                        <th>Amount</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Date</th>
                        <th>Receipt</th>
                        <th>Actions</th>

                    </tr>

                    </thead>

                    <tbody>

                    {expenses.length === 0 ? (

                        <tr>

                            <td colSpan="7" className="no-data">

                                No Expenses Found

                            </td>

                        </tr>

                    ) : (

                        expenses.map((expense) => (

                            <tr key={expense.id}>

                                <td>{expense.title}</td>

                                <td>₹ {expense.amount}</td>

                                <td>{expense.category}</td>

                                <td>{expense.description}</td>

                                <td>{expense.date}</td>

                                {/* Receipt Column */}

                                <td>

                                    {expense.receipt ? (

                                        <a
                                            href={`http://13.234.67.184:8080/uploads/receipts/${expense.receipt}`}
                                            target="_blank"
                                            rel="noreferrer"
                                            className="receipt-link"
                                        >
                                            View Receipt
                                        </a>

                                    ) : (

                                        <label className="upload-label">

                                            Upload

                                            <input
                                                type="file"
                                                accept="image/*,.pdf"
                                                style={{ display: "none" }}
                                                onChange={(e) =>
                                                    handleReceiptUpload(
                                                        expense.id,
                                                        e.target.files[0]
                                                    )
                                                }
                                            />

                                        </label>

                                    )}

                                </td>

                                {/* Actions */}

                                <td>

                                    <button
                                        className="edit-btn"
                                        onClick={() =>
                                            navigate(`/edit-expense/${expense.id}`)
                                        }
                                    >
                                        Edit
                                    </button>

                                    <button
                                        className="delete-btn"
                                        onClick={() =>
                                            handleDelete(expense.id)
                                        }
                                    >
                                        Delete
                                    </button>

                                </td>

                            </tr>

                        ))

                    )}

                    </tbody>

                </table>

                {/* Pagination */}

                <div className="pagination">

                    <button
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        Previous
                    </button>

                    <span>

                        Page {page + 1} of {totalPages}

                    </span>

                    <button
                        disabled={page + 1 === totalPages}
                        onClick={() => setPage(page + 1)}
                    >
                        Next
                    </button>

                </div>

            </div>

        </DashboardLayout>

    );

}

export default ExpenseList;
