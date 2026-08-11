import {

    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid

} from "recharts";

import {

    Card,
    CardContent,
    Typography

} from "@mui/material";

function ExpenseLineChart({ data }) {

    return (

        <Card
            elevation={5}
            sx={{
                borderRadius:3,
                height:420
            }}
        >

            <CardContent>

                <Typography
                    variant="h6"
                    mb={2}
                    fontWeight="bold"
                >

                    Monthly Expense Trend

                </Typography>

                <ResponsiveContainer
                    width="100%"
                    height={320}
                >

                    <LineChart
                        data={data}
                    >

                        <CartesianGrid
                            strokeDasharray="5 5"
                        />

                        <XAxis
                            dataKey="month"
                        />

                        <YAxis/>

                        <Tooltip/>

                        <Line

                            type="monotone"

                            dataKey="amount"

                            stroke="#1976d2"

                            strokeWidth={4}

                            dot={{
                                r:6
                            }}

                            activeDot={{
                                r:9
                            }}

                        />

                    </LineChart>

                </ResponsiveContainer>

            </CardContent>

        </Card>

    );

}

export default ExpenseLineChart;