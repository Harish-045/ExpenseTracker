import {
  FaHome,
  FaMoneyBillWave,
  FaWallet,
  FaUser,
  FaSignOutAlt,
  FaUsers,
} from "react-icons/fa";

import { NavLink, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

import "../styles/sidebar.css";

function Sidebar() {

  const navigate = useNavigate();

  const { logout } = useContext(AuthContext);

  const handleLogout = () => {

    logout();

    navigate("/");

  };

  return (

    <aside className="sidebar">

      <div className="sidebar-top">

        <h2 className="logo">ExpenseWise</h2>

      </div>

      <nav className="sidebar-nav">

        <NavLink
          to="/dashboard"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaHome />
          <span>Dashboard</span>
        </NavLink>

        <NavLink
          to="/expenses"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaMoneyBillWave />
          <span>Expenses</span>
        </NavLink>

        <NavLink
          to="/budget"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaWallet />
          <span>Budget</span>
        </NavLink>

        {/* NEW - Split Expense */}

        <NavLink
          to="/split-expense"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaUsers />
          <span>Split Expense</span>
        </NavLink>

        {/* NEW - My Splits */}

        <NavLink
          to="/my-splits"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaUsers />
          <span>My Splits</span>
        </NavLink>

        <NavLink
          to="/profile"
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaUser />
          <span>Profile</span>
        </NavLink>

      </nav>

      <button
        className="logout-btn"
        onClick={handleLogout}
      >
        <FaSignOutAlt />
        Logout
      </button>

    </aside>
  );
}

export default Sidebar;