import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import StatCard from "../components/dashboard/StatCard";
import { getDashboard } from "../services/dashboardService";
import ExpensePieChart from "../components/dashboard/ExpensePieChart";
import ExpenseLineChart from "../components/dashboard/ExpenseLineChart";
import RecentExpenses from "../components/dashboard/RecentExpenses";
import Loader from "../components/common/Loader";
import "../styles/dashboard.css";


function Dashboard() {

    const [dashboard, setDashboard] = useState(null);

    useEffect(() => {

        loadDashboard();

    }, []);

    const loadDashboard = async () => {

        try {

            const response = await getDashboard();

            setDashboard(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

if (!dashboard) {
    return (
        <DashboardLayout>
            <Loader />
        </DashboardLayout>
    );
}

return (

    <DashboardLayout>

        <h2 className="dashboard-title">
            Dashboard
        </h2>

        <div className="stats-grid">

            <StatCard
                title="Total Expense"
                value={`₹ ${dashboard.totalExpense}`}
            />

            <StatCard
                title="Budget"
                value={`₹ ${dashboard.budgetAmount}`}
            />

            <StatCard
                title="Remaining Budget"
                value={`₹ ${dashboard.remainingBudget}`}
            />

        </div>

        <div className="chart-grid">

            <ExpensePieChart
                data={dashboard.categoryChart}
             />

            <ExpenseLineChart
            data={dashboard.monthlyChart}
            />

        </div>

        <RecentExpenses
         expenses={dashboard.recentExpenses}
         />
         {/* Analytics Summary */}

         <div className="analytics-summary">

             <div className="analytics-card">

                 <h4>Average Expense</h4>

                 <h2>
                     ₹ {
                         Object.values(dashboard.categoryChart || {})
                             .reduce((a, b) => a + b, 0) /
                             (Object.keys(dashboard.categoryChart || {}).length || 1)
                     }
                 </h2>

             </div>

             <div className="analytics-card">

                 <h4>Highest Category</h4>

                 <h2>
                     {
                         Object.entries(dashboard.categoryChart || {})
                             .sort((a, b) => b[1] - a[1])[0]?.[0] || "N/A"
                     }
                 </h2>

             </div>

             <div className="analytics-card">

                 <h4>Total Categories</h4>

                 <h2>
                     {Object.keys(dashboard.categoryChart || {}).length}
                 </h2>

             </div>

         </div>
    </DashboardLayout>

);

}

export default Dashboard;