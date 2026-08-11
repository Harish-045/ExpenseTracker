import {
    PieChart,
    Pie,
    Cell,
    Tooltip,
    Legend,
    ResponsiveContainer
} from "recharts";

import {
    Card,
    CardContent,
    Typography
} from "@mui/material";

const COLORS = [
    "#1976d2",
    "#2e7d32",
    "#ed6c02",
    "#9c27b0",
    "#d32f2f",
    "#0288d1",
    "#7b1fa2",
    "#43a047"
];

function ExpensePieChart({ data }) {

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

                    Category Wise Expenses

                </Typography>

                <ResponsiveContainer
                    width="100%"
                    height={320}
                >

                    <PieChart>

                        <Pie

                            data={data}

                            dataKey="amount"

                            nameKey="category"

                            outerRadius={120}

                            label

                        >

                            {

                                data.map((entry,index)=>(

                                    <Cell

                                        key={index}

                                        fill={
                                            COLORS[
                                                index%
                                                COLORS.length
                                            ]
                                        }

                                    />

                                ))

                            }

                        </Pie>

                        <Tooltip/>

                        <Legend/>

                    </PieChart>

                </ResponsiveContainer>

            </CardContent>

        </Card>

    );

}

export default ExpensePieChart;