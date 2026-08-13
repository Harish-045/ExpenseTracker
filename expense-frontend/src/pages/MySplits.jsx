import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import { getMySplits } from "../services/splitService";
import "../styles/expense.css";

function MySplits() {

    const [splits, setSplits] = useState([]);

    useEffect(() => {
        loadSplits();
    }, []);

    const loadSplits = async () => {

        try {

            const response = await getMySplits();

            setSplits(response.data);

        } catch (error) {

            console.log(error);
        }
    };

    return (

        <DashboardLayout>

            <div className="expense-container">

                <div className="expense-header">

                    <h2>My Splits</h2>

                </div>

                <table className="expense-table">

                    <thead>

                    <tr>

                        <th>Description</th>

                        <th>Total Amount</th>

                        <th>Friend Pays</th>

                    </tr>

                    </thead>

                    <tbody>

                    {splits.length === 0 ? (

                        <tr>

                            <td colSpan="3" className="no-data">

                                No split expenses found

                            </td>

                        </tr>

                    ) : (

                        splits.map((split) => (

                            <tr key={split.id}>

                                <td>{split.description}</td>

                                <td>₹ {split.totalAmount}</td>

                                <td>₹ {split.splitAmount}</td>

                            </tr>

                        ))
                    )}

                    </tbody>

                </table>

            </div>

        </DashboardLayout>
    );
}

export default MySplits;