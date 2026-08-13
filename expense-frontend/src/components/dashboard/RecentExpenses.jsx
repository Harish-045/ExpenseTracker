import "../../styles/dashboard.css";

function RecentExpenses({ expenses }) {

    return (

        <div className="table-card">

            <h3>Recent Expenses</h3>

            <table className="expense-table">

                <thead>

                <tr>

                    <th>Title</th>
                    <th>Category</th>
                    <th>Date</th>
                    <th>Amount</th>

                </tr>

                </thead>

                <tbody>

                {expenses.map((expense) => (

                    <tr key={expense.id}>

                        <td>{expense.title}</td>

                        <td>{expense.category}</td>

                        <td>{expense.date}</td>

                        <td>₹ {expense.amount}</td>

                    </tr>

                ))}

                </tbody>

            </table>

        </div>

    );

}

export default RecentExpenses;