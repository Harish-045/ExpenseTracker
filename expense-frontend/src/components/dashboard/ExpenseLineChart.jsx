import {
    LineChart,
    Line,
    CartesianGrid,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
} from "recharts";

function ExpenseLineChart({ data }) {

    const chartData = Object.entries(data || {}).map(
        ([month, expense]) => ({
            month,
            expense,
        })
    );

    return (

        <div className="chart-card">

            <h3>Monthly Expenses</h3>

            <ResponsiveContainer
                width="100%"
                height={300}
            >

                <LineChart data={chartData}>

                    <CartesianGrid strokeDasharray="3 3" />

                    <XAxis dataKey="month" />

                    <YAxis />

                    <Tooltip />

                    <Line
                        type="monotone"
                        dataKey="expense"
                        stroke="#2563EB"
                        strokeWidth={3}
                    />

                </LineChart>

            </ResponsiveContainer>

        </div>

    );

}

export default ExpenseLineChart;