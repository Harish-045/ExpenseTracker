import "../styles/navbar.css";
import { useTheme } from "../context/ThemeContext";

function Navbar() {

    const { theme, toggleTheme } = useTheme();

    return (

        <nav className="navbar">

            <div className="navbar-title">

                <h2>Expense Tracker</h2>

                <p>Manage your expenses smartly</p>

            </div>

            <button
                className="theme-btn"
                onClick={toggleTheme}
            >
                {theme === "light"
                    ? "🌙 Dark"
                    : "☀️ Light"}
            </button>

        </nav>

    );
}

export default Navbar;