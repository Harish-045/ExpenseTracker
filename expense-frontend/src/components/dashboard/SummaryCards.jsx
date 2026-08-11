import "../../styles/dashboard.css";

function SummaryCards() {

    const cards = [
        {
            title: "Total Expense",
            value: "₹ 45,250"
        },
        {
            title: "This Month",
            value: "₹ 8,450"
        },
        {
            title: "This Year",
            value: "₹ 45,250"
        },
        {
            title: "Budget Left",
            value: "₹ 14,750"
        }
    ];

    return (
        <div className="summary-grid">

            {cards.map((card, index) => (

                <div className="summary-card" key={index}>

                    <h4>{card.title}</h4>

                    <h2>{card.value}</h2>

                </div>

            ))}

        </div>
    );

}

export default SummaryCards;